package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.rewriter.ComponentRewriter;

class Protocol1_12_2To1_13$2
extends TranslatableRewriter<ClientboundPackets1_13> {
    Protocol1_12_2To1_13$2(BackwardsProtocol protocol, ComponentRewriter.ReadType type) {
        super(protocol, type);
    }

    @Override
    protected void handleTranslate(JsonObject root, String translate) {
        String mappedKey = this.mappedTranslationKey(translate);
        if (mappedKey != null || (mappedKey = Protocol1_12_2To1_13.this.getMappingData().getTranslateMappings().get(translate)) != null) {
            root.addProperty("translate", Protocol1_13To1_12_2.MAPPINGS.getMojangTranslation().getOrDefault(mappedKey, mappedKey));
        }
    }
}
