package xyz.cucumber.base.interf.altmanager.ut;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.Session;
import xyz.cucumber.base.interf.altmanager.AltManager;
import xyz.cucumber.base.interf.altmanager.impl.AltManagerClickable;
import xyz.cucumber.base.interf.altmanager.ut.AltManagerSession;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.button.Button;
import xyz.cucumber.base.utils.button.CloseButton;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class AltManagerPanel {
    public GuiTextField username;
    public GuiTextField password;
    private PositionUtils position;
    private List<Button> buttons = new ArrayList<Button>();
    public boolean open;
    private double animation;
    private double fieldAnimation1;
    private double fieldAnimation2;
    AltManager altManager;

    public AltManagerPanel(AltManager altManager) {
        this.altManager = altManager;
        this.open = false;
        this.position = new PositionUtils(altManager.width / 2 - 75, altManager.height, 150.0, 160.0, 1.0f);
        this.username = new GuiTextField(0, Minecraft.getMinecraft().fontRendererObj, (int)(this.position.getX() + 2.0), (int)(this.position.getY() + this.position.getHeight() / 2.0 - 5.0 - 25.0), 146, 25);
        this.password = new GuiTextField(0, Minecraft.getMinecraft().fontRendererObj, (int)(this.position.getX() + 2.0), (int)(this.position.getY() + this.position.getHeight() / 2.0 + 5.0), 146, 25);
        double centerLX = this.position.getX() + this.position.getWidth() / 2.0;
        double centerLY = this.position.getY() + this.position.getHeight() / 2.0;
        this.buttons.clear();
        this.buttons.add(new AltManagerClickable(0, "Add", this.position.getX() + this.position.getWidth() / 2.0 - 72.5, this.position.getY() + this.position.getHeight() / 2.0 - 17.5, 70.0, 15.0));
        this.buttons.add(new AltManagerClickable(1, "Login", this.position.getX() + this.position.getWidth() / 2.0 + 2.5, this.position.getY() + this.position.getHeight() / 2.0 - 17.5, 70.0, 15.0));
        this.buttons.add(new CloseButton(2, this.position.getX() + this.position.getWidth() / 2.0 - 17.0, this.position.getY() + 2.0, 15.0, 15.0));
    }

    public void draw(int mouseX, int mouseY) {
        this.updatePositions();
        RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), 0x20AAAAAA, 3.0f);
        RenderUtils.drawOutlinedRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), 0x50777777, 3.0, 0.5);
        String[] s = "Add account".split("");
        double w = 0.0;
        String[] stringArray = s;
        int n = s.length;
        int n2 = 0;
        while (n2 < n) {
            String t = stringArray[n2];
            Fonts.getFont("rb-b-h").drawStringWithShadow(t, this.position.getX() + this.position.getWidth() / 2.0 - Fonts.getFont("rb-b-h").getWidth("Add account") / 2.0 + w, this.position.getY() + 7.0, ColorUtils.mix(-10007340, -12751688, Math.sin(Math.toRadians((double)(System.nanoTime() / 1000000L) + w * 10.0) / 3.0) + 1.0, 2.0), 0x45000000);
            w += Fonts.getFont("rb-b-h").getWidth(t);
            ++n2;
        }
        this.renderTextField(this.username);
        this.renderTextField(this.password);
        for (Button b : this.buttons) {
            b.draw(mouseX, mouseY);
        }
    }

    public void key(char typedChar, int keyCode) {
        if (typedChar == '\t') {
            if (!this.username.isFocused()) {
                this.username.setFocused(true);
            }
            if (!this.password.isFocused()) {
                this.password.setFocused(true);
            }
        }
        this.username.textboxKeyTyped(typedChar, keyCode);
        this.password.textboxKeyTyped(typedChar, keyCode);
    }

    public void click(int mouseX, int mouseY, int b) {
        if (this.open && !this.position.isInside(mouseX, mouseY)) {
            this.open = false;
        }
        for (Button butt : this.getButtons()) {
            if (!butt.getPosition().isInside(mouseX, mouseY) || b != 0) continue;
            switch (butt.getId()) {
                case 1: {
                    if (this.username.getText().equals("")) {
                        return;
                    }
                    if (this.password.getText().equals("")) {
                        Minecraft.getMinecraft().session = new Session(this.username.getText(), "0", "0", "mojang");
                        return;
                    }
                    Session session = this.loginToAccount(this.username.getText(), this.password.getText());
                    if (session == null) {
                        return;
                    }
                    Minecraft.getMinecraft().session = session;
                    break;
                }
                case 0: {
                    if (this.username.getText().equals("")) {
                        return;
                    }
                    if (this.password.getText().equals("")) {
                        this.altManager.sessions.add(new AltManagerSession(this.altManager, new Session(this.username.getText(), "0", "0", "mojang")));
                        return;
                    }
                    Session session = this.loginToAccount(this.username.getText(), this.password.getText());
                    if (session == null) {
                        return;
                    }
                    this.altManager.sessions.add(new AltManagerSession(this.altManager, session));
                    break;
                }
                case 2: {
                    this.open = false;
                }
            }
        }
        this.username.mouseClicked(mouseX, mouseY, b);
        this.password.mouseClicked(mouseX, mouseY, b);
    }

    public Session loginToAccount(String email, String password) {
        try {
            MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
            MicrosoftAuthResult result = authenticator.loginWithCredentials(email, password);
            MinecraftProfile profile = result.getProfile();
            Session session = new Session(profile.getName(), profile.getId(), result.getAccessToken(), "mojang");
            return session;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updatePositions() {
        double centerLX = this.position.getX() + this.position.getWidth() / 2.0;
        double centerLY = this.position.getY() + this.position.getHeight() / 2.0;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if (!this.open) {
            this.position.setY((this.position.getY() * 17.0 + (double)sr.getScaledHeight()) / 18.0);
        } else {
            this.position.setY((this.position.getY() * 17.0 + (double)(sr.getScaledHeight() / 2 - 75)) / 18.0);
        }
        for (Button b : this.buttons) {
            switch (b.getId()) {
                case 0: {
                    b.getPosition().setX(this.position.getX() + this.position.getWidth() / 2.0 - 72.5);
                    b.getPosition().setY(this.position.getY() + this.position.getHeight() - 17.5);
                    break;
                }
                case 1: {
                    b.getPosition().setX(this.position.getX() + this.position.getWidth() / 2.0 + 2.5);
                    b.getPosition().setY(this.position.getY() + this.position.getHeight() - 17.5);
                    break;
                }
                case 2: {
                    b.getPosition().setX(this.position.getX() + this.position.getWidth() - 17.0);
                    b.getPosition().setY(this.position.getY() + 2.0);
                }
            }
        }
        this.username.xPosition = (int)(this.position.getX() + 2.0);
        this.username.yPosition = (int)(this.position.getY() + this.position.getHeight() / 2.0 - 5.0 - 22.0);
        this.password.xPosition = (int)(this.position.getX() + 2.0);
        this.password.yPosition = (int)(this.position.getY() + this.position.getHeight() / 2.0 + 5.0);
    }

    private double spacing(GuiTextField field) {
        String[] text = field.getText().split("");
        double d = 0.0;
        int i = 0;
        String[] stringArray = text;
        int n = text.length;
        int n2 = 0;
        while (n2 < n) {
            String t = stringArray[n2];
            if (field.getCursorPosition() == i) {
                return d;
            }
            ++i;
            d += field == this.password ? Fonts.getFont("rb-r").getWidth("*") : Fonts.getFont("rb-r").getWidth(t);
            ++n2;
        }
        return d;
    }

    public void renderTextField(GuiTextField field) {
        RenderUtils.drawRoundedRect((double)field.xPosition, (double)field.yPosition, (double)(field.xPosition + field.width), (double)(field.yPosition + field.height), 0x35AAAAAA, 3.0f);
        if (field.isFocused()) {
            if (field == this.username) {
                this.fieldAnimation2 = (this.fieldAnimation2 * 10.0 + 10.0) / 11.0;
                RenderUtils.drawOutlinedRoundedRect(field.xPosition, field.yPosition, field.xPosition + field.width, field.yPosition + field.height, ColorUtils.getAlphaColor(ColorUtils.mix(0x35AAAAAA, -1, this.fieldAnimation2, 10.0), 10), 3.0, 0.1);
            } else {
                this.fieldAnimation1 = (this.fieldAnimation1 * 10.0 + 10.0) / 11.0;
                RenderUtils.drawOutlinedRoundedRect(field.xPosition, field.yPosition, field.xPosition + field.width, field.yPosition + field.height, ColorUtils.getAlphaColor(ColorUtils.mix(0x35AAAAAA, -1, this.fieldAnimation1, 10.0), 10), 3.0, 0.1);
            }
            Fonts.getFont("rb-r").drawString("|", (double)(field.xPosition + 4) + this.spacing(field), field.yPosition + 11, -1);
        } else if (field == this.username) {
            this.fieldAnimation2 = this.fieldAnimation2 * 6.0 / 7.0;
            RenderUtils.drawOutlinedRoundedRect(field.xPosition, field.yPosition, field.xPosition + field.width, field.yPosition + field.height, ColorUtils.getAlphaColor(ColorUtils.mix(0x35AAAAAA, -1, this.fieldAnimation2, 10.0), 10), 3.0, 0.1);
        } else {
            this.fieldAnimation1 = this.fieldAnimation1 * 6.0 / 7.0;
            RenderUtils.drawOutlinedRoundedRect(field.xPosition, field.yPosition, field.xPosition + field.width, field.yPosition + field.height, ColorUtils.getAlphaColor(ColorUtils.mix(0x35AAAAAA, -1, this.fieldAnimation1, 10.0), 10), 3.0, 0.1);
        }
        if (field.getText().equals("")) {
            if (field == this.username) {
                Fonts.getFont("rb-r").drawString("Name / E-mail", field.xPosition + 4, field.yPosition + 11, -5592406);
            } else {
                Fonts.getFont("rb-r").drawString("Password", field.xPosition + 4, field.yPosition + 11, -5592406);
            }
        } else {
            int color = -5592406;
            if (field.isFocused()) {
                color = -1;
            }
            if (field == this.password) {
                String ps = "";
                String[] stringArray = field.getText().split("");
                int n = stringArray.length;
                int n2 = 0;
                while (n2 < n) {
                    String s = stringArray[n2];
                    ps = String.valueOf(ps) + "*";
                    ++n2;
                }
                Fonts.getFont("rb-r").drawString(ps, field.xPosition + 4, (double)field.yPosition + 12.5, color);
                return;
            }
            Fonts.getFont("rb-r").drawString(field.getText(), field.xPosition + 4, field.yPosition + 11, color);
        }
    }

    public List<Button> getButtons() {
        return this.buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }
}
