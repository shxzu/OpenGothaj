package xyz.cucumber.base.module.feat.visuals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventRender3D;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.math.RotationUtils;
import xyz.cucumber.base.utils.render.ColorUtils;

@ModuleInfo(category=Category.VISUALS, description="Displays china hat on your head", name="China Hat", priority=ArrayPriority.LOW)
public class ChinaHatModule
extends Mod {
    public ModeSettings mode = new ModeSettings("Mode", new String[]{"Player", "All"});
    public NumberSettings radius = new NumberSettings("Radius", 0.7, 0.0, 3.0, 0.05);
    public ColorSettings color = new ColorSettings("Color", "Static", -1, -1, 70);

    public ChinaHatModule() {
        this.addSettings(this.mode, this.radius, this.color);
    }

    @EventListener
    public void onRender3D(EventRender3D e) {
        if (this.mode.getMode().equalsIgnoreCase("All")) {
            for (Entity entity : this.mc.theWorld.loadedEntityList) {
                if (entity == this.mc.thePlayer || !(entity instanceof EntityPlayer)) continue;
                this.render(entity, e.getPartialTicks(), new float[]{entity.rotationYaw, entity.rotationPitch});
            }
        }
        if (this.mc.gameSettings.thirdPersonView == 0) {
            return;
        }
        this.render(this.mc.thePlayer, e.getPartialTicks(), new float[]{RotationUtils.customRots ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw, RotationUtils.customRots ? RotationUtils.serverPitch : this.mc.thePlayer.rotationPitch});
    }

    public void render(Entity e, float partialTicks, float[] rots) {
        GL11.glPushMatrix();
        RenderUtils.start3D();
        double x = e.prevPosX + (e.posX - e.prevPosX) * (double)partialTicks - this.mc.getRenderManager().viewerPosX;
        double y = e.prevPosY + (e.posY - e.prevPosY) * (double)partialTicks - this.mc.getRenderManager().viewerPosY;
        double z = e.prevPosZ + (e.posZ - e.prevPosZ) * (double)partialTicks - this.mc.getRenderManager().viewerPosZ;
        float yaw = rots[0];
        float pitch = rots[1];
        GL11.glTranslated((double)(x - Math.sin((double)yaw * Math.PI / 180.0) * (double)pitch / 270.0), (double)(y + (double)e.height - (e.isSneaking() ? 0.25 : 0.0)), (double)(z + Math.cos((double)yaw * Math.PI / 180.0) * (double)pitch / 270.0));
        GL11.glRotated((double)pitch, (double)Math.cos((double)yaw * Math.PI / 180.0), (double)0.0, (double)Math.sin((double)yaw * Math.PI / 180.0));
        GL11.glRotated((double)(-this.mc.thePlayer.rotationYaw - 270.0f), (double)0.0, (double)1.0, (double)0.0);
        GL11.glBegin((int)6);
        GL11.glVertex3d((double)0.0, (double)0.3, (double)0.0);
        double i = 0.0;
        while (i <= 360.0) {
            RenderUtils.color(ColorUtils.getColor(this.color, System.nanoTime() / 1000000L, i, 5.0));
            GL11.glVertex3d((double)(Math.sin(i * Math.PI / 180.0) * this.radius.getValue()), (double)0.0, (double)(-Math.cos(i * Math.PI / 180.0) * this.radius.getValue()));
            i += 5.0;
        }
        GL11.glEnd();
        GL11.glDisable((int)2929);
        RenderUtils.stop3D();
        GL11.glPopMatrix();
    }
}
