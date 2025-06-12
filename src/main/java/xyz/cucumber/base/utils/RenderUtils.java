package xyz.cucumber.base.utils;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.RoundedUtils;
import xyz.cucumber.base.utils.render.shaders.Shaders;

public class RenderUtils {
    private static final Frustum frustrum = new Frustum();

    public static void start2D() {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
    }

    public static void drawCustomRect(double x, double y, double x2, double y2, int color, float rounding, boolean rounded) {
        if (rounded) {
            RenderUtils.drawRoundedRect(x, y, x2, y2, color, rounding);
        } else {
            RenderUtils.drawRect(x, y, x2, y2, color);
        }
    }

    public static void drawGradientRectSideways(double x, double y, double x1, double y1, int color, int color2) {
        Gui.drawGradientRectSideways(x, y, x1, y1, color, color2);
    }

    public static boolean isInViewFrustrum(Entity entity) {
        return RenderUtils.isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }

    private static boolean isInViewFrustrum(AxisAlignedBB bb) {
        Entity current = Minecraft.getMinecraft().getRenderViewEntity();
        frustrum.setPosition(current.posX, current.posY, current.posZ);
        return frustrum.isBoundingBoxInFrustum(bb);
    }

    public static void start3D() {
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        GlStateManager.disableCull();
    }

    public static void cross(double x, double y, double size, double linewidth, int color) {
        GL11.glPushMatrix();
        RenderUtils.start2D();
        GL11.glLineWidth((float)((float)linewidth));
        RenderUtils.color(color);
        GL11.glBegin((int)2);
        GL11.glVertex2d((double)(x - size), (double)(y - size));
        GL11.glVertex2d((double)(x + size), (double)(y + size));
        GL11.glEnd();
        GL11.glBegin((int)2);
        GL11.glVertex2d((double)(x + size), (double)(y - size));
        GL11.glVertex2d((double)(x - size), (double)(y + size));
        GL11.glEnd();
        RenderUtils.stop2D();
        GL11.glPopMatrix();
    }

    public static void renderHitbox(AxisAlignedBB bb, int type) {
        GL11.glBegin((int)type);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glEnd();
        GL11.glBegin((int)type);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glEnd();
        GL11.glBegin((int)type);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glEnd();
        GL11.glBegin((int)type);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glEnd();
        GL11.glBegin((int)type);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glEnd();
        GL11.glBegin((int)type);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glEnd();
    }

    public static void stop3D() {
        GlStateManager.enableCull();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
    }

    public static void stop2D() {
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.enableCull();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
    }

    public static void enableScisor() {
        GL11.glEnable((int)3089);
    }

    public static void disableScisor() {
        GL11.glDisable((int)3089);
    }

    public static void scissor(ScaledResolution scaledResolution, double x, double y, double width, double height) {
        int scaleFactor = scaledResolution.getScaleFactor();
        GL11.glScissor((int)((int)Math.round(x * (double)scaleFactor)), (int)((int)Math.round(((double)scaledResolution.getScaledHeight() - (y + height)) * (double)scaleFactor)), (int)((int)Math.round(width * (double)scaleFactor)), (int)((int)Math.round(height * (double)scaleFactor)));
    }

