package server;

import Util.Configuration.Configuration;
import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
	public static void main(String[] args) {
		Spark.port(getHerokuAssignedPort());
		Router.init();
		DebugScreen.enableDebugScreen();
	}

	private static int getHerokuAssignedPort() {
		String herokuPort = System.getenv(Configuration.ENVIRONMENT_PATH_PORT);
		if (herokuPort != null) {
			return Integer.parseInt(herokuPort);
		}
		return Configuration.PUERTO_DEFAULT;
	}
}
