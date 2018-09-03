package com.greyseal.vertx.boot.util;

import com.greyseal.vertx.boot.Constant.Configuration;
import io.vertx.reactivex.ext.web.Cookie;
import io.vertx.reactivex.ext.web.RoutingContext;
import java.util.ArrayList;
import java.util.List;

public class ResponseUtil {

    public static String getHeaderValue(final RoutingContext event, final String headerName) {
        final String headerVal = event.request().getHeader(headerName);
        if (null != headerVal) {
            return headerVal;
        }
        return event.request().response().headers().get(headerName);
    }

    public static String getCookieValue(final RoutingContext event, final String name) {
        Cookie cookie = event.getCookie(name);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    public static void setCookiesForLogging(final RoutingContext event, final String value, final long timeInMillis) {
        Cookie cookie = Cookie.cookie(Configuration.COOKIE_METHOD, value);
        event.addCookie(cookie);
        cookie = Cookie.cookie(Configuration.COOKIE_DATE, timeInMillis + "");
        event.addCookie(cookie);
    }

    public static <T> List<T> toList(T object) {
        List<T> list = new ArrayList<T>();
        list.add(object);
        return list;
    }
}
