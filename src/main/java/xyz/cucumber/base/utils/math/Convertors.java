package xyz.cucumber.base.utils.math;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.GLAllocation;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Convertors {
    public static float[] convert2D(float x, float y, float z, int scaleFactor) {
        FloatBuffer windowPosition = GLAllocation.createDirectFloatBuffer(4);
        IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
        FloatBuffer modelMatrix = GLAllocation.createDirectFloatBuffer(16);
        FloatBuffer projectionMatrix = GLAllocation.createDirectFloatBuffer(16);
        GL11.glGetFloat((int)2982, (FloatBuffer)modelMatrix);
        GL11.glGetFloat((int)2983, (FloatBuffer)projectionMatrix);
        GL11.glGetInteger((int)2978, (IntBuffer)viewport);
        if (GLU.gluProject((float)x, (float)y, (float)z, (FloatBuffer)modelMatrix, (FloatBuffer)projectionMatrix, (IntBuffer)viewport, (FloatBuffer)windowPosition)) {
            return new float[]{windowPosition.get(0) / (float)scaleFactor, ((float)Display.getHeight() - windowPosition.get(1)) / (float)scaleFactor, windowPosition.get(2)};
        }
        return null;
    }
}
