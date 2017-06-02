package q2p.q2pexperiments;

import org.bukkit.configuration.file.FileConfiguration;

public final class Config {
	public static FileConfiguration cfg = HubStatus.plugin.getConfig();
	
	public static final void save() {
		HubStatus.plugin.saveConfig();
	}
	
	public static final void load() {
		cfg = HubStatus.plugin.getConfig();
		cfg.addDefault("testingVar", true);
		cfg.options().copyDefaults(true);
		save();
	}
	
	public static final void unload() {
		cfg = null;
	}
}