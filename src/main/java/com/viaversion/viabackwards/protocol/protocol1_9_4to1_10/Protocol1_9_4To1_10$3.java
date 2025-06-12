package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10;

import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_9_4To1_10$3
extends PacketHandlers {
    final SoundRewriter val$soundRewriter;

    Protocol1_9_4To1_10$3(SoundRewriter soundRewriter) {
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
        this.map(Type.FLOAT, TO_OLD_PITCH);
        this.handler(this.val$soundRewriter.getSoundHandler());
    }
}
