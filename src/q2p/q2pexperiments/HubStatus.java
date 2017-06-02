package q2p.q2pexperiments;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import q2p.q2pexperiments.client.ClientPool;

public class HubStatus {
	private static LogicTick logicTick = null;
	private static int logicTickId = -1;

	public static JavaPlugin plugin = null;
	public static Server server = null;
	
	static final void initilize(final JavaPlugin plugin) {
		HubStatus.plugin = plugin;
		server = plugin.getServer();
		
		Log.load();
		
		Config.load();
		
		ClientPool.initilize();
				
		logicTick = new LogicTick();
    logicTickId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, logicTick, 0, 1);
	}
	
	static final void deInitilize() {
		Config.unload();
		Log.unload();
		ClientPool.deInitilize();
		plugin.getServer().getScheduler().cancelTask(logicTickId);
		logicTickId = -1;
		logicTick = null;
		server = null;
		plugin = null;
	}
}
