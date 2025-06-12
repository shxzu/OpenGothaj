package xyz.cucumber.base.utils.render;

import java.nio.FloatBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import xyz.cucumber.base.utils.render.Shader;
import xyz.cucumber.base.utils.render.shaders.Kernel;
import xyz.cucumber.base.utils.render.shaders.Shaders;

public class BlurUtils {
    private static Minecraft mc = Minecraft.getMinecraft();
    public static Framebuffer framebuffer = new Framebuffer(1, 1, false);

    public static void setupUniforms(float dir1, float dir2, float radius) {
        ScaledResolution sr = new ScaledResolution(mc);
        Shader.uniform1i(Shaders.blur.getProgramID(), "textureIn", 0);
        Shader.uniform2f(Shaders.blur.getProgramID(), "texelSize", 1.0f / (float)sr.getScaledWidth_double(), 1.0f / (float)sr.getScaledHeight_double());
        Shader.uniform2f(Shaders.blur.getProgramID(), "direction", dir1, dir2);
        Shader.uniform1f(Shaders.blur.getProgramID(), "radius", radius);
        FloatBuffer weightBuffer = BufferUtils.createFloatBuffer((int)256);
        int i = 0;
        while ((float)i <= radius) {
            weightBuffer.put(Kernel.calculateGaussianValue(i, radius / 2.0f));
            ++i;
        }
        weightBuffer.rewind();
        GL20.glUniform1((int)GL20.glGetUniformLocation((int)Shaders.blur.getProgramID(), (CharSequence)"weights"), (FloatBuffer)weightBuffer);
    }

    public static void renderBlur(float radius) {
        ScaledResolution sr = new ScaledResolution(mc);
        GlStateManager.enableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        framebuffer = BlurUtils.createFrameBuffer(framebuffer);
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        Shaders.blur.startProgram();
        BlurUtils.setupUniforms(1.0f, 0.0f, radius);
        BlurUtils.bindTexture(BlurUtils.mc.getFramebuffer().framebufferTexture);
        Shaders.blur.renderShader(0.0, 0.0, sr.getScaledWidth_double(), sr.getScaledHeight_double());
        framebuffer.unbindFramebuffer();
        Shaders.blur.stopProgram();
        mc.getFramebuffer().bindFramebuffer(true);
        Shaders.blur.startProgram();
        BlurUtils.setupUniforms(0.0f, 1.0f, radius);
        BlurUtils.bindTexture(BlurUtils.framebuffer.framebufferTexture);
        Shaders.blur.renderShader(0.0, 0.0, sr.getScaledWidth_double(), sr.getScaledHeight_double());
        Shaders.blur.stopProgram();
        GlStateManager.resetColor();
        GlStateManager.bindTexture(0);
    }

    public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        if (framebuffer == null || framebuffer.framebufferWidth != BlurUtils.mc.displayWidth || framebuffer.framebufferHeight != BlurUtils.mc.displayHeight) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(BlurUtils.mc.displayWidth, BlurUtils.mc.displayHeight, true);
        }
        return framebuffer;
    }

    public static void bindTexture(int texture) {
        GL11.glBindTexture((int)3553, (int)texture);
    }
}
