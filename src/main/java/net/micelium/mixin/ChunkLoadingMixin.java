package net.micelium.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.level.ServerChunkCache;

/**
 * Pre-hook for chunk loading and routing.
 *
 * <p>Use this mixin to intercept chunk requests before Minecraft performs its normal lookup so the
 * router can redirect reads to the correct peer owner.
 */
@Mixin(ServerChunkCache.class)
public final class ChunkLoadingMixin {

	@Inject(method = "getChunk", at = @At("HEAD"), cancellable = true)
	private void micelium$beforeChunkLoad(CallbackInfoReturnable<Object> callbackInfo) {
		// TODO: Check ownership, redirect remote loads, and short-circuit when a cached copy exists.
	}
}