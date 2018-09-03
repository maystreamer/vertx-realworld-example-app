package com.greyseal.vertx.boot.annotation;

import com.greyseal.vertx.boot.Constant.Configuration;
import com.greyseal.vertx.boot.handler.BaseHandler;
import com.greyseal.vertx.boot.handler.PostHandler;
import com.greyseal.vertx.boot.handler.PreHandler;
import com.greyseal.vertx.boot.helper.ConfigHelper;
import com.greyseal.vertx.boot.util.DateUtil;
import com.greyseal.vertx.boot.util.ResponseUtil;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Route;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public final class AnnotationProcessor {
    private static final String BASE_HANDLER_PACKAGE = "com.greyseal.vertx.boot.handler";
    private static final String HANDLER_PACKAGE = ConfigHelper.getHandlerPackage();
    private static final String CONTEXT_PATH = ConfigHelper.getContextPath();
    protected static Logger logger = LoggerFactory.getLogger(AnnotationProcessor.class);
    // private static final Reflections reflections = new Reflections(PACKAGE_NAME);
    static Reflections reflections;
    static Set<Class<? extends BaseHandler>> clazzes;

    static {
        if (null == HANDLER_PACKAGE) {
            throw new RuntimeException("No handlers to configure. Default PingHandler will be configured.");
        }
        reflections = new Reflections(new ConfigurationBuilder().addUrls(ClasspathHelper.forPackage(HANDLER_PACKAGE)).addUrls(ClasspathHelper.forPackage(BASE_HANDLER_PACKAGE))
                .setScanners(new SubTypesScanner(false), new MethodAnnotationsScanner())
                .filterInputsBy(new FilterBuilder().includePackage(HANDLER_PACKAGE, BASE_HANDLER_PACKAGE)));
        clazzes = reflections.getSubTypesOf(BaseHandler.class);
    }

    public static void init(final Router router, final Vertx vertx) {

        if (null != clazzes && !clazzes.isEmpty()) {
            clazzes.forEach(baseHandler -> buildHandler(router, baseHandler, vertx));
        } else {
            throw new RuntimeException("There are no handlers to configure");
        }
    }

    private static void buildHandler(final Router router, final Class<? extends BaseHandler> clazz, final Vertx vertx) {
        RequestMapping mapping = clazz.getAnnotation(RequestMapping.class);
        // TODO: re-factor for the params
        //if (null != mapping) {
        final String[] baseConsumes = mapping != null ? mapping.consumes() : null;
        final String[] baseProduces = mapping != null ? mapping.produces() : null;
        final String basePath = mapping != null ? mapping.path() : null;
        final Set<Method> methods = getMethodsAnnotatedWith(clazz, RequestMapping.class);
        methods.forEach(method -> {
            final RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            final String[] methodConsumes = annotation.consumes();
            final String[] methodProduces = annotation.produces();
            final String methodPath = annotation.path();
            final HttpMethod httpMethod = annotation.method();
            final String path = basePath != null ? String.join("", CONTEXT_PATH, basePath, methodPath) : String.join("", CONTEXT_PATH, methodPath);
            final Route route = router.route(httpMethod, path);
            setMediaType(route, methodConsumes == null ? baseConsumes : methodConsumes, false);
            setMediaType(route, methodProduces == null ? baseProduces : methodProduces, true);
            if (null != method.getAnnotation(Protected.class)) {
                if (null != clazzes) {
                    final Class<? extends BaseHandler> _clazz = getAuthProvider();
                    try {
                        if (null != _clazz) {
                            //final Method authMethod = _clazz.getMethod("create", Vertx.class);
                            //route.handler((Handler<RoutingContext>) authMethod.invoke(null, vertx));
                            route.handler((Handler<RoutingContext>) _clazz.getDeclaredConstructor(Vertx.class).newInstance(vertx));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new RuntimeException("There is no Auth handler configured");
                    }
                }
            }
            System.out.println(httpMethod.name() + " " + route.getPath());
            createHandler(clazz, vertx, method, route);
        });
    }

    private static Class<? extends BaseHandler> getAuthProvider() {
        for (Class<? extends BaseHandler> clazz : clazzes) {
            if (clazz.isAnnotationPresent(AuthProvider.class)) {
                return clazz;
            }
        }
        return null;
    }

    private static void createHandler(final Class<? extends BaseHandler> clazz, final Vertx vertx, final Method method,
                                      final Route route) {
        route.handler(PreHandler.create(vertx))
                .handler(ctx -> {
                    try {
                        // TODO: Need to see a better way of doing this
                        final long timeInMillis = DateUtil.getTimeInMS();
                        final String traceId = ResponseUtil.getHeaderValue(ctx, Configuration.TRACE_ID);
                        logger.info(String.join(" ", "TraceID [", traceId, "] : Started executing method", method.getDeclaringClass().getName() + "." + method.getName()));
                        ResponseUtil.setCookiesForLogging(ctx, method.getDeclaringClass().getName() + "." + method.getName(), timeInMillis);
                        method.invoke(clazz.getDeclaredConstructor(Vertx.class).newInstance(vertx), ctx);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }).handler(PostHandler.create(vertx));
    }

    private static void setMediaType(final Route route, final String[] mediaTypes, final boolean isProduced) {
        if (null != mediaTypes && mediaTypes.length > 0) {
            Arrays.asList(mediaTypes).forEach(contentType -> {
                if (!isProduced) {
                    route.consumes(contentType);
                } else {
                    route.produces(contentType);
                }
            });
        }
    }

    private static Set<Method> getMethodsAnnotatedWith(final Class<?> type,
                                                       final Class<? extends Annotation> annotation) {
        final List<String> methodNames = new ArrayList<>();
        final Set<Method> methods = new HashSet<Method>();
        Class<?> klass = type;
        while (klass != Object.class) {
            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
            for (final Method method : allMethods) {
                if (method.isAnnotationPresent(annotation)) {
                    if (!methodNames.contains(method.getName())) {
                        methods.add(method);
                        methodNames.add(method.getName());
                    }
                }
            }
            klass = klass.getSuperclass();
        }
        return methods;
    }
}