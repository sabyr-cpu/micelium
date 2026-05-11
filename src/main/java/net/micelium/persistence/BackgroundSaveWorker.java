package net.micelium.persistence;

/**
 * Background thread wrapper for periodic persistence work.
 */
public final class BackgroundSaveWorker implements Runnable {

	private final PersistenceClient persistenceClient;
	private volatile boolean running;

	public BackgroundSaveWorker(PersistenceClient persistenceClient) {
		this.persistenceClient = persistenceClient;
	}

	public void start() {
		// TODO: Launch a dedicated worker thread or scheduled executor.
		running = true;
	}

	public void stop() {
		// TODO: Stop the worker and perform a final flush.
		running = false;
	}

	@Override
	public void run() {
		// TODO: Periodically upload owned chunks while the worker is running.
		if (running) {
			persistenceClient.flush();
		}
	}
}