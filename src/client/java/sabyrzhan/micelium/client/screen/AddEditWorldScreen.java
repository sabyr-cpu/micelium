package sabyrzhan.micelium.client.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import sabyrzhan.micelium.client.world.MiceliumWorldEntry;
import sabyrzhan.micelium.client.world.MiceliumWorldStorage;

public final class AddEditWorldScreen extends Screen {

	private static final Component NAME_LABEL = Component.translatable("micelium.screen.add_world.name");
	private static final Component ADDRESS_LABEL = Component.translatable("micelium.screen.add_world.address");
	private static final Component PORT_LABEL = Component.translatable("micelium.screen.add_world.port");

	private final MiceliumWorldsScreen parent;
	private final MiceliumWorldStorage storage;
	private final MiceliumWorldEntry existingEntry;
	private final int editIndex;

	private EditBox nameField;
	private EditBox addressField;
	private EditBox portField;
	private Button doneButton;

	public AddEditWorldScreen(MiceliumWorldsScreen parent, MiceliumWorldStorage storage,
			MiceliumWorldEntry existing, int editIndex) {
		super(Component.translatable(editIndex < 0
			? "micelium.screen.add_world.title"
			: "micelium.screen.edit_world.title"));
		this.parent = parent;
		this.storage = storage;
		this.existingEntry = existing;
		this.editIndex = editIndex;
	}

	@Override
	protected void init() {
		int cx = this.width / 2 - 100;
		int startY = this.height / 4;

		nameField = new EditBox(this.font, cx, startY, 200, 20, NAME_LABEL);
		nameField.setMaxLength(64);
		nameField.setHint(Component.translatable("micelium.screen.add_world.name.hint"));
		nameField.setResponder(v -> updateDoneButton());
		addWidget(nameField);

		addressField = new EditBox(this.font, cx, startY + 40, 200, 20, ADDRESS_LABEL);
		addressField.setMaxLength(128);
		addressField.setHint(Component.literal("localhost"));
		addressField.setResponder(v -> updateDoneButton());
		addWidget(addressField);

		portField = new EditBox(this.font, cx, startY + 80, 200, 20, PORT_LABEL);
		portField.setMaxLength(5);
		portField.setHint(Component.literal(String.valueOf(MiceliumWorldEntry.DEFAULT_PORT)));
		addWidget(portField);

		if (existingEntry != null) {
			nameField.setValue(existingEntry.name());
			addressField.setValue(existingEntry.address());
			portField.setValue(String.valueOf(existingEntry.port()));
		}

		doneButton = addRenderableWidget(
			Button.builder(CommonComponents.GUI_DONE, btn -> save())
				.bounds(cx, startY + 120, 96, 20).build()
		);
		addRenderableWidget(
			Button.builder(CommonComponents.GUI_CANCEL, btn -> onClose())
				.bounds(cx + 104, startY + 120, 96, 20).build()
		);

		updateDoneButton();
	}

	@Override
	protected void setInitialFocus() {
		setInitialFocus(nameField);
	}

	@Override
	public void resize(int width, int height) {
		String name = nameField.getValue();
		String address = addressField.getValue();
		String port = portField.getValue();
		init(width, height);
		nameField.setValue(name);
		addressField.setValue(address);
		portField.setValue(port);
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		super.extractRenderState(graphics, mouseX, mouseY, delta);
		int cx = this.width / 2 - 100;
		int startY = this.height / 4;

		graphics.centeredText(this.font, this.title, this.width / 2, 16, 0xFFFFFF);
		graphics.text(this.font, NAME_LABEL, cx + 1, startY - 11, 0xA0A0A0);
		graphics.text(this.font, ADDRESS_LABEL, cx + 1, startY + 29, 0xA0A0A0);
		graphics.text(this.font, PORT_LABEL, cx + 1, startY + 69, 0xA0A0A0);

		nameField.extractRenderState(graphics, mouseX, mouseY, delta);
		addressField.extractRenderState(graphics, mouseX, mouseY, delta);
		portField.extractRenderState(graphics, mouseX, mouseY, delta);
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(parent);
	}

	private void updateDoneButton() {
		doneButton.active = !nameField.getValue().isBlank() && !addressField.getValue().isBlank();
	}

	private void save() {
		String name = nameField.getValue().trim();
		String address = addressField.getValue().trim();
		int port = parsePort(portField.getValue().trim());
		MiceliumWorldEntry entry = new MiceliumWorldEntry(name, address, port);
		if (editIndex < 0) {
			storage.add(entry);
		} else {
			storage.update(editIndex, entry);
		}
		parent.onWorldSaved();
		this.minecraft.setScreen(parent);
	}

	private static int parsePort(String text) {
		try {
			int p = Integer.parseInt(text);
			return (p > 0 && p <= 65535) ? p : MiceliumWorldEntry.DEFAULT_PORT;
		} catch (NumberFormatException e) {
			return MiceliumWorldEntry.DEFAULT_PORT;
		}
	}
}
