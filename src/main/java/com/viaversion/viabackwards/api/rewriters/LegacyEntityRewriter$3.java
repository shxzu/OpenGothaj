package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.List;

class LegacyEntityRewriter$3
extends PacketHandlers {
    final Type val$oldMetaType;
    final Type val$newMetaType;

    LegacyEntityRewriter$3(Type type, Type type2) {
        this.val$oldMetaType = type;
        this.val$newMetaType = type2;
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        if (this.val$oldMetaType != null) {
            this.map(this.val$oldMetaType, this.val$newMetaType);
        } else {
            this.map(this.val$newMetaType);
        }
        this.handler(wrapper -> {
            List metadata = (List)wrapper.get(this.val$newMetaType, 0);
            LegacyEntityRewriter.this.handleMetadata(wrapper.get(Type.VAR_INT, 0), metadata, wrapper.user());
        });
    }
}
