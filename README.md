# micelium

**⚠️ Early development — nothing works yet.**

A peer-to-peer Minecraft multiplayer network using distributed chunk ownership. 
Each player runs a server + client, eliminating the need for a central game server.

**Status:** Core protocol design in progress. Do not expect stability or documentation.

The central coordinator lives in a separate repo: **[hyphae](https://github.com/sabyr-cpu/hyphae)**.

## Roadmap

**v0.1 — First working prototype**
- [x] Central coordinator → [hyphae](https://github.com/sabyr-cpu/hyphae) (peer discovery, ownership registry, cold storage)
- [ ] Chunk ownership assignment and tracking
- [ ] Basic peer-to-peer chunk transfer
- [ ] Two players can connect and see each other's chunks

**v0.2 — Stability**
- [ ] Graceful disconnect handling (chunk upload on quit)
- [ ] Failure detection and chunk reclaim
- [ ] Periodic auto-save (60s interval)
- [ ] 4-player stress test on LAN

**v1.0 — Production-ready for friends**
- [ ] Configuration file (central server address, save interval)
- [ ] Basic observability (logs, connection status)
- [ ] Installation guide and troubleshooting docs
- [ ] 8-player sustained session without crashes

**Future**
- NAT traversal (play over internet without VPN)
- Chunk replication (reduce data loss on crash)
- Load-based rebalancing
- Mod compatibility testing

Current focus: v0.1 core protocol implementation. 

### NOTHING IS FINAL AND I HAVE NO IDEA WHAT I AM DOING