package net.micelium.network;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.micelium.chunk.OwnershipTracker;
import net.micelium.config.MiceliumConfig;
import net.micelium.proto.ChunkData;
import net.micelium.proto.OwnershipUpdate;

/**
 * gRPC client for the central coordinator.
 *
 * <p>The coordinator owns discovery, authoritative ownership updates, and cold storage; it does
 * not execute gameplay logic.
 */
public final class CentralServerClient {

	private final MiceliumConfig config;

	public CentralServerClient(MiceliumConfig config) {
		this.config = config;
	}

	public List<String> fetchPeerList() {
		// TODO: Fetch the current online peer list from the coordinator.
		return Collections.emptyList();
	}

	public Map<String, String> fetchOwnershipMap() {
		// TODO: Fetch the authoritative chunk ownership registry.
		return Collections.emptyMap();
	}

	public void subscribeToOwnershipUpdates(OwnershipTracker ownershipTracker) {
		// TODO: Stream OwnershipUpdate messages and apply them atomically.
	}

	public void publishOwnershipUpdate(OwnershipUpdate update) {
		// TODO: Broadcast a registry change to the central coordinator.
	}

	public void uploadColdStorageChunk(ChunkData chunkData) {
		// TODO: Upload inactive chunk data to the coordinator's cold storage tier.
	}

	public MiceliumConfig config() {
		return config;
	}
}