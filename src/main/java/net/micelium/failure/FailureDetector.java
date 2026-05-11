package net.micelium.failure;

import net.micelium.chunk.ChunkRouter;
import net.micelium.chunk.OwnershipTracker;
import net.micelium.config.MiceliumConfig;
import net.micelium.network.CentralServerClient;
import net.micelium.network.PeerConnectionManager;

/**
 * Reacts to peer disconnects and ownership loss.
 *
 * <p>When a peer disappears, the system should reconcile orphaned chunks with the coordinator and
 * recover any nearby data from cold storage.
 */
public final class FailureDetector {

	private final MiceliumConfig config;
	private final CentralServerClient centralServerClient;
	private final PeerConnectionManager peerConnectionManager;
	private final OwnershipTracker ownershipTracker;
	private final ChunkRouter chunkRouter;

	public FailureDetector(
		MiceliumConfig config,
		CentralServerClient centralServerClient,
		PeerConnectionManager peerConnectionManager,
		OwnershipTracker ownershipTracker,
		ChunkRouter chunkRouter
	) {
		this.config = config;
		this.centralServerClient = centralServerClient;
		this.peerConnectionManager = peerConnectionManager;
		this.ownershipTracker = ownershipTracker;
		this.chunkRouter = chunkRouter;
	}

	public void initialize() {
		// TODO: Subscribe to disconnect events and reclaim orphaned chunks as they appear.
		peerConnectionManager.addDisconnectListener(this::onPeerDisconnected);
	}

	public void onPeerDisconnected(String peerId) {
		// TODO: Query the coordinator for orphaned chunks and recover nearby ones from storage.
	}

	public MiceliumConfig config() {
		return config;
	}

	public CentralServerClient centralServerClient() {
		return centralServerClient;
	}

	public OwnershipTracker ownershipTracker() {
		return ownershipTracker;
	}

	public ChunkRouter chunkRouter() {
		return chunkRouter;
	}
}