package q2p.q2pexperiments.client;

import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import q2p.q2pexperiments.HubStatus;

public final class CliMeta implements MetadataValue {
	final ClientInfo clientInfo;
	
	public CliMeta(final ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}
	
	public final Object value() {
		return clientInfo;
	}
	
	public final boolean asBoolean() {return false;}
	public final byte asByte() {return 0;}
	public final double asDouble() {return 0;}
	public final float asFloat() {return 0;}
	public final int asInt() {return 0;}
	public final long asLong() {return 0;}
	public final short asShort() {return 0;	}
	public final String asString() {return null;}
	public final Plugin getOwningPlugin() {return HubStatus.plugin;}
	public final void invalidate() {}
}
