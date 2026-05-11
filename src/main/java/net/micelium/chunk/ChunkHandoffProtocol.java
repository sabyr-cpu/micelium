package net.micelium.chunk;

import net.micelium.network.CentralServerClient;
import net.micelium.network.PeerConnectionManager;
import net.micelium.proto.ChunkData;
import net.micelium.proto.ChunkTransfer;

/**
 * Serializes and transfers chunk ownership between peers.
 *
 * <p>Chunk handoff should be atomic from the perspective of the ownership registry: a chunk is
 * serialized, transferred, acknowledged, and only then marked as moved.
 */
public final class ChunkHandoffProtocol {

	private final ChunkSerializer chunkSerializer;
	private final OwnershipTracker ownershipTracker;
	private final PeerConnectionManager peerConnectionManager;
	private final CentralServerClient centralServerClient;

	public ChunkHandoffProtocol(
		ChunkSerializer chunkSerializer,
		OwnershipTracker ownershipTracker,
		PeerConnectionManager peerConnectionManager,
		CentralServerClient centralServerClient
	) {
		this.chunkSerializer = chunkSerializer;
		this.ownershipTracker = ownershipTracker;
		this.peerConnectionManager = peerConnectionManager;
		this.centralServerClient = centralServerClient;
	}

	public void transferChunk(String fromNodeId, String toNodeId, String chunkKey) {
		// TODO: Serialize to NBT, gzip the payload, wrap it in ChunkData, and await acknowledgment.
	}

	public void acceptTransfer(ChunkTransfer transfer) {
		// TODO: Validate the incoming transfer, persist it locally, and update ownership atomically.
	}

	public ChunkData serializeChunk(String chunkKey) {
		// TODO: Serialize the world chunk to the protobuf payload.
		return null;
	}

	public ChunkSerializer chunkSerializer() {
		return chunkSerializer;
	}

	public OwnershipTracker ownershipTracker() {
		return ownershipTracker;
	}

	public PeerConnectionManager peerConnectionManager() {
		return peerConnectionManager;
	}

	public CentralServerClient centralServerClient() {
		return centralServerClient;
	}
}