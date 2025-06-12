package xyz.cucumber.base.utils.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.utils.render.Shader;

public class RoundedUtils {
    public static Shader roundedRect = new Shader("#version 120\n\nuniform vec2 size;\nuniform vec4 color;\nuniform float radius;\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b, 0.0)) - r;\n}\n\n\nvoid main() {\n    vec2 rectHalf = size * .5;\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * size), rectHalf - radius - 1., radius))) * color.a;\n    gl_FragColor = vec4(color.rgb, smoothedAlpha);\n\n}");
    public static Shader roundedGradientRectSideways = new Shader("#version 120\n\nuniform vec2 size;\nuniform vec4 color, color2;\nuniform float radius;\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b, 0.0)) - r;\n}\n\n\nvoid main() {\n    vec2 rectHalf = size * .5;\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * size), rectHalf - radius - 1., radius))) * color.a;\n    gl_FragColor = vec4(mix(color.rgb, color2.rgb, gl_TexCoord[0].st.x), smoothedAlpha);\n\n}");
    public static Shader roundedGradientRect = new Shader("#version 120\n\nuniform vec2 size;\nuniform vec4 color, color2, color3, color4;\nuniform float radius;\nuniform float iTime;\nvec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){\r\n\tvec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\r\n\tcolor += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\r\n\treturn color;\r\n}float roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b, 0.0)) - r;\n}\n\n\nvoid main() {\n  vec2 uv = gl_TexCoord[0].st;    vec2 rectHalf = size * .5;\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (uv * size), rectHalf - radius - 1., radius))) * color.a;\n    gl_FragColor = vec4(createGradient(uv, color, color2, color3, color4), smoothedAlpha);\n\n}");
    public static Shader outlinedRoundedRect = new Shader("#version 120\r\n\r\nuniform vec2 location, rectSize;\r\nuniform vec4 color, outlineColor;\r\nuniform float radius, outlineThickness;\r\n\r\nfloat roundedSDF(vec2 centerPos, vec2 size, float radius) {\r\n    return length(max(abs(centerPos) - size + radius, 0.0)) - radius;\r\n}\r\n\r\nvoid main() {\r\n    float distance = roundedSDF(gl_FragCoord.xy - location - (rectSize * .5), (rectSize * .5) + (outlineThickness *.5) - 1.0, radius);\r\n\r\n    float blendAmount = smoothstep(0., 2., abs(distance) - (outlineThickness * .5));\r\n\r\n    vec4 insideColor = (distance < 0.) ? color : vec4(outlineColor.rgb,  0.0);\r\n    gl_FragColor = mix(outlineColor, insideColor, blendAmount);\r\n\r\n}");

    public static void drawGradientRectAnimated(double x, double y, double width, double height, int color, int color2, float rounding) {
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        float f3a = (float)(color2 >> 24 & 0xFF) / 255.0f;
        float fa = (float)(color2 >> 16 & 0xFF) / 255.0f;
        float f1a = (float)(color2 >> 8 & 0xFF) / 255.0f;
        float f2a = (float)(color2 & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        roundedGradientRect.startProgram();
        GL20.glUniform1f((int)roundedGradientRect.getUniformLoc("iTime"), (float)(System.currentTimeMillis() - (long)Client.INSTANCE.startTime));
        GL20.glUniform1f((int)roundedGradientRect.getUniformLoc("radius"), (float)rounding);
        GL20.glUniform2f((int)roundedGradientRect.getUniformLoc("size"), (float)((float)width), (float)((float)height));
        GL20.glUniform4f((int)roundedGradientRect.getUniformLoc("color"), (float)f, (float)f1, (float)f2, (float)f3);
        GL20.glUniform4f((int)roundedGradientRect.getUniformLoc("color2"), (float)fa, (float)f1a, (float)f2a, (float)f3a);
        int textureID = GL11.glGenTextures();
        GL11.glBindTexture((int)3553, (int)textureID);
        roundedGradientRect.renderShader(x, y, (float)width, (float)height);
        GL11.glBindTexture((int)3553, (int)textureID);
        roundedGradientRect.stopProgram();
        GlStateManager.disableBlend();
    }

    public static void drawGradientRectSideways(double x, double y, double width, double height, int color, int color2, float rounding) {
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        float f3a = (float)(color2 >> 24 & 0xFF) / 255.0f;
        float fa = (float)(color2 >> 16 & 0xFF) / 255.0f;
        float f1a = (float)(color2 >> 8 & 0xFF) / 255.0f;
        float f2a = (float)(color2 & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        roundedGradientRectSideways.startProgram();
        GL20.glUniform1f((int)roundedGradientRectSideways.getUniformLoc("radius"), (float)rounding);
        GL20.glUniform2f((int)roundedGradientRectSideways.getUniformLoc("size"), (float)((float)width), (float)((float)height));
        GL20.glUniform4f((int)roundedGradientRectSideways.getUniformLoc("color"), (float)f, (float)f1, (float)f2, (float)f3);
        GL20.glUniform4f((int)roundedGradientRectSideways.getUniformLoc("color2"), (float)fa, (float)f1a, (float)f2a, (float)f3a);
        int textureID = GL11.glGenTextures();
        GL11.glBindTexture((int)3553, (int)textureID);
        roundedGradientRectSideways.renderShader(x, y, (float)width, (float)height);
        GL11.glBindTexture((int)3553, (int)textureID);
        roundedGradientRectSideways.stopProgram();
        GlStateManager.disableBlend();
    }

    public static void drawRoundedRect(double x, double y, double width, double height, int color, float rounding) {
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        roundedRect.startProgram();
        GL20.glUniform1f((int)roundedRect.getUniformLoc("radius"), (float)rounding);
        GL20.glUniform2f((int)roundedRect.getUniformLoc("size"), (float)((float)width), (float)((float)height));
        GL20.glUniform4f((int)roundedRect.getUniformLoc("color"), (float)f, (float)f1, (float)f2, (float)f3);
        int textureID = GL11.glGenTextures();
        GL11.glBindTexture((int)3553, (int)textureID);
        roundedRect.renderShader(x, y, (float)width, (float)height);
        GL11.glBindTexture((int)3553, (int)textureID);
        roundedRect.stopProgram();
        GlStateManager.disableBlend();
    }

    public static void drawRoundOutline(float x, float y, float width, float height, float radius, float outlineThickness, Color outlineColor) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        outlinedRoundedRect.startProgram();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        Shader.uniform1f(outlinedRoundedRect.getProgramID(), "outlineThickness", outlineThickness);
        Shader.uniform2f(outlinedRoundedRect.getProgramID(), "location", x * (float)sr.getScaleFactor(), (float)Minecraft.getMinecraft().displayHeight - height * (float)sr.getScaleFactor() - y * (float)sr.getScaleFactor());
        Shader.uniform2f(outlinedRoundedRect.getProgramID(), "rectSize", width * (float)sr.getScaleFactor(), height * (float)sr.getScaleFactor());
        Shader.uniform1f(outlinedRoundedRect.getProgramID(), "radius", radius * 2.0f);
        Shader.uniform4f(outlinedRoundedRect.getProgramID(), "outlineColor", (float)outlineColor.getRed() / 255.0f, (float)outlineColor.getGreen() / 255.0f, (float)outlineColor.getBlue() / 255.0f, (float)outlineColor.getAlpha() / 255.0f);
        roundedRect.renderShader(x - (1.0f + outlineThickness), y - (1.0f + outlineThickness), width + (2.0f + outlineThickness), height + (2.0f + outlineThickness));
        outlinedRoundedRect.stopProgram();
        GlStateManager.disableBlend();
    }
}
