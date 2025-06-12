package xyz.cucumber.base.module.feat.other;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.util.ResourceLocation;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventBloom;
import xyz.cucumber.base.events.ext.EventRenderGui;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;

@ModuleInfo(category=Category.OTHER, description="Displays otifications on screen", name="Notifications", priority=ArrayPriority.LOW)
public class NotificationsModule
extends Mod {
    public ArrayList<Notification> notifications = new ArrayList();

    @EventListener
    public void onBloom(EventBloom e) {
    }

    @EventListener
    public void onRenderGui(EventRenderGui e) {
        Iterator<Notification> itr = this.notifications.iterator();
        int i = 0;
        while (itr.hasNext()) {
            Notification n = itr.next();
            boolean r = n.update(e.getScaledResolution().getScaledWidth(), e.getScaledResolution().getScaledHeight() - 25, i);
            ++i;
            if (!r) continue;
            itr.remove();
            --i;
        }
    }

    public static class Notification {
        private String name;
        private String description;
        private Type type;
        private long maxTime = 3500L;
        private ResourceLocation spotifyIcon = new ResourceLocation("client/images/notifications/spotify.png");
        private ResourceLocation enabledIcon = new ResourceLocation("client/images/notifications/checked.png");
        private ResourceLocation disabledIcon = new ResourceLocation("client/images/notifications/cancel.png");
        private ResourceLocation warningIcon = new ResourceLocation("client/images/notifications/warning.png");
        private PositionUtils position;
        public int time = -1;
        public double animation;

        public Notification(String name, String description, Type type, PositionUtils position) {
            this.name = name;
            this.description = description;
            this.type = type;
            this.position = position;
            this.time = (int)(System.nanoTime() / 1000000L + this.maxTime);
        }

        public boolean update(double x, double y, int offset) {
            if (this.time == -1) {
                this.time = (int)(System.nanoTime() / 1000000L + this.maxTime);
            }
            if (System.nanoTime() / 1000000L >= (long)this.time) {
                return true;
            }
            double t = (long)this.time - System.nanoTime() / 1000000L;
            double w = Fonts.getFont("rb-r").getWidth(this.description) + 40.0 + 5.0;
            double size = 0.0;
            size = t > (double)(this.maxTime - 500L) ? w - Math.pow(w, 1.0 - ((double)this.maxTime - t) / 500.0) : (t <= (double)(this.maxTime - 500L) && t >= 500.0 ? w : Math.pow(w, t / 500.0));
            this.position.setX(x - size);
            this.animation = (this.animation * 9.0 + (double)(25 * offset)) / 10.0;
            this.position.setY(y - this.animation);
            this.position.setWidth(w);
            this.position.setHeight(20.0);
            RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -1879048192, 1.0f);
            int col = -1;
            ResourceLocation icon = null;
            if (this.type == Type.ENABLED) {
                icon = this.enabledIcon;
                col = 1161096088;
            } else if (this.type == Type.DISABLED) {
                icon = this.disabledIcon;
                col = 1173042249;
            } else if (this.type == Type.ALERT) {
                icon = this.warningIcon;
                col = 1173076532;
            } else if (this.type == Type.SPOTIFY) {
                icon = this.spotifyIcon;
                col = 1161096058;
            }
            RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX() + 20.0, this.position.getY2(), col, 1.0f);
            RenderUtils.drawImage(this.position.getX() + 3.0, this.position.getY() + 2.5, 15.0, 15.0, icon, -1);
            Fonts.getFont("rb-m").drawString(this.name, this.position.getX() + 23.0, this.position.getY() + 5.0, -1);
            Fonts.getFont("rb-r").drawString(String.valueOf(this.description) + " (" + (double)((int)(t / 100.0)) / 10.0 + "s)", this.position.getX() + 23.0, this.position.getY() + 5.0 + 8.0, -5592406);
            return false;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Type getType() {
            return this.type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public long getMaxTime() {
            return this.maxTime;
        }

        public void setMaxTime(long maxTime) {
            this.maxTime = maxTime;
        }

        public ResourceLocation getSpotifyIcon() {
            return this.spotifyIcon;
        }

        public void setSpotifyIcon(ResourceLocation spotifyIcon) {
            this.spotifyIcon = spotifyIcon;
        }

        public ResourceLocation getEnabledIcon() {
            return this.enabledIcon;
        }

        public void setEnabledIcon(ResourceLocation enabledIcon) {
            this.enabledIcon = enabledIcon;
        }

        public ResourceLocation getDisabledIcon() {
            return this.disabledIcon;
        }

        public void setDisabledIcon(ResourceLocation disabledIcon) {
            this.disabledIcon = disabledIcon;
        }

        public ResourceLocation getWarningIcon() {
            return this.warningIcon;
        }

        public void setWarningIcon(ResourceLocation warningIcon) {
            this.warningIcon = warningIcon;
        }

        public PositionUtils getPosition() {
            return this.position;
        }

        public void setPosition(PositionUtils position) {
            this.position = position;
        }

        public int getTime() {
            return this.time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }

    public static enum Type {
        ENABLED,
        DISABLED,
        SPOTIFY,
        ALERT;

    }
}
