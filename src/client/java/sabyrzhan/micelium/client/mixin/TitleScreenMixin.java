package sabyrzhan.micelium.client.mixin;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sabyrzhan.micelium.client.screen.MiceliumWorldsScreen;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

	protected TitleScreenMixin() {
		super(Component.empty());
	}

	@Inject(method = "init", at = @At("TAIL"))
	private void addMiceliumWorldsButton(CallbackInfo ci) {
		// Positioned one row below the standard Singleplayer/Multiplayer/Realms trio.
		// Adjust the Y offset if it overlaps other buttons in your build.
		int y = this.height / 4 + 48 + 72;
		addRenderableWidget(Button.builder(
			Component.translatable("micelium.menu.worlds"),
			btn -> this.minecraft.setScreen(new MiceliumWorldsScreen(this))
		).bounds(this.width / 2 - 100, y, 200, 20).build());
	}
}
