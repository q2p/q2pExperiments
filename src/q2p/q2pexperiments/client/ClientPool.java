package q2p.q2pexperiments.client;

import java.util.ArrayList;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import q2p.q2pexperiments.HubStatus;
import org.bukkit.event.player.PlayerQuitEvent;

public final class ClientPool {
	static final ArrayList<ClientInfo> clients = new ArrayList<ClientInfo>();
	public static final ArrayList<ClientInfo> online = new ArrayList<ClientInfo>();
	static final ArrayList<ClientInfo> offline = new ArrayList<ClientInfo>();
	public static ClientInfo mainAdmin = null;
	
	public static final ClientInfo findClient(final String name, final boolean onlineFirst) {
		ArrayList<ClientInfo> s1 = online;
		ArrayList<ClientInfo> s2 = offline;
		while(s1 != null){
			for(int i = s1.size()-1; i != -1; i--) {
				if(s1.get(i).name.equalsIgnoreCase(name)) {
					return s1.get(i);
				}
			}
			s1 = s2;
			s2 = null;
		}
		return null;
	}

	public static final ClientInfo getClient(final Player player) {
		if(!player.hasMetadata("cli")) return null;
		return (ClientInfo)player.getMetadata("cli").get(0).value();
	}
	
	static final ClientInfo initClient(final Player player) {
		ClientInfo cli = findClient(player.getName(), false);
		if(cli == null) {
			cli = new ClientInfo();
			cli.name = player.getName().toLowerCase();
			clients.add(cli);
			offline.add(cli);
		}
		cli.player = player;
		if(player.hasMetadata("cli"))
			player.removeMetadata("cli", HubStatus.plugin);
		if(!player.hasMetadata("cli"))
			player.setMetadata("cli", new CliMeta(cli));
		offline.remove(cli);
		online.add(cli);
		return cli;
	}
	
	public static final void initilize() {
		
	}
	// Является ли игрок главным админом или сервером
	public static final boolean isAdmin(final ClientInfo cli) {
		return cli == mainAdmin;
	}
	public static final boolean isAdmin(final CommandSender sender) {
		if(sender instanceof Player)
			return mainAdmin != null && sender.getName().equalsIgnoreCase(mainAdmin.name);
		
		return sender.isOp();
	}
	// Разрешить игроку подключится?
	public static void acceptPlayer(PlayerLoginEvent event) {
		//ClientInfo cli = findClient(event.getPlayer().getName(), true);
		//if(cli != null && cli.player != null) {
			//event.disallow(Result.KICK_OTHER, "Player with name \""+event.getPlayer().getName()+"\" is already on server.");
			//return;
		//}
		
		//cli = initClient(event.getPlayer());
	}
	// Отключение игрока
	public static void onExit(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		//ClientInfo cli = ClientPool.getClient(event.getPlayer());
		//cli.player.setGameMode(GameMode.SURVIVAL);
		//cli.player = null;
		//online.remove(cli);
		//offline.add(cli);
	}

	
	public static void onJoin(final PlayerJoinEvent event) {
		// TODO Auto-generated method stub
		event.setJoinMessage(null);
	}

	public static void deInitilize() {
		// TODO Auto-generated method stub
		
	}
}