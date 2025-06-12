package com.viaversion.viarewind.utils;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.rewriter.ComponentRewriter;

class ChatUtil$1
extends ComponentRewriter<ClientboundPacketType> {
    ChatUtil$1(Protocol arg0, ComponentRewriter.ReadType arg1) {
        super(arg0, arg1);
    }

    @Override
    protected void handleTranslate(JsonObject object, String translate) {
        String text = Protocol1_13To1_12_2.MAPPINGS.getMojangTranslation().get(translate);
        if (text != null) {
            object.addProperty("translate", text);
        }
    }
}
