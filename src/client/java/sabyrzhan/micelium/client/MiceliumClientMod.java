package sabyrzhan.micelium.client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MiceliumClientMod implements ClientModInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger("micelium-client");

	@Override
	public void onInitializeClient() {
		LOGGER.info("Micelium client initialized");
	}
}
