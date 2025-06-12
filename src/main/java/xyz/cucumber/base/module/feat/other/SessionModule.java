package xyz.cucumber.base.module.feat.other;

import net.minecraft.network.play.server.S02PacketChat;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventBloom;
import xyz.cucumber.base.events.ext.EventBlur;
import xyz.cucumber.base.events.ext.EventReceivePacket;
import xyz.cucumber.base.events.ext.EventRenderGui;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.addons.Dragable;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.math.PositionHandler;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.Fonts;

@ModuleInfo(category=Category.OTHER, description="Displays session info", name="Session", priority=ArrayPriority.LOW)
public class SessionModule
extends Mod
implements Dragable {
    private NumberSettings positionX = new NumberSettings("Position X", 30.0, 0.0, 1000.0, 1.0);
    private NumberSettings positionY = new NumberSettings("Position Y", 50.0, 0.0, 1000.0, 1.0);
    private BooleanSettings blur = new BooleanSettings("Blur", true);
    private BooleanSettings bloom = new BooleanSettings("Bloom", true);
    public ColorSettings bloomColor = new ColorSettings("Bloom color", "Static", -16777216, -1, 40);
    public ColorSettings logoColor = new ColorSettings("Mark color", "Mix", -12272388, -13470224, 100);
    public ColorSettings backgroundColor = new ColorSettings("Background color", "Static", -16777216, -1, 40);
    private PositionUtils accounts = new PositionUtils(0.0, 0.0, 100.0, 75.0, 1.0f);
    private int wins;
    private int deaths;
    private int kills;
    private String[] winStrings = new String[]{"Winners: <me>", "Winner: <me>"};
    private String[] killStrings = new String[]{"was killed by <me>"};
    private String[] deadStrings = new String[]{"<me> was killed by", "<me> died", "<me> fell into", "<me> tried"};

    public SessionModule() {
        this.addSettings(this.positionX, this.positionY, this.blur, this.bloom, this.bloomColor, this.logoColor, this.backgroundColor);
    }

    @EventListener
    public void onBlur(EventBlur e) {
        if (!this.blur.isEnabled()) {
            return;
        }
        e.setCancelled(true);
        if (e.getType() == EventType.POST) {
            RenderUtils.drawRoundedRect(this.accounts.getX(), this.accounts.getY(), this.accounts.getX2(), this.accounts.getY2(), ColorUtils.getColor(this.backgroundColor, System.nanoTime() / 1000000L, 0.0, 5.0), 3.0);
        }
    }

    @EventListener
    public void onBloom(EventBloom e) {
        if (!this.bloom.isEnabled()) {
            return;
        }
        e.setCancelled(true);
        if (e.getType() == EventType.POST) {
            RenderUtils.drawRoundedRect(this.accounts.getX(), this.accounts.getY(), this.accounts.getX2(), this.accounts.getY2(), ColorUtils.getColor(this.bloomColor, System.nanoTime() / 1000000L, 0.0, 5.0), 3.0);
        }
    }

    @EventListener
    public void onRender2D(EventRenderGui e) {
        double[] pos = PositionHandler.getScaledPosition(this.positionX.getValue(), this.positionY.getValue());
        this.accounts.setX(pos[0]);
        this.accounts.setY(pos[1]);
        RenderUtils.drawRoundedRect(this.accounts.getX(), this.accounts.getY(), this.accounts.getX2(), this.accounts.getY2(), ColorUtils.getColor(this.backgroundColor, System.nanoTime() / 1000000L, 0.0, 5.0), 3.0);
        GL11.glPushMatrix();
        RenderUtils.start2D();
        GL11.glShadeModel((int)7425);
        GL11.glLineWidth((float)2.0f);
        GL11.glBegin((int)2);
        RenderUtils.color(0xAAAAAA);
        GL11.glVertex2d((double)this.accounts.getX(), (double)(this.accounts.getY() + 15.0));
        RenderUtils.color(-5592406);
        GL11.glVertex2d((double)(this.accounts.getX() + this.accounts.getWidth() / 2.0), (double)(this.accounts.getY() + 15.0));
        RenderUtils.color(0xAAAAAA);
        GL11.glVertex2d((double)this.accounts.getX2(), (double)(this.accounts.getY() + 15.0));
        GL11.glEnd();
        RenderUtils.stop2D();
        GL11.glPopMatrix();
        String[] s = "Session".split("");
        double w = 0.0;
        String[] stringArray = s;
        int n = s.length;
        int n2 = 0;
        while (n2 < n) {
            String t = stringArray[n2];
            Fonts.getFont("rb-b").drawStringWithShadow(t, this.accounts.getX() + this.accounts.getWidth() / 2.0 - Fonts.getFont("rb-b").getWidth("Session") / 2.0 + w, this.accounts.getY() + 6.0, ColorUtils.getColor(this.logoColor, System.nanoTime() / 1000000L, w * 2.0, 5.0), 0x45000000);
            w += Fonts.getFont("rb-b").getWidth(t);
            ++n2;
        }
        int time = (int)(System.currentTimeMillis() - (long)Client.INSTANCE.startTime);
        int seconds = time / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;
        Fonts.getFont("rb-b-13").drawString("Playing: ", this.accounts.getX() + 4.0, this.accounts.getY() + 20.0, -1);
        Fonts.getFont("rb-m-13").drawString(String.valueOf(hours) + "h " + minutes % 60 + "m " + seconds % 60 + "s", this.accounts.getX() + 4.0 + Fonts.getFont("rb-b-13").getWidth("Playing: "), this.accounts.getY() + 20.0, -5592406);
        Fonts.getFont("rb-b-13").drawString("Wins: ", this.accounts.getX() + 4.0, this.accounts.getY() + 32.0, -1);
        Fonts.getFont("rb-m-13").drawString("" + this.wins, this.accounts.getX() + 4.0 + Fonts.getFont("rb-b-13").getWidth("Wins: "), this.accounts.getY() + 32.0, -5592406);
        Fonts.getFont("rb-b-13").drawString("Kills: ", this.accounts.getX() + 4.0, this.accounts.getY() + 44.0, -1);
        Fonts.getFont("rb-m-13").drawString("" + this.kills, this.accounts.getX() + 4.0 + Fonts.getFont("rb-b-13").getWidth("Kills: "), this.accounts.getY() + 44.0, -5592406);
        Fonts.getFont("rb-b-13").drawString("Deaths: ", this.accounts.getX() + 4.0, this.accounts.getY() + 56.0, -1);
        Fonts.getFont("rb-m-13").drawString("" + this.deaths, this.accounts.getX() + 4.0 + Fonts.getFont("rb-b-13").getWidth("Deaths: "), this.accounts.getY() + 56.0, -5592406);
        Fonts.getFont("rb-b-13").drawString("K/D: ", this.accounts.getX() + 4.0, this.accounts.getY() + 68.0, -1);
        Fonts.getFont("rb-m-13").drawString("" + (float)Math.round((float)this.kills / (float)(this.deaths == 0 ? 1 : this.deaths) * 100.0f) / 100.0f, this.accounts.getX() + 4.0 + Fonts.getFont("rb-b-13").getWidth("K/D: "), this.accounts.getY() + 68.0, -5592406);
    }

    @EventListener
    public void onReceivePacket(EventReceivePacket e) {
        if (e.getPacket() instanceof S02PacketChat) {
            String f;
            S02PacketChat packet = (S02PacketChat)e.getPacket();
            String[] s = packet.getChatComponent().getUnformattedText().split("");
            int i = 0;
            String text = "";
            while (i < s.length) {
                if (s[i].equals("Â§")) {
                    i += 2;
                    continue;
                }
                text = String.valueOf(text) + s[i];
                ++i;
            }
            String[] stringArray = this.winStrings;
            int n = this.winStrings.length;
            int n2 = 0;
            while (n2 < n) {
                String win = stringArray[n2];
                f = win.replace("<me>", this.mc.thePlayer.getName());
                if (text.contains(f)) {
                    ++this.wins;
                    break;
                }
                ++n2;
            }
            stringArray = this.deadStrings;
            n = this.deadStrings.length;
            n2 = 0;
            while (n2 < n) {
                String death = stringArray[n2];
                f = death.replace("<me>", this.mc.thePlayer.getName());
                if (text.contains(f)) {
                    ++this.deaths;
                    break;
                }
                ++n2;
            }
            stringArray = this.killStrings;
            n = this.killStrings.length;
            n2 = 0;
            while (n2 < n) {
                String kill = stringArray[n2];
                f = kill.replace("<me>", this.mc.thePlayer.getName());
                if (text.contains(f)) {
                    ++this.kills;
                    break;
                }
                ++n2;
            }
        }
    }

    @Override
    public PositionUtils getPosition() {
        return this.accounts;
    }

    @Override
    public void setXYPosition(double x, double y) {
        this.positionX.setValue(x);
        this.positionY.setValue(y);
    }
}
