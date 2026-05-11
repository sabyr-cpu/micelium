package net.micelium.network;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

import net.micelium.config.MiceliumConfig;
import net.micelium.proto.ChunkRequest;
import net.micelium.proto.HeartbeatPing;

/**
 * Maintains live peer-to-peer connections.
 *
 * <p>This layer is deliberately isolated so the transport can evolve independently of the chunk
 * routing and ownership code.
 */
public final class PeerConnectionManager {

	private final MiceliumConfig config;
	private final Set<Consumer<String>> disconnectListeners = new CopyOnWriteArraySet<>();

	public PeerConnectionManager(MiceliumConfig config) {
		this.config = config;
	}

	public void connectToPeers(List<String> peerAddresses) {
		// TODO: Establish and maintain Netty channels to each advertised peer.
	}

	public void disconnectPeer(String peerId) {
		// TODO: Tear down the channel and notify listeners.
		disconnectListeners.forEach(listener -> listener.accept(peerId));
	}

	public void sendChunkRequest(String peerId, ChunkRequest request) {
		// TODO: Serialize the protobuf request onto the peer channel.
	}

	public void broadcastHeartbeat(HeartbeatPing ping) {
		// TODO: Fan out heartbeat pings to connected peers.
	}

	public void addDisconnectListener(Consumer<String> listener) {
		disconnectListeners.add(listener);
	}

	public void close() {
		// TODO: Close all channels and clear connection state.
	}

	public MiceliumConfig config() {
		return config;
	}
}