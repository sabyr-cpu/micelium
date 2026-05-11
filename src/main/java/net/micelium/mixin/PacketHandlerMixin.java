package net.micelium.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.Connection;

/**
 * Pre-hook for packet dispatch.
 *
 * <p>This is the place to observe peer network traffic and integrate Micelium's transport-level
 * bookkeeping without rewriting Minecraft's packet pipeline.
 */
@Mixin(Connection.class)
public final class PacketHandlerMixin {

	@Inject(method = "send", at = @At("HEAD"))
	private void micelium$beforeSend(CallbackInfo callbackInfo) {
		// TODO: Inspect outbound packets and attach Micelium transport metadata when needed.
	}
}