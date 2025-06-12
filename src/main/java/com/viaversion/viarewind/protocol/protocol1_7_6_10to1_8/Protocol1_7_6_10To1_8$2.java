package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.provider.CompressionHandlerProvider;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_7_6_10To1_8$2
extends PacketHandlers {
    Protocol1_7_6_10To1_8$2() {
    }

    @Override
    public void register() {
        this.handler(wrapper -> {
            int threshold = wrapper.read(Type.VAR_INT);
            Via.getManager().getProviders().get(CompressionHandlerProvider.class).onHandleLoginCompressionPacket(wrapper.user(), threshold);
            wrapper.cancel();
        });
    }
}
