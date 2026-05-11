package net.micelium.config;

import java.nio.file.Path;

/**
 * Immutable mod configuration.
 *
 * <p>The full project will likely load this from JSON or TOML, but the skeleton keeps the
 * configuration surface explicit so the networking and bootstrap layers have a stable contract.
 */
public final class MiceliumConfig {

	private final String centralServerAddress;
	private final int centralServerPort;
	private final int heartbeatIntervalSeconds;
	private final int remoteChunkCacheSize;
	private final int persistenceIntervalSeconds;

	public MiceliumConfig(
		String centralServerAddress,
		int centralServerPort,
		int heartbeatIntervalSeconds,
		int remoteChunkCacheSize,
		int persistenceIntervalSeconds
	) {
		this.centralServerAddress = centralServerAddress;
		this.centralServerPort = centralServerPort;
		this.heartbeatIntervalSeconds = heartbeatIntervalSeconds;
		this.remoteChunkCacheSize = remoteChunkCacheSize;
		this.persistenceIntervalSeconds = persistenceIntervalSeconds;
	}

	public static MiceliumConfig load(Path configPath) {
		// TODO: Read persisted configuration from disk and validate the network endpoints.
		return defaults();
	}

	public static MiceliumConfig defaults() {
		return new MiceliumConfig("127.0.0.1", 50051, 5, 512, 60);
	}

	public String centralServerAddress() {
		return centralServerAddress;
	}

	public int centralServerPort() {
		return centralServerPort;
	}

	public int heartbeatIntervalSeconds() {
		return heartbeatIntervalSeconds;
	}

	public int remoteChunkCacheSize() {
		return remoteChunkCacheSize;
	}

	public int persistenceIntervalSeconds() {
		return persistenceIntervalSeconds;
	}
}