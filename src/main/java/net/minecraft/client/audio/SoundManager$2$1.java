// ERROR: Unable to apply inner class name fixup
package net.minecraft.client.audio;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

class SoundManager.1
extends URLConnection {
    private final ResourceLocation val$p_148612_0_;

    SoundManager.1(URL $anonymous0, ResourceLocation resourceLocation) {
        this.val$p_148612_0_ = resourceLocation;
        super($anonymous0);
    }

    @Override
    public void connect() throws IOException {
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return Minecraft.getMinecraft().getResourceManager().getResource(this.val$p_148612_0_).getInputStream();
    }
}
