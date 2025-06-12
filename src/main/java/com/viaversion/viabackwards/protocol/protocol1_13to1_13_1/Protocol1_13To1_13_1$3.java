package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.ComponentUtil;

class Protocol1_13To1_13_1$3
extends PacketHandlers {
    Protocol1_13To1_13_1$3() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.STRING);
        this.handler(wrapper -> {
            JsonElement title = wrapper.passthrough(Type.COMPONENT);
            Protocol1_13To1_13_1.this.translatableRewriter.processText(title);
            if (ViaBackwards.getConfig().fix1_13FormattedInventoryTitle()) {
                if (title.isJsonObject() && title.getAsJsonObject().size() == 1 && title.getAsJsonObject().has("translate")) {
                    return;
                }
                JsonObject legacyComponent = new JsonObject();
                legacyComponent.addProperty("text", ComponentUtil.jsonToLegacy(title));
                wrapper.set(Type.COMPONENT, 0, legacyComponent);
            }
        });
    }
}
