package sabyrzhan.micelium.client.world;

public record MiceliumWorldEntry(String name, String address, int port) {

	public static final int DEFAULT_PORT = 50051;

	public String displayAddress() {
		return address + ":" + port;
	}
}
