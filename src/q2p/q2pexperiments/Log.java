package q2p.q2pexperiments;

import java.util.logging.Logger;
import org.bukkit.Server;

public final class Log {
	private static Logger logger;
	private static Server server;

	public static final void say(final String message) {
		server.broadcastMessage(message);
	}
	public static final void info(final String message) {
		logger.info(message);
	}
	public static final void warn(final String message) {
		logger.warning(message);
	}

	public static final void load() {
		server = HubStatus.server;
		logger = Logger.getGlobal();
	}
	public static final void unload() {
		logger = null;
		server = null;
	}
}