package q2p.q2pexperiments.world.metadata;

import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import q2p.q2pexperiments.HubStatus;

public final class StringMetadata implements MetadataValue {
	private String value;
	
	public StringMetadata(final String value) {
		this.value = value;
	}

	public final void set(final String value) {
		this.value = value;
	}

	public final String asString() { return value; }
	
	public final Plugin getOwningPlugin() { return HubStatus.plugin; }
	
	public final boolean asBoolean() { return false; }
	public final byte asByte() { return 0; }
	public final double asDouble() { return 0; }
	public final float asFloat() { return 0; }
	public final int asInt() { return 0; }
	public final long asLong() { return 0; }
	public final short asShort() { return 0; }
	public final Object value() { return null; }
	public final void invalidate() {}
}