package q2p.q2pexperiments.world;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UsableItem {
	public final Material material;
	public final String name;
	
	public UsableItem(final String name, final Material material) {
		this.name = ChatColor.RESET+name;
		this.material = material;
	}
	
	public final boolean is(final ItemStack item) {
		return item != null && item.hasItemMeta() && name.equals(item.getItemMeta().getDisplayName()) && material == item.getType();
	}

	public final ItemStack fabric() {
		final ItemStack item = new ItemStack(material, 1);
		
		final ItemMeta meta = item.getItemMeta();
		
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		
		meta.setDisplayName(name);
		
		meta.setUnbreakable(true);
		
		item.setItemMeta(meta);
		
		return item;
	}
}