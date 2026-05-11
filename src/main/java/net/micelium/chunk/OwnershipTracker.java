package net.micelium.chunk;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tracks the local view of chunk ownership.
 *
 * <p>The authoritative mapping comes from the central coordinator, but gameplay code should only
 * depend on this local cache to avoid blocking on network lookups.
 */
public final class OwnershipTracker {

	private final Map<String, String> ownershipByChunkKey = new ConcurrentHashMap<>();

	public void replaceAll(Map<String, String> ownershipMap) {
		// TODO: Apply a consistent snapshot update from the coordinator.
		ownershipByChunkKey.clear();
		ownershipByChunkKey.putAll(ownershipMap);
	}

	public void updateOwnership(String chunkKey, String ownerNodeId) {
		ownershipByChunkKey.put(chunkKey, ownerNodeId);
	}

	public String getOwner(String chunkKey) {
		return ownershipByChunkKey.get(chunkKey);
	}

	public boolean isOwnedLocally(String chunkKey, String localNodeId) {
		return localNodeId != null && localNodeId.equals(ownershipByChunkKey.get(chunkKey));
	}

	public Map<String, String> snapshot() {
		return Collections.unmodifiableMap(ownershipByChunkKey);
	}
}