package net.micelium.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ServerLevel;

/**
 * Hook for world-save events.
 *
 * <p>Persistence and backup uploads should be triggered from here or from a scheduler invoked by
 * the save lifecycle, depending on how aggressive the final implementation should be.
 */
@Mixin(ServerLevel.class)
public final class WorldSaveMixin {

	@Inject(method = "save", at = @At("HEAD"))
	private void micelium$beforeWorldSave(CallbackInfo callbackInfo) {
		// TODO: Flush owned chunk state and schedule cold-storage uploads before the save completes.
	}
}