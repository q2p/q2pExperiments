package q2p.q2pexperiments.world;

import java.util.LinkedList;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public final class Space {
	public static final float modelHeight = 1.875f;
	public static final float legsHeadDiam = 0.5625f;
	public static final float legsHeadRadius = legsHeadDiam*0.5f;
	public static final float legsHeadStandDiam = 0.3f;
	public static final float legsHeadStandRadius = legsHeadStandDiam*0.5f;
	
	public static final Location rayTrace(final Location start, final Vector ray, final double startX, final double startY, final double startZ, final double endX, final double endY, final double endZ) {
		final double fracX = 1.0 / ray.getX();
		final double fracY = 1.0 / ray.getY();
		final double fracZ = 1.0 / ray.getZ();
		
		final double t1 = (startX - start.getX())*fracX;
		final double t2 = (endX   - start.getX())*fracX;
		final double t3 = (startY - start.getY())*fracY;
		final double t4 = (endY   - start.getY())*fracY;
		final double t5 = (startZ - start.getZ())*fracZ;
		final double t6 = (endZ   - start.getZ())*fracZ;
		
		final double tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
		final double tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));
		
		if(tmax >= 0 && tmin <= tmax)
			return start.add(ray.multiply(tmin));
		
		return null;
	}
	public static final Location rayPlayer(final Player source, final Player target) {
		return rayTrace(source.getEyeLocation(), source.getEyeLocation().getDirection().normalize(), target.getLocation().getX() - legsHeadRadius, target.getLocation().getY(), target.getLocation().getZ() - legsHeadRadius, target.getLocation().getX() + legsHeadRadius, target.getLocation().getY() + modelHeight, target.getLocation().getZ() + legsHeadRadius);
	}
	public static final double distanceToAABB(final Location from, final double toX1, final double toY1, final double toZ1, final double toX2, final double toY2, final double toZ2) {
		final double dx = Math.max(Math.max(toX1 - from.getX(), from.getX() - toX2), 0);
		final double dy = Math.max(Math.max(toY1 - from.getY(), from.getY() - toY2), 0);
		final double dz = Math.max(Math.max(toZ1 - from.getZ(), from.getZ() - toZ2), 0);
	  
		return Math.sqrt(dx*dx + dy*dy + dz*dz);
	}
	public static final double distanceToPlayer(final Location from, final Player to) {
		return distanceToAABB(from, to.getLocation().getX() - legsHeadRadius, to.getLocation().getY(), to.getLocation().getZ() - legsHeadRadius, to.getLocation().getX() + legsHeadRadius, to.getLocation().getY() + modelHeight, to.getLocation().getZ() + legsHeadRadius);
	}
	public static final boolean aabbXaabb(double x1, double y1, double z1, double x2, double y2, double z2, double xSize1, double ySize1, double zSize1, double xSize2, double ySize2, double zSize2) {
		return	(Math.abs(x1 - x2) * 2 < (xSize1 + xSize2)) && (Math.abs(y1 - y2) * 2 < (ySize1 + ySize2)) && (Math.abs(z1 - z2) * 2 < (zSize1 + zSize2));
	}
	
	public static final LinkedList<int[]> walkGrid(final int[] p0, final int[] p1) {
		final int dx = p1[0] - p0[0];
		final int dy = p1[1] - p0[1];
		
		final int nx = Math.abs(dx);
		final int ny = Math.abs(dy);
		
		final int signX = dx > 0 ? 1 : -1;
		final int signY = dy > 0 ? 1 : -1;
		
		final LinkedList<int[]> points = new LinkedList<int[]>();
		
		points.add(p0.clone());
		
		for(int ix = 0, iy = 0; ix < nx || iy < ny;) {
			if((ix + 0.5) / nx < (iy + 0.5) / ny) {
				p0[0] += signX;
				ix++;
			} else {
				p0[1] += signY;
				iy++;
			}
			points.add(p0.clone());
		}
		
		return points;
	}
	
	public static final LinkedList<int[]> walkGrid(final int[][] pin) {
		final int[] d = new int[3];
		final int[] n = new int[3];
		final byte[] sign = new byte[3];
		final int[] it = new int[3];
		
		for(byte i = 0; i != 3; i++) {
			d[i] = pin[1][i] - pin[0][i];
			n[i] = Math.abs(d[i]);
			sign[i] = (byte)(d[i] > 0 ? 1 : -1);
			it[i] = 0;
		}
		
		final LinkedList<int[]> points = new LinkedList<int[]>();
		
		final int[] p = pin[0].clone();
		
		points.add(p);
		
		while(true) {
			boolean inPlace = true;
			for(byte i = 0; i != 3; i++) {
				if(it[i] < n[i]) {
					inPlace = false;
					break;
				}
			}
			
			if(inPlace)
				break;

			byte min = 0;
			byte minAmount = 0;
			
			for(byte i = 0, currentMinAmount = 0; i != 3 && minAmount != 2; i++) {
				currentMinAmount = 0;
				final double k = (it[min] + 0.5) / n[min];
				for(byte j = 0; j != 3; j++) {
					if(j == min)
						continue;
					
					if(k < (it[j] + 0.5) / n[j])
						currentMinAmount++;
				}
				
				if(currentMinAmount > minAmount) {
					min = i;
					minAmount = currentMinAmount;
				}
			}
			
			p[min] += sign[min];
			it[min]++;
			
			points.add(p.clone());
		}
		
		return points;
	}
	
	public static final Block traceTo(final Location location, final double maxDistance) {
		final BlockIterator iterator = new BlockIterator(location);
		Block block = location.getBlock();
		while(iterator.hasNext()) {
			block = iterator.next();
			
			if(location.distance(block.getLocation().add(0.5, 1, 0.5)) > maxDistance || !block.getType().isSolid())
				return block;
		}
		
		return block;
	}
	public static final Block dropTo(final World world, final int x, int y, final int z) {
		if(y < 0)
			return null;
		
		final int max = world.getMaxHeight()-1;
		
		if(y > max) {
			y = max;
			if(world.getBlockAt(x, y, z).getType().isSolid())
				return world.getBlockAt(x, y, z);
		}
		
		if(world.getBlockAt(x, y, z).getType().isSolid())
			return null;
		
		while(!world.getBlockAt(x, y, z).getType().isSolid()) {
			if(y == 0)
				return null;
			y--;
		}
		return world.getBlockAt(x, y, z);
	}
}