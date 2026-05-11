# Architecture

## Design

### Three components

**Fabric mod (on each player):** Owns a subset of world chunks, simulates them, serves them to other peers. Routes requests for remote chunks to their owners.

**Central coordinator (lightweight VPS):** Tracks who's online, which peer owns which chunks, and stores the world when no one is playing. Runs no gameplay logic.

**Network layer:** Peers communicate via Netty (chunk transfers) and gRPC (talking to central server). Messages use Protobuf.

### Chunk ownership

Each chunk is owned by exactly one online peer, determined by `hash(chunk_coords) % num_peers`. When you walk into a chunk owned by someone else, your client requests it from them.

**Hot chunks** (players nearby): Actively simulated by their owner.  
**Cold chunks** (no one nearby): Stored on central server, assigned an owner when someone walks near.

### Key modules

- `BootstrapManager` — connects to central server, fetches peer list
- `OwnershipTracker` — local cache of who owns what
- `ChunkRouter` — intercepts Minecraft's chunk loading, routes to correct owner
- `ChunkHandoffProtocol` — serializes and transfers chunks when ownership changes
- `PersistenceClient` — uploads owned chunks to central server every 60s
- `FailureDetector` — notices when peers disconnect, reclaims their chunks

### Failure handling

**Graceful quit:** Peer uploads all chunks to central server before disconnecting.

**Crash/disconnect:** Central server detects no heartbeat after 15s, declares peer dead. Surviving peers claim orphaned chunks from cold storage. Data loss: up to 60s (last auto-save).

**Central server down:** Peers keep playing using cached ownership map. Can't join new players until it's back.
