package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;

class ItemRewriter$1
extends PacketHandlers {
    final Type val$type;

    ItemRewriter$1(Type type) {
        this.val$type = type;
    }

    @Override
    public void register() {
        this.handler(wrapper -> {
            wrapper.passthrough(Type.BOOLEAN);
            int size = wrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < size; ++i) {
                wrapper.passthrough(Type.STRING);
                if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                    wrapper.passthrough(Type.STRING);
                }
                if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                    JsonElement title = wrapper.passthrough(Type.COMPONENT);
                    JsonElement description = wrapper.passthrough(Type.COMPONENT);
                    TranslatableRewriter translatableRewriter = ((BackwardsProtocol)ItemRewriter.this.protocol).getTranslatableRewriter();
                    if (translatableRewriter != null) {
                        translatableRewriter.processText(title);
                        translatableRewriter.processText(description);
                    }
                    ItemRewriter.this.handleItemToClient((Item)wrapper.passthrough(this.val$type));
                    wrapper.passthrough(Type.VAR_INT);
                    int flags = wrapper.passthrough(Type.INT);
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Type.STRING);
                    }
                    wrapper.passthrough(Type.FLOAT);
                    wrapper.passthrough(Type.FLOAT);
                }
                wrapper.passthrough(Type.STRING_ARRAY);
                int arrayLength = wrapper.passthrough(Type.VAR_INT);
                for (int array = 0; array < arrayLength; ++array) {
                    wrapper.passthrough(Type.STRING_ARRAY);
                }
            }
        });
    }
}
