package net.micelium.network;

/**
 * Wire-format helpers shared by peer and coordinator transports.
 *
 * <p>The protobuf schema lives in proto/micelium.proto; this class is the place for framing,
 * envelopes, and message-type dispatch.
 */
public final class NetworkProtocol {

	private NetworkProtocol() {
	}

	public static final int PROTOCOL_VERSION = 1;

	public static byte[] encodeEnvelope(int messageType, byte[] payload) {
		// TODO: Prepend message headers and checksum/framing metadata.
		return payload;
	}

	public static byte[] decodeEnvelope(byte[] encodedPayload) {
		// TODO: Strip the transport envelope and validate the payload.
		return encodedPayload;
	}
}