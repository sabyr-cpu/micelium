package net.micelium.chunk;

import net.micelium.network.PeerConnectionManager;

/**
 * Routes chunk loads to the correct owner.
 *
 * <p>Local chunks should resolve normally, while remote chunks are requested from the owning peer
 * and cached read-only on the requester.
 */
public final class ChunkRouter {

	private final OwnershipTracker ownershipTracker;
	private final PeerConnectionManager peerConnectionManager;

	public ChunkRouter(OwnershipTracker ownershipTracker, PeerConnectionManager peerConnectionManager) {
		this.ownershipTracker = ownershipTracker;
		this.peerConnectionManager = peerConnectionManager;
	}

	public void routeChunkLoad(int chunkX, int chunkZ) {
		// TODO: Resolve the owner and dispatch a remote ChunkRequest when necessary.
	}

	public void cacheRemoteChunk(String chunkKey, ChunkSerializer.SerializedChunk serializedChunk) {
		// TODO: Store remote chunks in a read-only local cache.
	}

	public OwnershipTracker ownershipTracker() {
		return ownershipTracker;
	}

	public PeerConnectionManager peerConnectionManager() {
		return peerConnectionManager;
	}
}