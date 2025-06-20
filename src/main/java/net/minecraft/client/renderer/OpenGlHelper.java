package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import org.lwjgl.opengl.ARBCopyBuffer;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTBlendFuncSeparate;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GLContext;
import oshi.SystemInfo;
import oshi.hardware.Processor;

public class OpenGlHelper {
    public static boolean nvidia;
    public static boolean ati;
    public static int GL_FRAMEBUFFER;
    public static int GL_RENDERBUFFER;
    public static int GL_COLOR_ATTACHMENT0;
    public static int GL_DEPTH_ATTACHMENT;
    public static int GL_FRAMEBUFFER_COMPLETE;
    public static int GL_FB_INCOMPLETE_ATTACHMENT;
    public static int GL_FB_INCOMPLETE_MISS_ATTACH;
    public static int GL_FB_INCOMPLETE_DRAW_BUFFER;
    public static int GL_FB_INCOMPLETE_READ_BUFFER;
    private static int framebufferType;
    public static boolean framebufferSupported;
    private static boolean shadersAvailable;
    private static boolean arbShaders;
    public static int GL_LINK_STATUS;
    public static int GL_COMPILE_STATUS;
    public static int GL_VERTEX_SHADER;
    public static int GL_FRAGMENT_SHADER;
    private static boolean arbMultitexture;
    public static int defaultTexUnit;
    public static int lightmapTexUnit;
    public static int GL_TEXTURE2;
    private static boolean arbTextureEnvCombine;
    public static int GL_COMBINE;
    public static int GL_INTERPOLATE;
    public static int GL_PRIMARY_COLOR;
    public static int GL_CONSTANT;
    public static int GL_PREVIOUS;
    public static int GL_COMBINE_RGB;
    public static int GL_SOURCE0_RGB;
    public static int GL_SOURCE1_RGB;
    public static int GL_SOURCE2_RGB;
    public static int GL_OPERAND0_RGB;
    public static int GL_OPERAND1_RGB;
    public static int GL_OPERAND2_RGB;
    public static int GL_COMBINE_ALPHA;
    public static int GL_SOURCE0_ALPHA;
    public static int GL_SOURCE1_ALPHA;
    public static int GL_SOURCE2_ALPHA;
    public static int GL_OPERAND0_ALPHA;
    public static int GL_OPERAND1_ALPHA;
    public static int GL_OPERAND2_ALPHA;
    private static boolean openGL14;
    public static boolean extBlendFuncSeparate;
    public static boolean openGL21;
    public static boolean shadersSupported;
    private static String logText;
    private static String cpu;
    public static boolean vboSupported;
    public static boolean vboSupportedAti;
    private static boolean arbVbo;
    public static int GL_ARRAY_BUFFER;
    public static int GL_STATIC_DRAW;
    public static float lastBrightnessX;
    public static float lastBrightnessY;
    public static boolean openGL31;
    public static boolean vboRegions;
    public static int GL_COPY_READ_BUFFER;
    public static int GL_COPY_WRITE_BUFFER;
    public static final int GL_QUADS = 7;
    public static final int GL_TRIANGLES = 4;

    static {
        logText = "";
        lastBrightnessX = 0.0f;
        lastBrightnessY = 0.0f;
    }

