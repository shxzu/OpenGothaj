package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import java.util.ArrayList;

class PlayerPackets$21
extends PacketHandlers {
    PlayerPackets$21() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.BYTE);
        this.map((Type)Type.BYTE, Type.VAR_INT);
        this.map(Type.BOOLEAN);
        this.map(Type.UNSIGNED_BYTE);
        this.create(Type.VAR_INT, 1);
        this.handler(packetWrapper -> {
            short flags = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
            PacketWrapper updateSkin = PacketWrapper.create(28, null, packetWrapper.user());
            updateSkin.write(Type.VAR_INT, packetWrapper.user().get(EntityTracker.class).getPlayerId());
            ArrayList<Metadata> metadata = new ArrayList<Metadata>();
            metadata.add(new Metadata(10, MetaType1_8.Byte, (byte)flags));
            updateSkin.write(Types1_8.METADATA_LIST, metadata);
            PacketUtil.sendPacket(updateSkin, Protocol1_8To1_9.class);
        });
    }
}
