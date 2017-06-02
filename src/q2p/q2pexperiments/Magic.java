package q2p.q2pexperiments;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import q2p.q2pexperiments.rings.LightningRing;
import q2p.q2pexperiments.world.Caster;

public final class Magic {
	public static final boolean parseCommand(final Command command, final CommandSender sender, final String[] args) {
		if(handCommand(command, sender, args)) return true;
		if(ringCommand(command, sender, args)) return true;
		return false;
	}
	
	private static final boolean handCommand(final Command command, final CommandSender sender, final String[] args) {
		if(!"hand".equals(command.getLabel()) || args.length != 0 || !(sender instanceof Player))
			return false;
		
		final Player player = (Player) sender;
		
		final PlayerInventory inv = player.getInventory();
		
		ItemStack[] items = inv.getStorageContents();
		
		for(final ItemStack item : items) {
			if(Caster.item.is(item))
				return true;
		}
		
		inv.addItem(Caster.item.fabric());
		
		return true;
	}
	
	private static final boolean ringCommand(final Command command, final CommandSender sender, final String[] args) {
		if(!"ring".equals(command.getLabel()) || args.length != 0 || !(sender instanceof Player))
			return false;
		
		((Player)sender).getInventory().addItem(LightningRing.RING.fabric());
		
		return true;
	}
}