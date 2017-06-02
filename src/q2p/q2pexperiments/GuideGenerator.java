package q2p.q2pexperiments;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class GuideGenerator {
	static boolean parseCommand(final Command command, final CommandSender sender, final String[] arguments) {
		if(!command.getName().equals("howto"))
			return false;
		String message = ChatColor.WHITE + "To see specific article type \""+ChatColor.YELLOW+"/howto <article>"+ChatColor.WHITE+"\"\n";
		message += ChatColor.BLUE + "Articles:\n";
		message += ChatColor.YELLOW + "about" + ChatColor.WHITE + ": tells about server.\n";
		message += ChatColor.YELLOW + "acount" + ChatColor.WHITE + ": tells about acount system on a server.\n";
		message += ChatColor.YELLOW + "building" + ChatColor.WHITE + ": tells about building stuff on a server.\n";
		message += ChatColor.YELLOW + "commutication" + ChatColor.WHITE + ": tells about players interactions between them.\n";
		message += ChatColor.YELLOW + "news" + ChatColor.WHITE + ": tells about news system.\n";
		sender.sendMessage(message);
		return true;
	}
}