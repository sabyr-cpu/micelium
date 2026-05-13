package sabyrzhan.micelium.client.mixin;

import net.minecraft.client.gui.components.AbstractWidget;
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

		int spY = this.height / 4 + 48;
		int realmY = this.height / 4 + 96;

		for (var child : this.children()) {
			if (child instanceof AbstractWidget w) {
				int y = w.getY();
				if (y >= this.height - 20) continue;
				if (y >= spY && y < realmY) {
					w.setY(y - 12);
				} else if (y >= realmY) {
					w.setY(y + 12);
				}
			}
		}
		addRenderableWidget(Button.builder(
			Component.translatable("micelium.menu.worlds"),
			btn -> this.minecraft.setScreen(new MiceliumWorldsScreen(this))
		).bounds(this.width / 2 - 100, this.height / 4 + 84, 200, 20).build());
	}
}
