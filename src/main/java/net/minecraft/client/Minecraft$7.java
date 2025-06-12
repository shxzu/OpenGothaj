package net.minecraft.client;

import java.util.concurrent.Callable;
import org.lwjgl.opengl.GL11;

class Minecraft$7
implements Callable<String> {
    Minecraft$7() {
    }

    @Override
    public String call() {
        return String.valueOf(GL11.glGetString((int)7937)) + " GL version " + GL11.glGetString((int)7938) + ", " + GL11.glGetString((int)7936);
    }
}
