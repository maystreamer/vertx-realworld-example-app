package com.greyseal.vertx.boot;


import com.greyseal.vertx.boot.config.VertxBootConfig;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.impl.launcher.VertxCommandLauncher;
import io.vertx.core.impl.launcher.VertxLifecycleHooks;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.SLF4JLogDelegateFactory;

public class AppLauncher extends VertxCommandLauncher implements VertxLifecycleHooks {

	// make sure you have the logger
	static {
		if (System.getProperty("vertx.logger-delegate-factory-class-name") == null) {
			System.setProperty("vertx.logger-delegate-factory-class-name",
					SLF4JLogDelegateFactory.class.getCanonicalName());
		}
	}

	@Override
	public void afterConfigParsed(JsonObject config) {
		if(null == config || config.isEmpty())
			throw new RuntimeException("No configuration provided");
		VertxBootConfig.INSTANCE.setConfig(config);

	}

	@Override
	public void beforeStartingVertx(VertxOptions options) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterStartingVertx(Vertx vertx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeDeployingVerticle(DeploymentOptions deploymentOptions) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeStoppingVertx(Vertx vertx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterStoppingVertx() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleDeployFailed(Vertx vertx, String mainVerticle, DeploymentOptions deploymentOptions,
			Throwable cause) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		new AppLauncher().dispatch(args);
	}

	public static void executeCommand(String cmd, String... args) {
		new AppLauncher().execute(cmd, args);
	}
}
