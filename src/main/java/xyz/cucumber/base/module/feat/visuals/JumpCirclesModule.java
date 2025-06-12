package xyz.cucumber.base.module.feat.visuals;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventJump;
import xyz.cucumber.base.events.ext.EventRender3D;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.render.ColorUtils;

@ModuleInfo(category=Category.VISUALS, description="Displays circle where you jump", name="Jump Circles")
public class JumpCirclesModule
extends Mod {
    private HashMap<Integer, Vec3> circles = new HashMap();
    private ColorSettings color = new ColorSettings("Color", "Static", -1, -1, 90);

    public JumpCirclesModule() {
        this.addSettings(this.color);
    }

    @EventListener
    public void onRender3D(EventRender3D e) {
        Iterator<Map.Entry<Integer, Vec3>> itr = this.circles.entrySet().iterator();
        while (itr.hasNext()) {
            double f;
            Map.Entry<Integer, Vec3> circle = itr.next();
            Vec3 position = circle.getValue();
            int time = circle.getKey();
            if ((long)time < System.nanoTime() / 1000000L) {
                itr.remove();
                continue;
            }
            double timeDiff = (long)time - System.nanoTime() / 1000000L;
            double r = 0.8f;
            float a = (float)this.color.getAlpha() / 100.0f;
            if (timeDiff >= 1000.0) {
                f = Math.abs(timeDiff - 2000.0);
                r = Math.pow(8.00000037997961E-4 * f, 0.9);
            } else {
                f = Math.abs(timeDiff - 1000.0);
                a = (float)this.color.getAlpha() / 100.0f - (float)((double)((float)this.color.getAlpha() / 100.0f / 1000.0f) * f);
            }
            double x = position.xCoord - this.mc.getRenderManager().viewerPosX;
            double y = position.yCoord - this.mc.getRenderManager().viewerPosY;
            double z = position.zCoord - this.mc.getRenderManager().viewerPosZ;
            GL11.glPushMatrix();
            RenderUtils.start3D();
            GL11.glShadeModel((int)7425);
            GL11.glEnable((int)2929);
            GL11.glTranslated((double)x, (double)(y + 0.01), (double)z);
            GL11.glRotatef((float)(((float)this.mc.thePlayer.ticksExisted + e.getPartialTicks()) * 8.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glBegin((int)6);
            RenderUtils.color(0xFFFFFF);
            GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
            double i = 0.0;
            while (i <= 360.0) {
                double posX = Math.sin(Math.toRadians(i)) * r;
                double posZ = Math.cos(Math.toRadians(i)) * r;
                int c = ColorUtils.getColor(this.color, 0.0, i, 10.0);
                RenderUtils.color(c, a);
                GL11.glVertex3d((double)posX, (double)0.01, (double)posZ);
                i += 5.0;
            }
            GL11.glEnd();
            GL11.glShadeModel((int)7424);
            RenderUtils.stop3D();
            GL11.glPopMatrix();
        }
    }

    @EventListener
    public void onJump(EventJump e) {
        if (this.mc.thePlayer.onGround) {
            this.circles.put((int)(System.nanoTime() / 1000000L + 2000L), new Vec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ));
        }
    }
}
