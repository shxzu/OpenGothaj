package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.AdvancementTranslations;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import com.viaversion.viaversion.rewriter.ComponentRewriter;

class ChatPackets1_12$1
extends ComponentRewriter<ClientboundPackets1_12> {
    ChatPackets1_12$1(Protocol arg0, ComponentRewriter.ReadType arg1) {
        super(arg0, arg1);
    }

    @Override
    public void processText(JsonElement element) {
        super.processText(element);
        if (element == null || !element.isJsonObject()) {
            return;
        }
        JsonObject object = element.getAsJsonObject();
        JsonElement keybind = object.remove("keybind");
        if (keybind == null) {
            return;
        }
        object.addProperty("text", keybind.getAsString());
    }

    @Override
    protected void handleTranslate(JsonObject object, String translate) {
        String text = AdvancementTranslations.get(translate);
        if (text != null) {
            object.addProperty("translate", text);
        }
    }
}
