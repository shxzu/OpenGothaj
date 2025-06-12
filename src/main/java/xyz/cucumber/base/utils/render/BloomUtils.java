package xyz.cucumber.base.utils.render;

import java.nio.FloatBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.interf.clientsettings.ext.adds.BloomSetting;
import xyz.cucumber.base.utils.render.Shader;
import xyz.cucumber.base.utils.render.shaders.Kernel;
import xyz.cucumber.base.utils.render.shaders.Shaders;

public class BloomUtils {
    private final Minecraft mc = Minecraft.getMinecraft();
    private Framebuffer inputFramebuffer = new Framebuffer(1, 1, true);
    private Framebuffer outputFramebuffer = new Framebuffer(1, 1, true);
    public static int programId = Shaders.bloom.getProgramID();
    private ScaledResolution sr = new ScaledResolution(this.mc);

    public void pre() {
        this.sr = new ScaledResolution(this.mc);
        this.inputFramebuffer.bindFramebuffer(true);
    }

    public void post() {
        BloomSetting setting = (BloomSetting)Client.INSTANCE.getClientSettings().getSettingByName("Bloom");
        this.outputFramebuffer.bindFramebuffer(true);
        Shaders.bloom.startProgram();
        FloatBuffer kernel = BufferUtils.createFloatBuffer((int)256);
        int i = 0;
        while ((double)i <= setting.radius.getValue()) {
            kernel.put(Kernel.calculateGaussianValue(i, (float)(setting.radius.getValue() / 2.0)));
            ++i;
        }
        kernel.rewind();
        Shader.uniform1f(programId, "u_radius", (float)setting.radius.getValue());
        Shader.uniform1f(programId, "u_saturation", (float)setting.saturation.getValue());
        Shader.uniformFB(programId, "u_kernel", kernel);
        Shader.uniform1i(programId, "u_texture1", 0);
        Shader.uniform1i(programId, "u_texture2", 20);
        Shader.uniform2f(programId, "u_texel_size", 1.0f / (float)this.sr.getScaledWidth(), 1.0f / (float)this.sr.getScaledHeight());
        Shader.uniform2f(programId, "u_direction", (float)setting.compression.getValue(), 0.0f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(1, 770);
        GlStateManager.alphaFunc(516, 0.0f);
        this.inputFramebuffer.bindFramebufferTexture();
        Shaders.bloom.renderShader(0.0, 0.0, this.sr.getScaledWidth(), this.sr.getScaledHeight());
        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.blendFunc(770, 771);
        Shader.uniform2f(programId, "u_direction", 0.0f, (float)setting.compression.getValue());
        this.outputFramebuffer.bindFramebufferTexture();
        GL13.glActiveTexture((int)34004);
        this.inputFramebuffer.bindFramebufferTexture();
        GL13.glActiveTexture((int)33984);
        Shaders.bloom.renderShader(0.0, 0.0, this.sr.getScaledWidth(), this.sr.getScaledHeight());
        GlStateManager.disableBlend();
        Shaders.bloom.stopProgram();
    }

    public void reset() {
        ScaledResolution sr = new ScaledResolution(this.mc);
        if (this.mc.displayWidth != this.inputFramebuffer.framebufferWidth || this.mc.displayHeight != this.inputFramebuffer.framebufferHeight) {
            this.inputFramebuffer.deleteFramebuffer();
            this.inputFramebuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
            this.outputFramebuffer.deleteFramebuffer();
            this.outputFramebuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, true);
        } else {
            this.inputFramebuffer.framebufferClear();
            this.outputFramebuffer.framebufferClear();
        }
        this.inputFramebuffer.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.outputFramebuffer.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
    }
}
