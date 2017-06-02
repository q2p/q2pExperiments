package q2p.q2pexperiments.rings;

import java.util.LinkedList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class MagicRing {
	private static final List<String> LORE = new LinkedList<String>();
	static {
		LORE.add(ChatColor.AQUA+"Magic Ring");
	}
	public final String name;
	
	protected MagicRing(final String name) {
		this.name = name;
	}
	
	public final boolean is(final ItemStack item) {
		if(item == null || !item.hasItemMeta() || Material.BOWL != item.getType())
			return false;
		
		final ItemMeta meta = item.getItemMeta();
		
		return meta.hasEnchant(Enchantment.DURABILITY) && meta.isUnbreakable() && name.equals(meta.getDisplayName()) && LORE.equals(meta.getLore());
	}

	public final ItemStack fabric() {
		final ItemStack item = new ItemStack(Material.BOWL, 1);
		
		final ItemMeta meta = item.getItemMeta();
		
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		
		meta.setDisplayName(name);
		
		meta.setLore(LORE);
		
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
		
		meta.setUnbreakable(true);
				
		item.setItemMeta(meta);
		
		return item;
	}
}