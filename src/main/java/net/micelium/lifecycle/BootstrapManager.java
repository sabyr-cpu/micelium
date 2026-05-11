package net.micelium.lifecycle;

import net.micelium.chunk.ChunkRouter;
import net.micelium.chunk.OwnershipTracker;
import net.micelium.config.MiceliumConfig;
import net.micelium.failure.FailureDetector;
import net.micelium.network.CentralServerClient;
import net.micelium.network.PeerConnectionManager;
import net.micelium.persistence.PersistenceClient;

/**
 * Orchestrates Micelium startup and shutdown.
 *
 * <p>The coordinator-first boot flow is intentionally centralized here so world-join logic,
 * ownership sync, heartbeat setup, and background persistence can be started in a predictable
 * order.
 */
public final class BootstrapManager {

	private final MiceliumConfig config;
	private final CentralServerClient centralServerClient;
	private final PeerConnectionManager peerConnectionManager;
	private final OwnershipTracker ownershipTracker;
	private final ChunkRouter chunkRouter;
	private final PersistenceClient persistenceClient;
	private final FailureDetector failureDetector;

	public BootstrapManager(MiceliumConfig config) {
		this.config = config;
		this.centralServerClient = new CentralServerClient(config);
		this.peerConnectionManager = new PeerConnectionManager(config);
		this.ownershipTracker = new OwnershipTracker();
		this.chunkRouter = new ChunkRouter(ownershipTracker, peerConnectionManager);
		this.persistenceClient = new PersistenceClient(config, centralServerClient, ownershipTracker);
		this.failureDetector = new FailureDetector(config, centralServerClient, peerConnectionManager, ownershipTracker, chunkRouter);
	}

	public void initialize() {
		// TODO: On world join, connect to the coordinator, hydrate peer/ownership state, then
		// wire heartbeat and failure detection listeners.
	}

	public void shutdown() {
		// TODO: Stop background workers and drain pending uploads before disconnecting.
	}

	public MiceliumConfig config() {
		return config;
	}

	public CentralServerClient centralServerClient() {
		return centralServerClient;
	}

	public PeerConnectionManager peerConnectionManager() {
		return peerConnectionManager;
	}

	public OwnershipTracker ownershipTracker() {
		return ownershipTracker;
	}

	public ChunkRouter chunkRouter() {
		return chunkRouter;
	}

	public PersistenceClient persistenceClient() {
		return persistenceClient;
	}

	public FailureDetector failureDetector() {
		return failureDetector;
	}
}