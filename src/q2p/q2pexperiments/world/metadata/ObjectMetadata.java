package q2p.q2pexperiments.world.metadata;

import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import q2p.q2pexperiments.HubStatus;

public final class ObjectMetadata implements MetadataValue {
	private Object value;
	
	public ObjectMetadata(final Object value) {
		this.value = value;
	}

	public final void set(final Object value) {
		this.value = value;
	}
	
	public final Object value() { return value; }

	public final Plugin getOwningPlugin() { return HubStatus.plugin; }

	public final boolean asBoolean() { return false; }
	public final byte asByte() { return 0; }
	public final double asDouble() { return 0; }
	public final float asFloat() { return 0; }
	public final int asInt() { return 0; }
	public final long asLong() { return 0; }
	public final short asShort() { return 0; }
	public final String asString() { return null; }
	public final void invalidate() {}
}