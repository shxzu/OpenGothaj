package xyz.cucumber.base.module.feat.visuals;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventRenderItem;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.NumberSettings;

@ModuleInfo(category=Category.VISUALS, description="Changes swing animation", name="Animations", priority=ArrayPriority.LOW)
public class AnimationsModule
extends Mod {
    public ModeSettings mode = new ModeSettings("Mode", new String[]{"Vanilla", "Sigma", "Stab", "Fan", "Sigma 2", "Old", "Exhibition", "Gothaj", "Swong", "Chill", "Basic", "Fast", "Fast 2"});
    public static NumberSettings swingSpeed = new NumberSettings("Swing Speed", 4.0, 6.0, 25.0, 1.0);
    public BooleanSettings skidSwing = new BooleanSettings("Skid Swing", false);
    public BooleanSettings onlySword = new BooleanSettings("Only Sword", true);
    public NumberSettings x = new NumberSettings("X", 0.0, -2.0, 2.0, 0.1f);
    public NumberSettings y = new NumberSettings("Y", 0.0, -2.0, 2.0, 0.1f);
    public NumberSettings z = new NumberSettings("Z", 0.0, -2.0, 2.0, 0.1f);

    public AnimationsModule() {
        this.addSettings(this.mode, swingSpeed, this.onlySword, this.skidSwing, this.x, this.y, this.z);
    }

    @EventListener
    public void onTick(EventTick e) {
        this.setInfo(this.mode.getMode());
    }

    @EventListener
    public void onRender(EventRenderItem e) {
        if (e.getType() != EventType.PRE) {
            return;
        }
        ItemRenderer itemRenderer = this.mc.getItemRenderer();
        float animationProgression = e.getF();
        float swingProgress = e.getF1();
        float convertedProgress = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float)Math.PI);
        GlStateManager.translate(this.x.getValue(), this.y.getValue(), this.z.getValue());
        switch (this.mode.getMode().toLowerCase()) {
            case "vanilla": {
                itemRenderer.transformFirstPersonItem(animationProgression, swingProgress);
                itemRenderer.doBlockTransformations();
                break;
            }
            case "sigma": {
                itemRenderer.transformFirstPersonItem(animationProgression, 0.0f);
                float y = -convertedProgress * 2.0f;
                GlStateManager.translate(0.0f, y / 10.0f + 0.1f, 0.0f);
                GlStateManager.rotate(y * 10.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(250.0f, 0.2f, 1.0f, -0.6f);
                GlStateManager.rotate(-10.0f, 1.0f, 0.5f, 1.0f);
                GlStateManager.rotate(-y * 20.0f, 1.0f, 0.5f, 1.0f);
                break;
            }
            case "stab": {
                float spin = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float)Math.PI);
                GlStateManager.translate((double)0.6f, (double)0.3f, (double)-0.6f + (double)(-spin) * 0.7);
                GlStateManager.rotate(6090.0f, 0.0f, 0.0f, 0.1f);
                GlStateManager.rotate(6085.0f, 0.0f, 0.1f, 0.0f);
                GlStateManager.rotate(6110.0f, 0.1f, 0.0f, 0.0f);
                itemRenderer.transformFirstPersonItem(0.0f, 0.0f);
                itemRenderer.doBlockTransformations();
                break;
            }
            case "fan": {
                itemRenderer.transformFirstPersonItem(animationProgression, 0.0f);
                GlStateManager.translate(0.0f, 0.2f, -1.0f);
                GlStateManager.rotate(-59.0f, -1.0f, 0.0f, 3.0f);
                GlStateManager.rotate(-(System.currentTimeMillis() / 2L % 360L), 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(60.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case "sigma 2": {
                itemRenderer.transformFirstPersonItem(animationProgression, 0.0f);
                GlStateManager.scale(0.8f, 0.8f, 0.8f);
                GlStateManager.translate(0.0f, 0.1f, 0.0f);
                itemRenderer.doBlockTransformations();
                GlStateManager.rotate(convertedProgress * 35.0f / 2.0f, 0.0f, 1.0f, 1.5f);
                GlStateManager.rotate(-convertedProgress * 135.0f / 4.0f, 1.0f, 1.0f, 0.0f);
                break;
            }
            case "old": {
                GlStateManager.translate(0.0f, 0.18f, 0.0f);
                itemRenderer.transformFirstPersonItem(animationProgression / 2.0f, swingProgress);
                itemRenderer.doBlockTransformations();
                break;
            }
            case "exhibition": {
                itemRenderer.transformFirstPersonItem(animationProgression / 2.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.3f, -0.0f);
                GlStateManager.rotate(-convertedProgress * 31.0f, 1.0f, 0.0f, 2.0f);
                GlStateManager.rotate(-convertedProgress * 33.0f, 1.5f, convertedProgress / 1.1f, 0.0f);
                itemRenderer.doBlockTransformations();
                break;
            }
            case "gothaj": {
                itemRenderer.transformFirstPersonItem(animationProgression / 2.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.3f, -0.0f);
                GlStateManager.rotate(-convertedProgress * 30.0f, 1.0f, 0.0f, 2.0f);
                GlStateManager.rotate(-convertedProgress * 44.0f, 1.5f, convertedProgress / 1.2f, 0.0f);
                itemRenderer.doBlockTransformations();
                break;
            }
            case "swong": {
                itemRenderer.transformFirstPersonItem(animationProgression / 2.0f, swingProgress);
                GlStateManager.rotate(convertedProgress * 30.0f / 2.0f, -convertedProgress, -0.0f, 9.0f);
                GlStateManager.rotate(convertedProgress * 40.0f, 1.0f, -convertedProgress / 2.0f, -0.0f);
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
                itemRenderer.doBlockTransformations();
                break;
            }
            case "chill": {
                itemRenderer.transformFirstPersonItem(animationProgression / 1.5f, 0.0f);
                itemRenderer.doBlockTransformations();
                GlStateManager.translate(-0.05f, 0.3f, 0.3f);
                GlStateManager.rotate(-convertedProgress * 140.0f, 8.0f, 0.0f, 8.0f);
                GlStateManager.rotate(convertedProgress * 90.0f, 8.0f, 0.0f, 8.0f);
                break;
            }
            case "basic": {
                itemRenderer.transformFirstPersonItem(-0.25f, 1.0f + convertedProgress / 10.0f);
                GL11.glRotated((double)(-convertedProgress * 25.0f), (double)1.0, (double)0.0, (double)0.0);
                itemRenderer.doBlockTransformations();
                break;
            }
            case "fast 2": {
                GlStateManager.translate(0.41f, -0.25f, -0.5555557f);
                GlStateManager.translate(0.0f, 0.0f, 0.0f);
                GlStateManager.rotate(35.0f, 0.0f, 1.5f, 0.0f);
                float racism = MathHelper.sin(swingProgress * swingProgress / 64.0f * (float)Math.PI);
                GlStateManager.rotate(racism * -5.0f, 0.0f, 0.0f, 0.0f);
                GlStateManager.rotate(convertedProgress * -12.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(convertedProgress * -65.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.scale(0.3f, 0.3f, 0.3f);
                itemRenderer.doBlockTransformations();
                break;
            }
            case "fast": {
                itemRenderer.transformFirstPersonItem(animationProgression, swingProgress);
                itemRenderer.doBlockTransformations();
                GlStateManager.translate(-0.3f, -0.1f, -0.0f);
            }
        }
    }
}
