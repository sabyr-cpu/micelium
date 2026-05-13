package sabyrzhan.micelium.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import sabyrzhan.micelium.client.world.MiceliumWorldEntry;
import sabyrzhan.micelium.client.world.MiceliumWorldStorage;

public final class MiceliumWorldsScreen extends Screen {

	private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 33, 64);
	private final Screen parent;
	private final MiceliumWorldStorage storage;

	private WorldListWidget listWidget;
	private Button connectButton;
	private Button editButton;
	private Button deleteButton;

	public MiceliumWorldsScreen(Screen parent) {
		super(Component.translatable("micelium.screen.worlds.title"));
		this.parent = parent;
		this.storage = new MiceliumWorldStorage();
	}

	@Override
	protected void init() {
		this.layout.addTitleHeader(this.title, this.font);

		listWidget = this.layout.addToContents(
			new WorldListWidget(this.minecraft, this.width, this.layout.getContentHeight(), this.layout.getHeaderHeight(), 36)
		);

		LinearLayout footer = this.layout.addToFooter(LinearLayout.vertical().spacing(4));
		footer.defaultCellSetting().alignHorizontallyCenter();

		LinearLayout topRow = footer.addChild(LinearLayout.horizontal().spacing(4));
		connectButton = topRow.addChild(Button.builder(
			Component.translatable("micelium.screen.worlds.connect"),
			btn -> connectToSelected()
		).width(100).build());
		topRow.addChild(Button.builder(
			Component.translatable("micelium.screen.worlds.add"),
			btn -> openAddScreen()
		).width(100).build());
		topRow.addChild(Button.builder(
			Component.translatable("micelium.screen.worlds.create"),
			btn -> openCreateScreen()
		).width(100).build());

		LinearLayout bottomRow = footer.addChild(LinearLayout.horizontal().spacing(4));
		editButton = bottomRow.addChild(Button.builder(
			Component.translatable("micelium.screen.worlds.edit"),
			btn -> editSelected()
		).width(74).build());
		deleteButton = bottomRow.addChild(Button.builder(
			Component.translatable("micelium.screen.worlds.delete"),
			btn -> deleteSelected()
		).width(74).build());
		bottomRow.addChild(Button.builder(
			Component.translatable("micelium.screen.worlds.refresh"),
			btn -> refresh()
		).width(74).build());
		bottomRow.addChild(Button.builder(
			CommonComponents.GUI_BACK,
			btn -> this.onClose()
		).width(74).build());

		this.layout.visitWidgets(this::addRenderableWidget);
		this.repositionElements();
		this.onSelectedChange();
	}

	@Override
	protected void repositionElements() {
		this.layout.arrangeElements();
		if (listWidget != null) {
			listWidget.updateSize(this.width, this.layout);
		}
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(parent);
	}

	public void onSelectedChange() {
		boolean hasSelection = listWidget != null && listWidget.getSelected() != null;
		connectButton.active = hasSelection;
		editButton.active = hasSelection;
		deleteButton.active = hasSelection;
	}

	private void connectToSelected() {
		// TODO: initiate P2P connection via MiceliumMod
	}

	private void openAddScreen() {
		this.minecraft.setScreen(new AddEditWorldScreen(this, storage, null, -1));
	}

	private void openCreateScreen() {
		MiceliumWorldEntry template = new MiceliumWorldEntry("New World", "localhost", MiceliumWorldEntry.DEFAULT_PORT);
		this.minecraft.setScreen(new AddEditWorldScreen(this, storage, template, -1));
	}

	private void editSelected() {
		WorldListWidget.WorldEntry entry = listWidget.getSelected();
		if (entry == null) return;
		int index = listWidget.children().indexOf(entry);
		this.minecraft.setScreen(new AddEditWorldScreen(this, storage, entry.getWorld(), index));
	}

	private void deleteSelected() {
		WorldListWidget.WorldEntry entry = listWidget.getSelected();
		if (entry == null) return;
		int index = listWidget.children().indexOf(entry);
		storage.remove(index);
		listWidget.reload();
		onSelectedChange();
	}

	private void refresh() {
		listWidget.reload();
		onSelectedChange();
	}

	public void onWorldSaved() {
		listWidget.reload();
		onSelectedChange();
	}

	// -------------------------------------------------------------------------

	final class WorldListWidget extends ObjectSelectionList<WorldListWidget.WorldEntry> {

		WorldListWidget(Minecraft minecraft, int width, int height, int y, int itemHeight) {
			super(minecraft, width, height, y, itemHeight);
			reload();
		}

		void reload() {
			clearEntries();
			for (MiceliumWorldEntry world : storage.getWorlds()) {
				addEntry(new WorldEntry(world));
			}
		}

		@Override
		public void setSelected(WorldEntry entry) {
			super.setSelected(entry);
			MiceliumWorldsScreen.this.onSelectedChange();
		}

		@Override
		public int getRowWidth() {
			return super.getRowWidth() + 85;
		}

		// ---- Entry -------------------------------------------------------

		final class WorldEntry extends ObjectSelectionList.Entry<WorldEntry> {

			private final MiceliumWorldEntry world;

			WorldEntry(MiceliumWorldEntry world) {
				this.world = world;
			}

			public MiceliumWorldEntry getWorld() {
				return world;
			}

			@Override
			public Component getNarration() {
				return Component.translatable("narrator.select", world.name());
			}

			@Override
			public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
					boolean hovered, float tickDelta) {
				graphics.text(MiceliumWorldsScreen.this.font, world.name(), getContentX(), getContentY() + 1, 0xFFFFFF);
				graphics.text(MiceliumWorldsScreen.this.font,
					Component.literal(world.displayAddress()),
					getContentX(), getContentY() + 12, 0x808080);
			}

			@Override
			public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
				WorldListWidget.this.setSelected(this);
				return super.mouseClicked(event, doubleClick);
			}
		}
	}
}