    public static void initializeTextures() {
        Config.initDisplay();
        ContextCapabilities contextcapabilities = GLContext.getCapabilities();
        arbMultitexture = contextcapabilities.GL_ARB_multitexture && !contextcapabilities.OpenGL13;
        arbTextureEnvCombine = contextcapabilities.GL_ARB_texture_env_combine && !contextcapabilities.OpenGL13;
        openGL31 = contextcapabilities.OpenGL31;
        if (openGL31) {
            GL_COPY_READ_BUFFER = 36662;
            GL_COPY_WRITE_BUFFER = 36663;
        } else {
            GL_COPY_READ_BUFFER = 36662;
            GL_COPY_WRITE_BUFFER = 36663;
        }
        boolean flag = openGL31 || contextcapabilities.GL_ARB_copy_buffer;
        boolean flag1 = contextcapabilities.OpenGL14;
        boolean bl = vboRegions = flag && flag1;
        if (!vboRegions) {
            ArrayList<String> list = new ArrayList<String>();
            if (!flag) {
                list.add("OpenGL 1.3, ARB_copy_buffer");
            }
            if (!flag1) {
                list.add("OpenGL 1.4");
            }
            String s = "VboRegions not supported, missing: " + Config.listToString(list);
            Config.dbg(s);
            logText = String.valueOf(logText) + s + "\n";
        }
        if (arbMultitexture) {
            logText = String.valueOf(logText) + "Using ARB_multitexture.\n";
            defaultTexUnit = 33984;
            lightmapTexUnit = 33985;
            GL_TEXTURE2 = 33986;
        } else {
            logText = String.valueOf(logText) + "Using GL 1.3 multitexturing.\n";
            defaultTexUnit = 33984;
            lightmapTexUnit = 33985;
            GL_TEXTURE2 = 33986;
        }
        if (arbTextureEnvCombine) {
            logText = String.valueOf(logText) + "Using ARB_texture_env_combine.\n";
            GL_COMBINE = 34160;
            GL_INTERPOLATE = 34165;
            GL_PRIMARY_COLOR = 34167;
            GL_CONSTANT = 34166;
            GL_PREVIOUS = 34168;
            GL_COMBINE_RGB = 34161;
            GL_SOURCE0_RGB = 34176;
            GL_SOURCE1_RGB = 34177;
            GL_SOURCE2_RGB = 34178;
            GL_OPERAND0_RGB = 34192;
            GL_OPERAND1_RGB = 34193;
            GL_OPERAND2_RGB = 34194;
            GL_COMBINE_ALPHA = 34162;
            GL_SOURCE0_ALPHA = 34184;
            GL_SOURCE1_ALPHA = 34185;
            GL_SOURCE2_ALPHA = 34186;
            GL_OPERAND0_ALPHA = 34200;
            GL_OPERAND1_ALPHA = 34201;
            GL_OPERAND2_ALPHA = 34202;
        } else {
            logText = String.valueOf(logText) + "Using GL 1.3 texture combiners.\n";
            GL_COMBINE = 34160;
            GL_INTERPOLATE = 34165;
            GL_PRIMARY_COLOR = 34167;
            GL_CONSTANT = 34166;
            GL_PREVIOUS = 34168;
            GL_COMBINE_RGB = 34161;
            GL_SOURCE0_RGB = 34176;
            GL_SOURCE1_RGB = 34177;
            GL_SOURCE2_RGB = 34178;
            GL_OPERAND0_RGB = 34192;
            GL_OPERAND1_RGB = 34193;
            GL_OPERAND2_RGB = 34194;
            GL_COMBINE_ALPHA = 34162;
            GL_SOURCE0_ALPHA = 34184;
            GL_SOURCE1_ALPHA = 34185;
            GL_SOURCE2_ALPHA = 34186;
            GL_OPERAND0_ALPHA = 34200;
            GL_OPERAND1_ALPHA = 34201;
            GL_OPERAND2_ALPHA = 34202;
        }
        extBlendFuncSeparate = contextcapabilities.GL_EXT_blend_func_separate && !contextcapabilities.OpenGL14;
        openGL14 = contextcapabilities.OpenGL14 || contextcapabilities.GL_EXT_blend_func_separate;
        boolean bl2 = framebufferSupported = openGL14 && (contextcapabilities.GL_ARB_framebuffer_object || contextcapabilities.GL_EXT_framebuffer_object || contextcapabilities.OpenGL30);
        if (framebufferSupported) {
            logText = String.valueOf(logText) + "Using framebuffer objects because ";
            if (contextcapabilities.OpenGL30) {
                logText = String.valueOf(logText) + "OpenGL 3.0 is supported and separate blending is supported.\n";
                framebufferType = 0;
                GL_FRAMEBUFFER = 36160;
                GL_RENDERBUFFER = 36161;
                GL_COLOR_ATTACHMENT0 = 36064;
                GL_DEPTH_ATTACHMENT = 36096;
                GL_FRAMEBUFFER_COMPLETE = 36053;
                GL_FB_INCOMPLETE_ATTACHMENT = 36054;
                GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
                GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
                GL_FB_INCOMPLETE_READ_BUFFER = 36060;
            } else if (contextcapabilities.GL_ARB_framebuffer_object) {
                logText = String.valueOf(logText) + "ARB_framebuffer_object is supported and separate blending is supported.\n";
                framebufferType = 1;
                GL_FRAMEBUFFER = 36160;
                GL_RENDERBUFFER = 36161;
                GL_COLOR_ATTACHMENT0 = 36064;
                GL_DEPTH_ATTACHMENT = 36096;
                GL_FRAMEBUFFER_COMPLETE = 36053;
                GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
                GL_FB_INCOMPLETE_ATTACHMENT = 36054;
                GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
                GL_FB_INCOMPLETE_READ_BUFFER = 36060;
            } else if (contextcapabilities.GL_EXT_framebuffer_object) {
                logText = String.valueOf(logText) + "EXT_framebuffer_object is supported.\n";
                framebufferType = 2;
                GL_FRAMEBUFFER = 36160;
                GL_RENDERBUFFER = 36161;
                GL_COLOR_ATTACHMENT0 = 36064;
                GL_DEPTH_ATTACHMENT = 36096;
                GL_FRAMEBUFFER_COMPLETE = 36053;
                GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
                GL_FB_INCOMPLETE_ATTACHMENT = 36054;
                GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
                GL_FB_INCOMPLETE_READ_BUFFER = 36060;
            }
        } else {
            logText = String.valueOf(logText) + "Not using framebuffer objects because ";
            logText = String.valueOf(logText) + "OpenGL 1.4 is " + (contextcapabilities.OpenGL14 ? "" : "not ") + "supported, ";
            logText = String.valueOf(logText) + "EXT_blend_func_separate is " + (contextcapabilities.GL_EXT_blend_func_separate ? "" : "not ") + "supported, ";
            logText = String.valueOf(logText) + "OpenGL 3.0 is " + (contextcapabilities.OpenGL30 ? "" : "not ") + "supported, ";
            logText = String.valueOf(logText) + "ARB_framebuffer_object is " + (contextcapabilities.GL_ARB_framebuffer_object ? "" : "not ") + "supported, and ";
            logText = String.valueOf(logText) + "EXT_framebuffer_object is " + (contextcapabilities.GL_EXT_framebuffer_object ? "" : "not ") + "supported.\n";
        }
        openGL21 = contextcapabilities.OpenGL21;
        shadersAvailable = openGL21 || contextcapabilities.GL_ARB_vertex_shader && contextcapabilities.GL_ARB_fragment_shader && contextcapabilities.GL_ARB_shader_objects;
        logText = String.valueOf(logText) + "Shaders are " + (shadersAvailable ? "" : "not ") + "available because ";
        if (shadersAvailable) {
            if (contextcapabilities.OpenGL21) {
                logText = String.valueOf(logText) + "OpenGL 2.1 is supported.\n";
                arbShaders = false;
                GL_LINK_STATUS = 35714;
                GL_COMPILE_STATUS = 35713;
                GL_VERTEX_SHADER = 35633;
                GL_FRAGMENT_SHADER = 35632;
            } else {
                logText = String.valueOf(logText) + "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n";
                arbShaders = true;
                GL_LINK_STATUS = 35714;
                GL_COMPILE_STATUS = 35713;
                GL_VERTEX_SHADER = 35633;
                GL_FRAGMENT_SHADER = 35632;
            }
        } else {
            logText = String.valueOf(logText) + "OpenGL 2.1 is " + (contextcapabilities.OpenGL21 ? "" : "not ") + "supported, ";
            logText = String.valueOf(logText) + "ARB_shader_objects is " + (contextcapabilities.GL_ARB_shader_objects ? "" : "not ") + "supported, ";
            logText = String.valueOf(logText) + "ARB_vertex_shader is " + (contextcapabilities.GL_ARB_vertex_shader ? "" : "not ") + "supported, and ";
            logText = String.valueOf(logText) + "ARB_fragment_shader is " + (contextcapabilities.GL_ARB_fragment_shader ? "" : "not ") + "supported.\n";
        }
        shadersSupported = framebufferSupported && shadersAvailable;
        String s1 = GL11.glGetString((int)7936).toLowerCase();
        nvidia = s1.contains("nvidia");
        arbVbo = !contextcapabilities.OpenGL15 && contextcapabilities.GL_ARB_vertex_buffer_object;
        vboSupported = contextcapabilities.OpenGL15 || arbVbo;
        logText = String.valueOf(logText) + "VBOs are " + (vboSupported ? "" : "not ") + "available because ";
        if (vboSupported) {
            if (arbVbo) {
                logText = String.valueOf(logText) + "ARB_vertex_buffer_object is supported.\n";
                GL_STATIC_DRAW = 35044;
                GL_ARRAY_BUFFER = 34962;
            } else {
                logText = String.valueOf(logText) + "OpenGL 1.5 is supported.\n";
                GL_STATIC_DRAW = 35044;
                GL_ARRAY_BUFFER = 34962;
            }
        }
        if (ati = s1.contains("ati")) {
            if (vboSupported) {
                vboSupportedAti = true;
            } else {
                GameSettings.Options.RENDER_DISTANCE.setValueMax(16.0f);
            }
        }
        try {
            Processor[] aprocessor = new SystemInfo().getHardware().getProcessors();
            cpu = String.format("%dx %s", aprocessor.length, aprocessor[0]).replaceAll("\\s+", " ");
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public static boolean areShadersSupported() {
        return shadersSupported;
    }

    public static String getLogText() {
        return logText;
    }

    public static int glGetProgrami(int program, int pname) {
        return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB((int)program, (int)pname) : GL20.glGetProgrami((int)program, (int)pname);
    }

    public static void glAttachShader(int program, int shaderIn) {
        if (arbShaders) {
            ARBShaderObjects.glAttachObjectARB((int)program, (int)shaderIn);
        } else {
            GL20.glAttachShader((int)program, (int)shaderIn);
        }
    }

    public static void glDeleteShader(int p_153180_0_) {
        if (arbShaders) {
            ARBShaderObjects.glDeleteObjectARB((int)p_153180_0_);
        } else {
            GL20.glDeleteShader((int)p_153180_0_);
        }
    }

    public static int glCreateShader(int type) {
        return arbShaders ? ARBShaderObjects.glCreateShaderObjectARB((int)type) : GL20.glCreateShader((int)type);
    }

    public static void glShaderSource(int shaderIn, ByteBuffer string) {
        if (arbShaders) {
            ARBShaderObjects.glShaderSourceARB((int)shaderIn, (ByteBuffer)string);
        } else {
            GL20.glShaderSource((int)shaderIn, (ByteBuffer)string);
        }
    }

    public static void glCompileShader(int shaderIn) {
        if (arbShaders) {
            ARBShaderObjects.glCompileShaderARB((int)shaderIn);
        } else {
            GL20.glCompileShader((int)shaderIn);
        }
    }

    public static int glGetShaderi(int shaderIn, int pname) {
        return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB((int)shaderIn, (int)pname) : GL20.glGetShaderi((int)shaderIn, (int)pname);
    }

    public static String glGetShaderInfoLog(int shaderIn, int maxLength) {
        return arbShaders ? ARBShaderObjects.glGetInfoLogARB((int)shaderIn, (int)maxLength) : GL20.glGetShaderInfoLog((int)shaderIn, (int)maxLength);
    }

    public static String glGetProgramInfoLog(int program, int maxLength) {
        return arbShaders ? ARBShaderObjects.glGetInfoLogARB((int)program, (int)maxLength) : GL20.glGetProgramInfoLog((int)program, (int)maxLength);
    }

    public static void glUseProgram(int program) {
        if (arbShaders) {
            ARBShaderObjects.glUseProgramObjectARB((int)program);
        } else {
            GL20.glUseProgram((int)program);
        }
    }

    public static int glCreateProgram() {
        return arbShaders ? ARBShaderObjects.glCreateProgramObjectARB() : GL20.glCreateProgram();
    }

    public static void glDeleteProgram(int program) {
        if (arbShaders) {
            ARBShaderObjects.glDeleteObjectARB((int)program);
        } else {
            GL20.glDeleteProgram((int)program);
        }
    }

    public static void glLinkProgram(int program) {
        if (arbShaders) {
            ARBShaderObjects.glLinkProgramARB((int)program);
        } else {
            GL20.glLinkProgram((int)program);
        }
    }

    public static int glGetUniformLocation(int programObj, CharSequence name) {
        return arbShaders ? ARBShaderObjects.glGetUniformLocationARB((int)programObj, (CharSequence)name) : GL20.glGetUniformLocation((int)programObj, (CharSequence)name);
    }

    public static void glUniform1(int location, IntBuffer values) {
        if (arbShaders) {
            ARBShaderObjects.glUniform1ARB((int)location, (IntBuffer)values);
        } else {
            GL20.glUniform1((int)location, (IntBuffer)values);
        }
    }

    public static void glUniform1i(int location, int v0) {
        if (arbShaders) {
            ARBShaderObjects.glUniform1iARB((int)location, (int)v0);
        } else {
            GL20.glUniform1i((int)location, (int)v0);
        }
    }

    public static void glUniform1(int location, FloatBuffer values) {
        if (arbShaders) {
            ARBShaderObjects.glUniform1ARB((int)location, (FloatBuffer)values);
        } else {
            GL20.glUniform1((int)location, (FloatBuffer)values);
        }
    }

    public static void glUniform2(int location, IntBuffer values) {
        if (arbShaders) {
            ARBShaderObjects.glUniform2ARB((int)location, (IntBuffer)values);
        } else {
            GL20.glUniform2((int)location, (IntBuffer)values);
        }
    }

    public static void glUniform2(int location, FloatBuffer values) {
        if (arbShaders) {
            ARBShaderObjects.glUniform2ARB((int)location, (FloatBuffer)values);
        } else {
            GL20.glUniform2((int)location, (FloatBuffer)values);
        }
    }

    public static void glUniform3(int location, IntBuffer values) {
        if (arbShaders) {
            ARBShaderObjects.glUniform3ARB((int)location, (IntBuffer)values);
        } else {
            GL20.glUniform3((int)location, (IntBuffer)values);
        }
    }

    public static void glUniform3(int location, FloatBuffer values) {
        if (arbShaders) {
            ARBShaderObjects.glUniform3ARB((int)location, (FloatBuffer)values);
        } else {
            GL20.glUniform3((int)location, (FloatBuffer)values);
        }
    }

    public static void glUniform4(int location, IntBuffer values) {
        if (arbShaders) {
            ARBShaderObjects.glUniform4ARB((int)location, (IntBuffer)values);
        } else {
            GL20.glUniform4((int)location, (IntBuffer)values);
        }
    }

    public static void glUniform4(int location, FloatBuffer values) {
        if (arbShaders) {
            ARBShaderObjects.glUniform4ARB((int)location, (FloatBuffer)values);
        } else {
            GL20.glUniform4((int)location, (FloatBuffer)values);
        }
    }

    public static void glUniformMatrix2(int location, boolean transpose, FloatBuffer matrices) {
        if (arbShaders) {
            ARBShaderObjects.glUniformMatrix2ARB((int)location, (boolean)transpose, (FloatBuffer)matrices);
        } else {
            GL20.glUniformMatrix2((int)location, (boolean)transpose, (FloatBuffer)matrices);
        }
    }

    public static void glUniformMatrix3(int location, boolean transpose, FloatBuffer matrices) {
        if (arbShaders) {
            ARBShaderObjects.glUniformMatrix3ARB((int)location, (boolean)transpose, (FloatBuffer)matrices);
        } else {
            GL20.glUniformMatrix3((int)location, (boolean)transpose, (FloatBuffer)matrices);
        }
    }

    public static void glUniformMatrix4(int location, boolean transpose, FloatBuffer matrices) {
        if (arbShaders) {
            ARBShaderObjects.glUniformMatrix4ARB((int)location, (boolean)transpose, (FloatBuffer)matrices);
        } else {
            GL20.glUniformMatrix4((int)location, (boolean)transpose, (FloatBuffer)matrices);
        }
    }

    public static int glGetAttribLocation(int p_153164_0_, CharSequence p_153164_1_) {
        return arbShaders ? ARBVertexShader.glGetAttribLocationARB((int)p_153164_0_, (CharSequence)p_153164_1_) : GL20.glGetAttribLocation((int)p_153164_0_, (CharSequence)p_153164_1_);
    }

    public static int glGenBuffers() {
        return arbVbo ? ARBVertexBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
    }

    public static void glBindBuffer(int target, int buffer) {
        if (arbVbo) {
            ARBVertexBufferObject.glBindBufferARB((int)target, (int)buffer);
        } else {
            GL15.glBindBuffer((int)target, (int)buffer);
        }
    }

    public static void glBufferData(int target, ByteBuffer data, int usage) {
        if (arbVbo) {
            ARBVertexBufferObject.glBufferDataARB((int)target, (ByteBuffer)data, (int)usage);
        } else {
            GL15.glBufferData((int)target, (ByteBuffer)data, (int)usage);
        }
    }

    public static void glDeleteBuffers(int buffer) {
        if (arbVbo) {
            ARBVertexBufferObject.glDeleteBuffersARB((int)buffer);
        } else {
            GL15.glDeleteBuffers((int)buffer);
        }
    }

    public static boolean useVbo() {
        return Config.isMultiTexture() ? false : (Config.isRenderRegions() && !vboRegions ? false : vboSupported && Minecraft.getMinecraft().gameSettings.useVbo);
    }

    public static void glBindFramebuffer(int target, int framebufferIn) {
        if (framebufferSupported) {
            switch (framebufferType) {
                case 0: {
                    GL30.glBindFramebuffer((int)target, (int)framebufferIn);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glBindFramebuffer((int)target, (int)framebufferIn);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glBindFramebufferEXT((int)target, (int)framebufferIn);
                }
            }
        }
    }

    public static void glBindRenderbuffer(int target, int renderbuffer) {
        if (framebufferSupported) {
            switch (framebufferType) {
                case 0: {
                    GL30.glBindRenderbuffer((int)target, (int)renderbuffer);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glBindRenderbuffer((int)target, (int)renderbuffer);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glBindRenderbufferEXT((int)target, (int)renderbuffer);
                }
            }
        }
    }

    public static void glDeleteRenderbuffers(int renderbuffer) {
        if (framebufferSupported) {
            switch (framebufferType) {
                case 0: {
                    GL30.glDeleteRenderbuffers((int)renderbuffer);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glDeleteRenderbuffers((int)renderbuffer);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glDeleteRenderbuffersEXT((int)renderbuffer);
                }
            }
        }
    }

    public static void glDeleteFramebuffers(int framebufferIn) {
        if (framebufferSupported) {
            switch (framebufferType) {
                case 0: {
                    GL30.glDeleteFramebuffers((int)framebufferIn);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glDeleteFramebuffers((int)framebufferIn);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glDeleteFramebuffersEXT((int)framebufferIn);
                }
            }
        }
    }

    public static int glGenFramebuffers() {
        if (!framebufferSupported) {
            return -1;
        }
        switch (framebufferType) {
            case 0: {
                return GL30.glGenFramebuffers();
            }
            case 1: {
                return ARBFramebufferObject.glGenFramebuffers();
            }
            case 2: {
                return EXTFramebufferObject.glGenFramebuffersEXT();
            }
        }
        return -1;
    }

    public static int glGenRenderbuffers() {
        if (!framebufferSupported) {
            return -1;
        }
        switch (framebufferType) {
            case 0: {
                return GL30.glGenRenderbuffers();
            }
            case 1: {
                return ARBFramebufferObject.glGenRenderbuffers();
            }
            case 2: {
                return EXTFramebufferObject.glGenRenderbuffersEXT();
            }
        }
        return -1;
    }

    public static void glRenderbufferStorage(int target, int internalFormat, int width, int height) {
        if (framebufferSupported) {
            switch (framebufferType) {
                case 0: {
                    GL30.glRenderbufferStorage((int)target, (int)internalFormat, (int)width, (int)height);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glRenderbufferStorage((int)target, (int)internalFormat, (int)width, (int)height);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glRenderbufferStorageEXT((int)target, (int)internalFormat, (int)width, (int)height);
                }
            }
        }
    }

    public static void glFramebufferRenderbuffer(int target, int attachment, int renderBufferTarget, int renderBuffer) {
        if (framebufferSupported) {
            switch (framebufferType) {
                case 0: {
                    GL30.glFramebufferRenderbuffer((int)target, (int)attachment, (int)renderBufferTarget, (int)renderBuffer);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glFramebufferRenderbuffer((int)target, (int)attachment, (int)renderBufferTarget, (int)renderBuffer);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glFramebufferRenderbufferEXT((int)target, (int)attachment, (int)renderBufferTarget, (int)renderBuffer);
                }
            }
        }
    }

    public static int glCheckFramebufferStatus(int target) {
        if (!framebufferSupported) {
            return -1;
        }
        switch (framebufferType) {
            case 0: {
                return GL30.glCheckFramebufferStatus((int)target);
            }
            case 1: {
                return ARBFramebufferObject.glCheckFramebufferStatus((int)target);
            }
            case 2: {
                return EXTFramebufferObject.glCheckFramebufferStatusEXT((int)target);
            }
        }
        return -1;
    }

    public static void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
        if (framebufferSupported) {
            switch (framebufferType) {
                case 0: {
                    GL30.glFramebufferTexture2D((int)target, (int)attachment, (int)textarget, (int)texture, (int)level);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glFramebufferTexture2D((int)target, (int)attachment, (int)textarget, (int)texture, (int)level);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glFramebufferTexture2DEXT((int)target, (int)attachment, (int)textarget, (int)texture, (int)level);
                }
            }
        }
    }

    public static void setActiveTexture(int texture) {
        if (arbMultitexture) {
            ARBMultitexture.glActiveTextureARB((int)texture);
        } else {
            GL13.glActiveTexture((int)texture);
        }
    }

    public static void setClientActiveTexture(int texture) {
        if (arbMultitexture) {
            ARBMultitexture.glClientActiveTextureARB((int)texture);
        } else {
            GL13.glClientActiveTexture((int)texture);
        }
    }

    public static void setLightmapTextureCoords(int target, float p_77475_1_, float p_77475_2_) {
        if (arbMultitexture) {
            ARBMultitexture.glMultiTexCoord2fARB((int)target, (float)p_77475_1_, (float)p_77475_2_);
        } else {
            GL13.glMultiTexCoord2f((int)target, (float)p_77475_1_, (float)p_77475_2_);
        }
        if (target == lightmapTexUnit) {
            lastBrightnessX = p_77475_1_;
            lastBrightnessY = p_77475_2_;
        }
    }

    public static void glBlendFunc(int sFactorRGB, int dFactorRGB, int sfactorAlpha, int dfactorAlpha) {
        if (openGL14) {
            if (extBlendFuncSeparate) {
                EXTBlendFuncSeparate.glBlendFuncSeparateEXT((int)sFactorRGB, (int)dFactorRGB, (int)sfactorAlpha, (int)dfactorAlpha);
            } else {
                GL14.glBlendFuncSeparate((int)sFactorRGB, (int)dFactorRGB, (int)sfactorAlpha, (int)dfactorAlpha);
            }
        } else {
            GL11.glBlendFunc((int)sFactorRGB, (int)dFactorRGB);
        }
    }

    public static boolean isFramebufferEnabled() {
        return Config.isAntialiasing() ? false : framebufferSupported && Minecraft.getMinecraft().gameSettings.fboEnable;
    }

    public static void glBufferData(int p_glBufferData_0_, long p_glBufferData_1_, int p_glBufferData_3_) {
        if (arbVbo) {
            ARBVertexBufferObject.glBufferDataARB((int)p_glBufferData_0_, (long)p_glBufferData_1_, (int)p_glBufferData_3_);
        } else {
            GL15.glBufferData((int)p_glBufferData_0_, (long)p_glBufferData_1_, (int)p_glBufferData_3_);
        }
    }

    public static void glBufferSubData(int p_glBufferSubData_0_, long p_glBufferSubData_1_, ByteBuffer p_glBufferSubData_3_) {
        if (arbVbo) {
            ARBVertexBufferObject.glBufferSubDataARB((int)p_glBufferSubData_0_, (long)p_glBufferSubData_1_, (ByteBuffer)p_glBufferSubData_3_);
        } else {
            GL15.glBufferSubData((int)p_glBufferSubData_0_, (long)p_glBufferSubData_1_, (ByteBuffer)p_glBufferSubData_3_);
        }
    }

    public static void glCopyBufferSubData(int p_glCopyBufferSubData_0_, int p_glCopyBufferSubData_1_, long p_glCopyBufferSubData_2_, long p_glCopyBufferSubData_4_, long p_glCopyBufferSubData_6_) {
        if (openGL31) {
            GL31.glCopyBufferSubData((int)p_glCopyBufferSubData_0_, (int)p_glCopyBufferSubData_1_, (long)p_glCopyBufferSubData_2_, (long)p_glCopyBufferSubData_4_, (long)p_glCopyBufferSubData_6_);
        } else {
            ARBCopyBuffer.glCopyBufferSubData((int)p_glCopyBufferSubData_0_, (int)p_glCopyBufferSubData_1_, (long)p_glCopyBufferSubData_2_, (long)p_glCopyBufferSubData_4_, (long)p_glCopyBufferSubData_6_);
        }
    }

    public static String getCpu() {
        return cpu == null ? "<unknown>" : cpu;
    }
}
