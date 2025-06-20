package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiCommandBlock
extends GuiScreen {
    private static final Logger field_146488_a = LogManager.getLogger();
    private GuiTextField commandTextField;
    private GuiTextField previousOutputTextField;
    private final CommandBlockLogic localCommandBlock;
    private GuiButton doneBtn;
    private GuiButton cancelBtn;
    private GuiButton field_175390_s;
    private boolean field_175389_t;

    public GuiCommandBlock(CommandBlockLogic p_i45032_1_) {
        this.localCommandBlock = p_i45032_1_;
    }

    @Override
    public void updateScreen() {
        this.commandTextField.updateCursorCounter();
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.clear();
        this.doneBtn = new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0]));
        this.buttonList.add(this.doneBtn);
        this.cancelBtn = new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0]));
        this.buttonList.add(this.cancelBtn);
        this.field_175390_s = new GuiButton(4, this.width / 2 + 150 - 20, 150, 20, 20, "O");
        this.buttonList.add(this.field_175390_s);
        this.commandTextField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 150, 50, 300, 20);
        this.commandTextField.setMaxStringLength(Short.MAX_VALUE);
        this.commandTextField.setFocused(true);
        this.commandTextField.setText(this.localCommandBlock.getCommand());
        this.previousOutputTextField = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 150, 150, 276, 20);
        this.previousOutputTextField.setMaxStringLength(Short.MAX_VALUE);
        this.previousOutputTextField.setEnabled(false);
        this.previousOutputTextField.setText("-");
        this.field_175389_t = this.localCommandBlock.shouldTrackOutput();
        this.func_175388_a();
        this.doneBtn.enabled = this.commandTextField.getText().trim().length() > 0;
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 1) {
                this.localCommandBlock.setTrackOutput(this.field_175389_t);
                this.mc.displayGuiScreen(null);
            } else if (button.id == 0) {
                PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
                packetbuffer.writeByte(this.localCommandBlock.func_145751_f());
                this.localCommandBlock.func_145757_a(packetbuffer);
                packetbuffer.writeString(this.commandTextField.getText());
                packetbuffer.writeBoolean(this.localCommandBlock.shouldTrackOutput());
                this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|AdvCdm", packetbuffer));
                if (!this.localCommandBlock.shouldTrackOutput()) {
                    this.localCommandBlock.setLastOutput(null);
                }
                this.mc.displayGuiScreen(null);
            } else if (button.id == 4) {
                this.localCommandBlock.setTrackOutput(!this.localCommandBlock.shouldTrackOutput());
                this.func_175388_a();
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.commandTextField.textboxKeyTyped(typedChar, keyCode);
        this.previousOutputTextField.textboxKeyTyped(typedChar, keyCode);
        boolean bl = this.doneBtn.enabled = this.commandTextField.getText().trim().length() > 0;
        if (keyCode != 28 && keyCode != 156) {
            if (keyCode == 1) {
                this.actionPerformed(this.cancelBtn);
            }
        } else {
            this.actionPerformed(this.doneBtn);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.commandTextField.mouseClicked(mouseX, mouseY, mouseButton);
        this.previousOutputTextField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand", new Object[0]), this.width / 2, 20, 0xFFFFFF);
        this.drawString(this.fontRendererObj, I18n.format("advMode.command", new Object[0]), this.width / 2 - 150, 37, 0xA0A0A0);
        this.commandTextField.drawTextBox();
        int i = 75;
        int j = 0;
        this.drawString(this.fontRendererObj, I18n.format("advMode.nearestPlayer", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 0xA0A0A0);
        this.drawString(this.fontRendererObj, I18n.format("advMode.randomPlayer", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 0xA0A0A0);
        this.drawString(this.fontRendererObj, I18n.format("advMode.allPlayers", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 0xA0A0A0);
        this.drawString(this.fontRendererObj, I18n.format("advMode.allEntities", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 0xA0A0A0);
        this.drawString(this.fontRendererObj, "", this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 0xA0A0A0);
        if (this.previousOutputTextField.getText().length() > 0) {
            i = i + j * this.fontRendererObj.FONT_HEIGHT + 16;
            this.drawString(this.fontRendererObj, I18n.format("advMode.previousOutput", new Object[0]), this.width / 2 - 150, i, 0xA0A0A0);
            this.previousOutputTextField.drawTextBox();
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void func_175388_a() {
        if (this.localCommandBlock.shouldTrackOutput()) {
            this.field_175390_s.displayString = "O";
            if (this.localCommandBlock.getLastOutput() != null) {
                this.previousOutputTextField.setText(this.localCommandBlock.getLastOutput().getUnformattedText());
            }
        } else {
            this.field_175390_s.displayString = "X";
            this.previousOutputTextField.setText("-");
        }
    }
}
