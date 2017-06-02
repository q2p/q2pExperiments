package q2p.q2pexperiments;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.bukkit.util.CachedServerIcon;

public final class IconsRenderer {
	private static final byte[][][] SKELETONS = new byte[][][] {{
		{1,1,1,1,1,1,1,1},
		{1,0,0,0,0,0,0,1},
		{1,0,2,2,2,2,0,1},
		{1,0,2,0,2,0,0,1},
		{1,0,2,2,2,2,0,1},
		{1,0,0,0,2,0,0,1},
		{1,0,0,0,0,0,0,1},
		{1,1,1,1,1,1,1,1}
	},{
		{1,1,1,1,1,1,1,1},
		{1,2,2,2,2,2,2,1},
		{1,2,0,0,0,0,2,1},
		{1,2,0,2,0,2,2,1},
		{1,2,0,0,0,0,2,1},
		{1,2,2,2,0,2,2,1},
		{1,2,2,2,2,2,2,1},
		{1,1,1,1,1,1,1,1}
	}};
	private static final short[][] COLORS = new short[][] {
		{  0, 127,   0},
		{  0, 127, 255},
		{127,   0, 255}
	};
	
	public static final CachedServerIcon[] ICONS = new CachedServerIcon[COLORS.length * SKELETONS.length];
	
	static {
		final byte SIZE = (byte) SKELETONS[0].length;
		final byte PIXEL = (byte)(64/SIZE);
		final BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
		final Graphics g = img.getGraphics();
		final Color[] cs = new Color[] { null, new Color(0,0,0), new Color(255,255,255) };
		
		byte iconsPos = 0;
		
		for(byte c = (byte)(COLORS.length-1); c != -1; c--) {
			cs[0] = new Color(COLORS[c][0], COLORS[c][1], COLORS[c][2]);
			for(byte s = (byte)(SKELETONS.length-1); s != -1; s--) {
				for(byte y = (byte)(SIZE-1); y != -1; y--) {
				for(byte x = (byte)(SIZE-1); x != -1; x--) {
					g.setColor(cs[SKELETONS[s][y][x]]);
					g.fillRect(x*PIXEL, y*PIXEL, PIXEL, PIXEL);
				}
				}
				try {
					ICONS[iconsPos++] = HubStatus.server.loadServerIcon(img);
				} catch(final Exception e) {}
			}
		}
	}
}