package net.micelium;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.micelium.config.MiceliumConfig;
import net.micelium.lifecycle.BootstrapManager;

/**
 * Fabric entrypoint for Micelium.
 *
 * <p>This mod is intentionally split into thin service classes so that chunk ownership,
 * peer discovery, and persistence logic can evolve independently.
 */
public final class MiceliumMod implements ModInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger("micelium");

	private final MiceliumConfig config;
	private final BootstrapManager bootstrapManager;

	public MiceliumMod() {
		this(MiceliumConfig.load(FabricLoader.getInstance().getConfigDir().resolve("micelium.json")));
	}

	MiceliumMod(MiceliumConfig config) {
		this.config = config;
		this.bootstrapManager = new BootstrapManager(config);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Micelium skeleton at {}", config.centralServerAddress());

		// TODO: Connect to the central coordinator, fetch peers, then start the ownership and
		// heartbeat subsystems once the world join lifecycle is available.
		bootstrapManager.initialize();
	}
}