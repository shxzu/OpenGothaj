package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19;

import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_18_2To1_19$1
extends PacketHandlers {
    final SoundRewriter val$soundRewriter;

    Protocol1_18_2To1_19$1(SoundRewriter soundRewriter) {
        this.val$soundRewriter = soundRewriter;
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.read(Type.LONG);
        this.handler(this.val$soundRewriter.getSoundHandler());
    }
}
