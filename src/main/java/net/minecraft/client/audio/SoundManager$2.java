package net.minecraft.client.audio;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

class SoundManager$2
extends URLStreamHandler {
    private final ResourceLocation val$p_148612_0_;

    SoundManager$2(ResourceLocation resourceLocation) {
        this.val$p_148612_0_ = resourceLocation;
    }

    @Override
    protected URLConnection openConnection(URL p_openConnection_1_) {
        return new URLConnection(p_openConnection_1_){

            @Override
            public void connect() throws IOException {
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return Minecraft.getMinecraft().getResourceManager().getResource(val$p_148612_0_).getInputStream();
            }
        };
    }
}
