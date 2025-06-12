package net.minecraft.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;

public class GuiKeyBindingList$KeyEntry
implements GuiListExtended.IGuiListEntry {
    private final KeyBinding keybinding;
    private final String keyDesc;
    private final GuiButton btnChangeKeyBinding;
    private final GuiButton btnReset;

    private GuiKeyBindingList$KeyEntry(KeyBinding p_i45029_2_) {
        this.keybinding = p_i45029_2_;
        this.keyDesc = I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]);
        this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 20, I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]));
        this.btnReset = new GuiButton(0, 0, 0, 50, 20, I18n.format("controls.reset", new Object[0]));
    }

    @Override
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
        boolean flag = ((GuiKeyBindingList)GuiKeyBindingList.this).field_148191_k.buttonId == this.keybinding;
        ((GuiKeyBindingList)GuiKeyBindingList.this).mc.fontRendererObj.drawString(this.keyDesc, x + 90 - GuiKeyBindingList.this.maxListLabelWidth, y + slotHeight / 2 - ((GuiKeyBindingList)GuiKeyBindingList.this).mc.fontRendererObj.FONT_HEIGHT / 2, 0xFFFFFF);
        this.btnReset.xPosition = x + 190;
        this.btnReset.yPosition = y;
        this.btnReset.enabled = this.keybinding.getKeyCode() != this.keybinding.getKeyCodeDefault();
        this.btnReset.drawButton(GuiKeyBindingList.this.mc, mouseX, mouseY);
        this.btnChangeKeyBinding.xPosition = x + 105;
        this.btnChangeKeyBinding.yPosition = y;
        this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.keybinding.getKeyCode());
        boolean flag1 = false;
        if (this.keybinding.getKeyCode() != 0) {
            KeyBinding[] keyBindingArray = ((GuiKeyBindingList)GuiKeyBindingList.this).mc.gameSettings.keyBindings;
            int n = ((GuiKeyBindingList)GuiKeyBindingList.this).mc.gameSettings.keyBindings.length;
            int n2 = 0;
            while (n2 < n) {
                KeyBinding keybinding = keyBindingArray[n2];
                if (keybinding != this.keybinding && keybinding.getKeyCode() == this.keybinding.getKeyCode()) {
                    flag1 = true;
                    break;
                }
                ++n2;
            }
        }
        if (flag) {
            this.btnChangeKeyBinding.displayString = (Object)((Object)EnumChatFormatting.WHITE) + "> " + (Object)((Object)EnumChatFormatting.YELLOW) + this.btnChangeKeyBinding.displayString + (Object)((Object)EnumChatFormatting.WHITE) + " <";
        } else if (flag1) {
            this.btnChangeKeyBinding.displayString = (Object)((Object)EnumChatFormatting.RED) + this.btnChangeKeyBinding.displayString;
        }
        this.btnChangeKeyBinding.drawButton(GuiKeyBindingList.this.mc, mouseX, mouseY);
    }

    @Override
    public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
        if (this.btnChangeKeyBinding.mousePressed(GuiKeyBindingList.this.mc, p_148278_2_, p_148278_3_)) {
            ((GuiKeyBindingList)GuiKeyBindingList.this).field_148191_k.buttonId = this.keybinding;
            return true;
        }
        if (this.btnReset.mousePressed(GuiKeyBindingList.this.mc, p_148278_2_, p_148278_3_)) {
            ((GuiKeyBindingList)GuiKeyBindingList.this).mc.gameSettings.setOptionKeyBinding(this.keybinding, this.keybinding.getKeyCodeDefault());
            KeyBinding.resetKeyBindingArrayAndHash();
            return true;
        }
        return false;
    }

    @Override
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
        this.btnChangeKeyBinding.mouseReleased(x, y);
        this.btnReset.mouseReleased(x, y);
    }

    @Override
    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {
    }
}
