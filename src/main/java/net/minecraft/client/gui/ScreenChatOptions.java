package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class ScreenChatOptions
extends GuiScreen {
    private static final GameSettings.Options[] field_146399_a = new GameSettings.Options[]{GameSettings.Options.CHAT_VISIBILITY, GameSettings.Options.CHAT_COLOR, GameSettings.Options.CHAT_LINKS, GameSettings.Options.CHAT_OPACITY, GameSettings.Options.CHAT_LINKS_PROMPT, GameSettings.Options.CHAT_SCALE, GameSettings.Options.CHAT_HEIGHT_FOCUSED, GameSettings.Options.CHAT_HEIGHT_UNFOCUSED, GameSettings.Options.CHAT_WIDTH, GameSettings.Options.REDUCED_DEBUG_INFO};
    private final GuiScreen parentScreen;
    private final GameSettings game_settings;
    private String field_146401_i;

    public ScreenChatOptions(GuiScreen parentScreenIn, GameSettings gameSettingsIn) {
        this.parentScreen = parentScreenIn;
        this.game_settings = gameSettingsIn;
    }

    @Override
    public void initGui() {
        int i = 0;
        this.field_146401_i = I18n.format("options.chat.title", new Object[0]);
        GameSettings.Options[] optionsArray = field_146399_a;
        int n = field_146399_a.length;
        int n2 = 0;
        while (n2 < n) {
            GameSettings.Options gamesettings$options = optionsArray[n2];
            if (gamesettings$options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), gamesettings$options));
            } else {
                this.buttonList.add(new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), gamesettings$options, this.game_settings.getKeyBinding(gamesettings$options)));
            }
            ++i;
            ++n2;
        }
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 120, I18n.format("gui.done", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id < 100 && button instanceof GuiOptionButton) {
                this.game_settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
                button.displayString = this.game_settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
            }
            if (button.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146401_i, this.width / 2, 20, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
