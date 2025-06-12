package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;

class SoundRewriter$2
extends PacketHandlers {
    SoundRewriter$2() {
    }

    @Override
    public void register() {
        this.handler(SoundRewriter.this.getStopSoundHandler());
    }
}
