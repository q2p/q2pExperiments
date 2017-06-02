package q2p.q2pexperiments.rings;

import java.util.LinkedList;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import q2p.q2pexperiments.world.Space;

public class LightningRing extends MagicRing {
	public static final MagicRing RING = new LightningRing();
	
	private LightningRing() {
		super(ChatColor.RESET+""+ChatColor.BLUE+"Lightning Ring");
	}

	private static final double distance = 1.5;
	private static final double length = 80;
	private static final double damage = 50;
	public static final void castAbove(final Player player) {
		Block block = player.getLocation().add(0, -0.1, 0).getBlock();
		final int[] standsOn = new int[] {block.getX(), block.getY()};
		final Location location = player.getEyeLocation();
		final World world = location.getWorld();
		
		final int max = world.getMaxHeight();

		location.setY(max);
		location.setPitch(0);
		
		final BlockIterator iterator = new BlockIterator(location);
		while(iterator.hasNext()) {
			block = iterator.next();
			
			if(location.distance(block.getLocation().add(0.5, 1, 0.5)) > length)
				break;
			
			if(block.getX() == standsOn[0] && block.getY() == standsOn[1])
				continue;
						
			strike(player, world, max, Space.dropTo(world, block.getX(), max, block.getZ()));
		}
				
		passedEntities.clear();
		passedBlocks.clear();
	}
	public static final void castAim(final Player player) {
		final Block standsOn = player.getLocation().add(0, -0.1, 0).getBlock();
		final Location location = player.getEyeLocation();
		
		final Location floorStart = location.clone();
		floorStart.setY(0);
		
		final Location floorEnd = Space.traceTo(location, length).getLocation().add(0.5, 0, 0.5);
		floorEnd.setY(0);
		
		final double limitSq = floorStart.distanceSquared(floorEnd);
		
		final World world = location.getWorld();
		
		final int max = world.getMaxHeight();

		final BlockIterator iterator = new BlockIterator(location);
		while(iterator.hasNext()) {
			Block block = iterator.next();
			
			final Location floorTarget = block.getLocation();
			floorTarget.add(0.5, 0, 0.5);
			floorTarget.setY(0);
			if(floorStart.distanceSquared(floorTarget) > limitSq)
				break;
			
			block = Space.dropTo(world, block.getX(), block.getY(), block.getZ());
			
			if(block == standsOn)
				continue;
						
			strike(player, world, max, block);
		}
		
		passedEntities.clear();
		passedBlocks.clear();
	}

	private static final LinkedList<LivingEntity> passedEntities = new LinkedList<LivingEntity>();
	private static final LinkedList<Block> passedBlocks = new LinkedList<Block>();
	private static final void strike(final Player caster, final World world, final double max, final Block block) {
		if(block == null || passedBlocks.contains(block))
			return;
		
		passedBlocks.addLast(block);
		
		final Location location = block.getLocation().add(0.5, 1, 0.5);
		
		for(final Entity e : world.getNearbyEntities(location, distance, max, distance)) {
			if(!(e instanceof LivingEntity))
				continue;
			
			LivingEntity le = (LivingEntity) e;
			if(le == caster || le.getLocation().getY() < location.getY() || le.isDead() || passedEntities.contains(le))
				continue;

			le.setFireTicks(10);
			
			le.damage(damage);
			
			passedEntities.addLast(le);
		}
		
		world.strikeLightning(location);
	}
}