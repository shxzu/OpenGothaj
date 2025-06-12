package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class SoundRewriter$1
extends PacketHandlers {
    SoundRewriter$1() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(SoundRewriter.this.getNamedSoundHandler());
    }
}
