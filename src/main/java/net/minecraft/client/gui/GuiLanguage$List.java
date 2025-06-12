package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.settings.GameSettings;

class GuiLanguage$List
extends GuiSlot {
    private final List<String> langCodeList;
    private final Map<String, Language> languageMap;

    public GuiLanguage$List(Minecraft mcIn) {
        super(mcIn, GuiLanguage.this.width, GuiLanguage.this.height, 32, GuiLanguage.this.height - 65 + 4, 18);
        this.langCodeList = Lists.newArrayList();
        this.languageMap = Maps.newHashMap();
        for (Language language : GuiLanguage.this.languageManager.getLanguages()) {
            this.languageMap.put(language.getLanguageCode(), language);
            this.langCodeList.add(language.getLanguageCode());
        }
    }

    @Override
    protected int getSize() {
        return this.langCodeList.size();
    }

    @Override
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
        Language language = this.languageMap.get(this.langCodeList.get(slotIndex));
        GuiLanguage.this.languageManager.setCurrentLanguage(language);
        ((GuiLanguage)GuiLanguage.this).game_settings_3.language = language.getLanguageCode();
        this.mc.refreshResources();
        GuiLanguage.this.fontRendererObj.setUnicodeFlag(GuiLanguage.this.languageManager.isCurrentLocaleUnicode() || ((GuiLanguage)GuiLanguage.this).game_settings_3.forceUnicodeFont);
        GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.languageManager.isCurrentLanguageBidirectional());
        ((GuiLanguage)GuiLanguage.this).confirmSettingsBtn.displayString = I18n.format("gui.done", new Object[0]);
        ((GuiLanguage)GuiLanguage.this).forceUnicodeFontBtn.displayString = GuiLanguage.this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
        GuiLanguage.this.game_settings_3.saveOptions();
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return this.langCodeList.get(slotIndex).equals(GuiLanguage.this.languageManager.getCurrentLanguage().getLanguageCode());
    }

    @Override
    protected int getContentHeight() {
        return this.getSize() * 18;
    }

    @Override
    protected void drawBackground() {
        GuiLanguage.this.drawDefaultBackground();
    }

    @Override
    protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
        GuiLanguage.this.fontRendererObj.setBidiFlag(true);
        GuiLanguage.this.drawCenteredString(GuiLanguage.this.fontRendererObj, this.languageMap.get(this.langCodeList.get(entryID)).toString(), this.width / 2, p_180791_3_ + 1, 0xFFFFFF);
        GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.languageManager.getCurrentLanguage().isBidirectional());
    }
}
