package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import java.util.ArrayList;

class BlockItemPackets1_14$1
extends PacketHandlers {
    BlockItemPackets1_14$1() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.map(Type.ITEM1_13_2);
        this.handler(BlockItemPackets1_14.this.itemToClientHandler(Type.ITEM1_13_2));
        this.handler(wrapper -> {
            int entityId = wrapper.get(Type.VAR_INT, 0);
            EntityType entityType = wrapper.user().getEntityTracker(Protocol1_13_2To1_14.class).entityType(entityId);
            if (entityType == null) {
                return;
            }
            if (entityType.isOrHasParent(EntityTypes1_14.ABSTRACT_HORSE)) {
                int armorType;
                wrapper.setPacketType(ClientboundPackets1_13.ENTITY_METADATA);
                wrapper.resetReader();
                wrapper.passthrough(Type.VAR_INT);
                wrapper.read(Type.VAR_INT);
                Item item = wrapper.read(Type.ITEM1_13_2);
                int n = armorType = item == null || item.identifier() == 0 ? 0 : item.identifier() - 726;
                if (armorType < 0 || armorType > 3) {
                    wrapper.cancel();
                    return;
                }
                ArrayList<Metadata> metadataList = new ArrayList<Metadata>();
                metadataList.add(new Metadata(16, Types1_13_2.META_TYPES.varIntType, armorType));
                wrapper.write(Types1_13.METADATA_LIST, metadataList);
            }
        });
    }
}
