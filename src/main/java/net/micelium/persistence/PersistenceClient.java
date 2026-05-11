package net.micelium.persistence;

import net.micelium.chunk.OwnershipTracker;
import net.micelium.config.MiceliumConfig;
import net.micelium.network.CentralServerClient;
import net.micelium.proto.ChunkData;

/**
 * Periodically uploads active chunks to cold storage.
 *
 * <p>This client keeps the coordinator's backup tier current even when no player is nearby to
 * keep a chunk live on a peer node.
 */
public final class PersistenceClient {

	private final MiceliumConfig config;
	private final CentralServerClient centralServerClient;
	private final OwnershipTracker ownershipTracker;

	public PersistenceClient(
		MiceliumConfig config,
		CentralServerClient centralServerClient,
		OwnershipTracker ownershipTracker
	) {
		this.config = config;
		this.centralServerClient = centralServerClient;
		this.ownershipTracker = ownershipTracker;
	}

	public void uploadChunk(ChunkData chunkData) {
		// TODO: Upload the chunk payload to the central backup store.
	}

	public void flush() {
		// TODO: Flush any pending chunk uploads before shutdown.
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
}