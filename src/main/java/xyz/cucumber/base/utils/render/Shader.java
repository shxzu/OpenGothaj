package xyz.cucumber.base.utils.render;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader {
    private final int programID;
    private final int program = GL20.glCreateProgram();

    public Shader(String fragmentShaderLoc) {
        String vertexLoc = "#version 120\n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix*gl_Vertex;\n}";
        GL20.glAttachShader((int)this.program, (int)this.createShader(fragmentShaderLoc, 35632));
        GL20.glAttachShader((int)this.program, (int)this.createShader(vertexLoc, 35633));
        GL20.glLinkProgram((int)this.program);
        this.programID = this.program;
    }

    public void startProgram() {
        GL20.glUseProgram((int)this.programID);
    }

    public static void uniformFB(int programId, String name, FloatBuffer floatBuffer) {
        GL20.glUniform1((int)Shader.getLocation(programId, name), (FloatBuffer)floatBuffer);
    }

    public static void uniform1i(int programId, String name, int i) {
        GL20.glUniform1i((int)Shader.getLocation(programId, name), (int)i);
    }

    public static void uniform2i(int programId, String name, int i, int j) {
        GL20.glUniform2i((int)Shader.getLocation(programId, name), (int)i, (int)j);
    }

    public static void uniform1f(int programId, String name, float f) {
        GL20.glUniform1f((int)Shader.getLocation(programId, name), (float)f);
    }

    public static void uniform2f(int programId, String name, float f, float g) {
        GL20.glUniform2f((int)Shader.getLocation(programId, name), (float)f, (float)g);
    }

    public static void uniform3f(int programId, String name, float f, float g, float h) {
        GL20.glUniform3f((int)Shader.getLocation(programId, name), (float)f, (float)g, (float)h);
    }

    public static void uniform4f(int programId, String name, float f, float g, float h, float i) {
        GL20.glUniform4f((int)Shader.getLocation(programId, name), (float)f, (float)g, (float)h, (float)i);
    }

    private static int getLocation(int programId, String name) {
        return GL20.glGetUniformLocation((int)programId, (CharSequence)name);
    }

    public int getProgramID() {
        return this.programID;
    }

    public void stopProgram() {
        GL20.glUseProgram((int)0);
    }

    public void renderShader(double x, double y, double width, double height) {
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)0.0f, (float)1.0f);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glTexCoord2f((float)0.0f, (float)0.0f);
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glTexCoord2f((float)1.0f, (float)0.0f);
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glTexCoord2f((float)1.0f, (float)1.0f);
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glEnd();
    }

    public int getUniformLoc(String uniform) {
        return GL20.glGetUniformLocation((int)this.programID, (CharSequence)uniform);
    }

    private int createShader(String oskar, int shaderType) {
        int shader = GL20.glCreateShader((int)shaderType);
        GL20.glShaderSource((int)shader, (CharSequence)oskar);
        GL20.glCompileShader((int)shader);
        System.out.println(GL20.glGetShaderInfoLog((int)shader, (int)1024));
        return shader;
    }
}