    public static void drawRect(double x, double y, double x1, double y1, int color) {
        double temp;
        if (x1 < x) {
            temp = x;
            x = x1;
            x1 = temp;
        }
        if (y1 < y) {
            temp = y;
            y = y1;
            y1 = temp;
        }
        GL11.glPushMatrix();
        RenderUtils.start2D();
        RenderUtils.color(color);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x1, (double)y);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x, (double)y1);
        GL11.glEnd();
        RenderUtils.stop2D();
        GL11.glPopMatrix();
    }

    public static void drawColorPicker(double x, double y, double width, double height, float hue) {
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)1048575);
        Shaders.colorPicker.startProgram();
        GL20.glUniform2f((int)Shaders.colorPicker.getUniformLoc("u_resolution"), (float)((float)width), (float)((float)height));
        GL20.glUniform1f((int)Shaders.colorPicker.getUniformLoc("hue"), (float)hue);
        Shaders.colorPicker.renderShader(x, y, width, height);
        Shaders.colorPicker.stopProgram();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void drawColorSlider(double x, double y, double width, double height) {
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)1048575);
        Shaders.colorSlider.startProgram();
        GL20.glUniform2f((int)Shaders.colorSlider.getUniformLoc("u_resolution"), (float)((float)width), (float)((float)height));
        Shaders.colorSlider.renderShader(x, y, width, height);
        Shaders.colorSlider.stopProgram();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void drawCircle(double x, double y, double radius, int color, double step) {
        GL11.glPushMatrix();
        RenderUtils.start2D();
        RenderUtils.color(color);
        GL11.glPointSize((float)((float)radius * 4.0f));
        GL11.glEnable((int)2832);
        GL11.glBegin((int)0);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glEnd();
        RenderUtils.stop2D();
        GL11.glPopMatrix();
    }

    public static void drawArrowForClickGui(double x, double y, double size, int color, double angle) {
        GL11.glPushMatrix();
        RenderUtils.start2D();
        GL11.glTranslated((double)x, (double)y, (double)0.0);
        GL11.glRotated((double)angle, (double)0.0, (double)0.0, (double)1.0);
        GL11.glTranslated((double)(-x), (double)(-y), (double)0.0);
        RenderUtils.color(color);
        GL11.glBegin((int)9);
        GL11.glVertex2d((double)(x - size / 2.0), (double)(y + size / 2.0));
        GL11.glVertex2d((double)x, (double)(y - size / 2.0));
        GL11.glVertex2d((double)(x + size / 2.0), (double)(y + size / 2.0));
        GL11.glEnd();
        GL11.glRotated((double)0.0, (double)0.0, (double)0.0, (double)0.0);
        RenderUtils.stop2D();
        GL11.glPopMatrix();
    }

    public static void drawOutlinedRect(double x, double y, double x1, double y1, int color, float size) {
        double temp;
        if (x1 < x) {
            temp = x;
            x = x1;
            x1 = temp;
        }
        if (y1 < y) {
            temp = y;
            y = y1;
            y1 = temp;
        }
        GL11.glPushMatrix();
        RenderUtils.start2D();
        RenderUtils.color(color);
        GL11.glLineWidth((float)size);
        GL11.glBegin((int)2);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x1, (double)y);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x, (double)y1);
        GL11.glEnd();
        RenderUtils.stop2D();
        GL11.glPopMatrix();
    }

    public static void drawPoint(double x, double y, int color, float size) {
        GL11.glPushMatrix();
        RenderUtils.start2D();
        GL11.glPointSize((float)size);
        GL11.glBegin((int)0);
        RenderUtils.color(color);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glEnd();
        RenderUtils.stop2D();
        GL11.glPopMatrix();
    }

    public static void drawOtherBackground(double x, double y, double width, double height, float time) {
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)1048575);
        Shaders.testShader.startProgram();
        GL20.glUniform1f((int)Shaders.testShader.getUniformLoc("iTime"), (float)time);
        Shaders.testShader.renderShader(x, y, width, height);
        Shaders.testShader.stopProgram();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void drawBlurTest(double x, double y, double width, double height, float radius) {
        try {
            GL11.glPushMatrix();
            GL11.glPushAttrib((int)1048575);
            Shaders.testShader.startProgram();
            GL20.glUniform2f((int)Shaders.testShader.getUniformLoc("screenSize"), (float)((float)width), (float)((float)height));
            GL20.glUniform1f((int)Shaders.testShader.getUniformLoc("radius"), (float)radius);
            GL20.glUniform1f((int)Shaders.testShader.getUniformLoc("texture0"), (float)0.0f);
            Shaders.testShader.renderShader(x, y, width, height);
            Shaders.testShader.stopProgram();
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void drawImageWithAlpha(double x, double y, double width, double height, int color, ResourceLocation location, int alpha) {
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)1048575);
        Shaders.alphaImage.startProgram();
        GL20.glUniform1f((int)Shaders.alphaImage.getUniformLoc("texture"), (float)0.0f);
        GL20.glUniform1f((int)Shaders.alphaImage.getUniformLoc("alpha"), (float)((float)alpha / 255.0f));
        RenderUtils.drawImage(x, y, width, height, location, color);
        Shaders.alphaImage.stopProgram();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void drawRoundedRect(double x, double y, double x1, double y1, int color, float rounding) {
        double temp;
        if (x1 < x) {
            temp = x;
            x = x1;
            x1 = temp;
        }
        if (y1 < y) {
            temp = y;
            y = y1;
            y1 = temp;
        }
        float rectWidth = (float)(x1 - x);
        float rectHeight = (float)(y1 - y);
        RoundedUtils.drawRoundedRect(x, y, rectWidth, rectHeight, color, rounding);
    }

    public static void drawLine(double x, double y, double x1, double y1, int color, float size) {
        GL11.glPushMatrix();
        RenderUtils.start2D();
        GL11.glLineWidth((float)size);
        RenderUtils.color(color);
        GL11.glBegin((int)2);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glEnd();
        RenderUtils.stop2D();
        GL11.glPopMatrix();
    }

    public static void color(int color) {
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        GlStateManager.color(f, f1, f2, f3);
    }

    public static void color(int color, float a) {
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        GlStateManager.color(f, f1, f2, a);
    }

    public static void drawImage(double x, double y, double width, double height, ResourceLocation image, int color) {
        GL11.glDisable((int)2929);
        GlStateManager.enableBlend();
        GL11.glDepthMask((boolean)false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        RenderUtils.color(color);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture((float)x, (float)y, 0.0f, 0.0f, width, height, width, height);
        GlStateManager.resetColor();
        GL11.glDepthMask((boolean)true);
        GlStateManager.disableBlend();
        GL11.glEnable((int)2929);
    }

    private static void drawCirclePart(double x, double y, double radius, double from, double to, int color) {
        GL11.glPushMatrix();
        RenderUtils.start2D();
        RenderUtils.color(color);
        GL11.glBegin((int)6);
        double step = 2.0;
        GL11.glVertex2d((double)x, (double)y);
        double i = from;
        while (i <= to) {
            GL11.glVertex2d((double)(x + Math.sin(i * Math.PI / 180.0) * radius), (double)(y - Math.cos(i * Math.PI / 180.0) * radius));
            i += step;
        }
        GL11.glEnd();
        RenderUtils.stop2D();
        GL11.glPopMatrix();
    }

    public static void drawRoundedRectWithCorners(double x, double y, double x1, double y1, int color, double radius, boolean leftTop, boolean rightTop, boolean leftBot, boolean rightBot) {
        GL11.glPushMatrix();
        RenderUtils.drawRect(x + radius, y, x1 - radius, y1, color);
        RenderUtils.drawRect(x, y + (leftTop ? radius : 0.0), x + radius, y1 - (leftBot ? radius : 0.0), color);
        RenderUtils.drawRect(x1 - radius, y + (rightTop ? radius : 0.0), x1, y1 - (rightBot ? radius : 0.0), color);
        if (leftTop) {
            RenderUtils.drawCirclePart(x + radius, y + radius, radius, 270.0, 360.0, color);
        }
        if (rightTop) {
            RenderUtils.drawCirclePart(x1 - radius, y + radius, radius, 0.0, 90.0, color);
        }
        if (leftBot) {
            RenderUtils.drawCirclePart(x + radius, y1 - radius, radius, 180.0, 270.0, color);
        }
        if (rightBot) {
            RenderUtils.drawCirclePart(x1 - radius, y1 - radius, radius, 90.0, 180.0, color);
        }
        GL11.glPopMatrix();
    }

    public static void drawRoundedRect(double x, double y, double x1, double y1, int color, double radius) {
        double temp;
        if (x1 < x) {
            temp = x;
            x = x1;
            x1 = temp;
        }
        if (y1 < y) {
            temp = y;
            y = y1;
            y1 = temp;
        }
        RenderUtils.start2D();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)6);
        double xs = x + (radius *= 2.0);
        double ys = y + radius;
        RenderUtils.color(color);
        double i = 270.0;
        while (i < 360.0) {
            GL11.glVertex2d((double)(xs + Math.sin(i * Math.PI / 180.0) * radius), (double)(ys - Math.cos(i * Math.PI / 180.0) * radius));
            i += 1.0;
        }
        xs = x1 - radius;
        ys = y + radius;
        i = 0.0;
        while (i < 90.0) {
            GL11.glVertex2d((double)(xs + Math.sin(i * Math.PI / 180.0) * radius), (double)(ys - Math.cos(i * Math.PI / 180.0) * radius));
            i += 1.0;
        }
        xs = x1 - radius;
        ys = y1 - radius;
        i = 90.0;
        while (i < 180.0) {
            GL11.glVertex2d((double)(xs + Math.sin(i * Math.PI / 180.0) * radius), (double)(ys - Math.cos(i * Math.PI / 180.0) * radius));
            i += 1.0;
        }
        xs = x + radius;
        ys = y1 - radius;
        i = 180.0;
        while (i < 270.0) {
            GL11.glVertex2d((double)(xs + Math.sin(i * Math.PI / 180.0) * radius), (double)(ys - Math.cos(i * Math.PI / 180.0) * radius));
            i += 1.0;
        }
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
        RenderUtils.stop2D();
    }

    public static void drawOutlinedRoundedRect(double x, double y, double x1, double y1, int color, double radius, double linew) {
        double temp;
        if (x1 < x) {
            temp = x;
            x = x1;
            x1 = temp;
        }
        if (y1 < y) {
            temp = y;
            y = y1;
            y1 = temp;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        RoundedUtils.drawRoundOutline((float)x, (float)y, (float)(x1 - x), (float)(y1 - y), (float)radius, (float)linew / 3.0f, new Color(f, f1, f2, f3));
        GlStateManager.resetColor();
    }

    public static void drawMixedRoundedRect(double x, double y, double x1, double y1, int color1, int color2, double radius) {
        double temp;
        if (x1 < x) {
            temp = x;
            x = x1;
            x1 = temp;
        }
        if (y1 < y) {
            temp = y;
            y = y1;
            y1 = temp;
        }
        float alpha = (float)(color1 >> 24 & 0xFF) / 255.0f;
        RenderUtils.start2D();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)6);
        double xs = x + radius;
        double ys = y + radius;
        RenderUtils.color(ColorUtils.getAlphaColor(ColorUtils.mix(color1, color2, Math.sin(Math.toRadians(System.nanoTime() / 1000000L / 7L + 0L)) + 1.0, 2.0), (int)(alpha * 100.0f)));
        double i = 270.0;
        while (i < 360.0) {
            GL11.glVertex2d((double)(xs + Math.sin(i * Math.PI / 180.0) * radius), (double)(ys - Math.cos(i * Math.PI / 180.0) * radius));
            i += 2.0;
        }
        RenderUtils.color(ColorUtils.getAlphaColor(ColorUtils.mix(color1, color2, Math.sin(Math.toRadians(System.nanoTime() / 1000000L / 7L + 90L)) + 1.0, 2.0), (int)(alpha * 100.0f)));
        xs = x1 - radius;
        ys = y + radius;
        i = 0.0;
        while (i < 90.0) {
            GL11.glVertex2d((double)(xs + Math.sin(i * Math.PI / 180.0) * radius), (double)(ys - Math.cos(i * Math.PI / 180.0) * radius));
            i += 2.0;
        }
        xs = x1 - radius;
        ys = y1 - radius;
        RenderUtils.color(ColorUtils.getAlphaColor(ColorUtils.mix(color1, color2, Math.sin(Math.toRadians(System.nanoTime() / 1000000L / 7L + 180L)) + 1.0, 2.0), (int)(alpha * 100.0f)));
        i = 90.0;
        while (i < 180.0) {
            GL11.glVertex2d((double)(xs + Math.sin(i * Math.PI / 180.0) * radius), (double)(ys - Math.cos(i * Math.PI / 180.0) * radius));
            i += 2.0;
        }
        RenderUtils.color(ColorUtils.getAlphaColor(ColorUtils.mix(color1, color2, Math.sin(Math.toRadians(System.nanoTime() / 1000000L / 7L + 260L)) + 1.0, 2.0), (int)(alpha * 100.0f)));
        xs = x + radius;
        ys = y1 - radius;
        i = 180.0;
        while (i < 270.0) {
            GL11.glVertex2d((double)(xs + Math.sin(i * Math.PI / 180.0) * radius), (double)(ys - Math.cos(i * Math.PI / 180.0) * radius));
            i += 2.0;
        }
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
        RenderUtils.stop2D();
    }

    public static void drawMixedRect(double x, double y, double x1, double y1, int color1, int color2) {
        double temp;
        if (x1 < x) {
            temp = x;
            x = x1;
            x1 = temp;
        }
        if (y1 < y) {
            temp = y;
            y = y1;
            y1 = temp;
        }
        float alpha = color1 >> 24 & 0xFF;
        RenderUtils.start2D();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)7);
        RenderUtils.color(ColorUtils.getAlphaColor(ColorUtils.mix(color1, color2, Math.sin(Math.toRadians(System.nanoTime() / 1000000L / 10L + 0L)) + 1.0, 2.0), 100));
        GL11.glVertex2d((double)x, (double)y);
        RenderUtils.color(ColorUtils.getAlphaColor(ColorUtils.mix(color1, color2, Math.sin(Math.toRadians(System.nanoTime() / 1000000L / 10L + 90L)) + 1.0, 2.0), 100));
        GL11.glVertex2d((double)x1, (double)y);
        RenderUtils.color(ColorUtils.getAlphaColor(ColorUtils.mix(color1, color2, Math.sin(Math.toRadians(System.nanoTime() / 1000000L / 10L + 180L)) + 1.0, 2.0), 100));
        GL11.glVertex2d((double)x1, (double)y1);
        RenderUtils.color(ColorUtils.getAlphaColor(ColorUtils.mix(color1, color2, Math.sin(Math.toRadians(System.nanoTime() / 1000000L / 10L + 260L)) + 1.0, 2.0), 100));
        GL11.glVertex2d((double)x, (double)y1);
        GL11.glEnd();
        RenderUtils.stop2D();
    }
}
