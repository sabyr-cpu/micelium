package sabyrzhan.micelium.client.world;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public final class MiceliumWorldStorage {

	private static final Logger LOGGER = LoggerFactory.getLogger("micelium-client");
	private static final Path SAVE_PATH =
		FabricLoader.getInstance().getConfigDir().resolve("micelium-worlds.json");
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	private final List<MiceliumWorldEntry> worlds = new ArrayList<>();

	public MiceliumWorldStorage() {
		load();
	}

	public List<MiceliumWorldEntry> getWorlds() {
		return Collections.unmodifiableList(worlds);
	}

	public void add(MiceliumWorldEntry entry) {
		worlds.add(entry);
		save();
	}

	public void update(int index, MiceliumWorldEntry entry) {
		worlds.set(index, entry);
		save();
	}

	public void remove(int index) {
		worlds.remove(index);
		save();
	}

	private void load() {
		if (!Files.exists(SAVE_PATH)) return;
		try (Reader reader = Files.newBufferedReader(SAVE_PATH)) {
			JsonArray arr = JsonParser.parseReader(reader).getAsJsonArray();
			for (JsonElement el : arr) {
				JsonObject obj = el.getAsJsonObject();
				worlds.add(new MiceliumWorldEntry(
					obj.get("name").getAsString(),
					obj.get("address").getAsString(),
					obj.get("port").getAsInt()
				));
			}
		} catch (IOException | JsonParseException e) {
			LOGGER.warn("Failed to load micelium-worlds.json: {}", e.getMessage());
		}
	}

	private void save() {
		JsonArray arr = new JsonArray();
		for (MiceliumWorldEntry w : worlds) {
			JsonObject obj = new JsonObject();
			obj.addProperty("name", w.name());
			obj.addProperty("address", w.address());
			obj.addProperty("port", w.port());
			arr.add(obj);
		}
		try (Writer writer = Files.newBufferedWriter(SAVE_PATH)) {
			GSON.toJson(arr, writer);
		} catch (IOException e) {
			LOGGER.warn("Failed to save micelium-worlds.json: {}", e.getMessage());
		}
	}
}
