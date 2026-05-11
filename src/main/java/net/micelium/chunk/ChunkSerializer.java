package net.micelium.chunk;

import net.micelium.proto.ChunkData;

/**
 * Converts between Minecraft chunk state and the wire representation.
 *
 * <p>The final implementation will serialize NBT, compress it, and wrap the bytes in protobuf
 * messages so both peer-to-peer and coordinator transfers use the same payload format.
 */
public final class ChunkSerializer {

	public SerializedChunk serialize(String chunkKey) {
		// TODO: Read the chunk from the world and encode it into a compressed payload.
		return new SerializedChunk(null, null);
	}

	public void deserialize(ChunkData chunkData) {
		// TODO: Decode the compressed NBT payload and apply it to the target world.
	}

	public record SerializedChunk(String chunkKey, byte[] compressedNbt) {
	}
}