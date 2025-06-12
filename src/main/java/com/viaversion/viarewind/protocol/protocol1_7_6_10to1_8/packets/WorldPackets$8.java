package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.FixedByteArrayType;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;

class WorldPackets$8
extends PacketHandlers {
    WorldPackets$8() {
    }

    @Override
    public void register() {
        this.handler(wrapper -> {
            wrapper.cancel();
            int id = wrapper.read(Type.VAR_INT);
            byte scale = wrapper.read(Type.BYTE);
            int iconCount = wrapper.read(Type.VAR_INT);
            byte[] icons = new byte[iconCount * 4];
            for (int i = 0; i < iconCount; ++i) {
                byte directionAndType = wrapper.read(Type.BYTE);
                icons[i * 4] = (byte)(directionAndType >> 4 & 0xF);
                icons[i * 4 + 1] = wrapper.read(Type.BYTE);
                icons[i * 4 + 2] = wrapper.read(Type.BYTE);
                icons[i * 4 + 3] = (byte)(directionAndType & 0xF);
            }
            int columns = wrapper.read(Type.UNSIGNED_BYTE).shortValue();
            if (columns > 0) {
                int rows = wrapper.read(Type.UNSIGNED_BYTE).shortValue();
                short x = wrapper.read(Type.UNSIGNED_BYTE);
                short z = wrapper.read(Type.UNSIGNED_BYTE);
                byte[] data = wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                for (int column = 0; column < columns; ++column) {
                    byte[] columnData = new byte[rows + 3];
                    columnData[0] = 0;
                    columnData[1] = (byte)(x + column);
                    columnData[2] = (byte)z;
                    for (int i = 0; i < rows; ++i) {
                        columnData[i + 3] = data[column + i * columns];
                    }
                    PacketWrapper mapData = PacketWrapper.create(ClientboundPackets1_8.MAP_DATA, wrapper.user());
                    mapData.write(Type.VAR_INT, id);
                    mapData.write(Type.SHORT, (short)columnData.length);
                    mapData.write(new FixedByteArrayType(columnData.length), columnData);
                    mapData.send(Protocol1_7_6_10To1_8.class, true);
                }
            }
            if (iconCount > 0) {
                byte[] iconData = new byte[iconCount * 3 + 1];
                iconData[0] = 1;
                for (int i = 0; i < iconCount; ++i) {
                    iconData[i * 3 + 1] = (byte)(icons[i * 4] << 4 | icons[i * 4 + 3] & 0xF);
                    iconData[i * 3 + 2] = icons[i * 4 + 1];
                    iconData[i * 3 + 3] = icons[i * 4 + 2];
                }
                PacketWrapper mapData = PacketWrapper.create(ClientboundPackets1_8.MAP_DATA, wrapper.user());
                mapData.write(Type.VAR_INT, id);
                mapData.write(Type.SHORT, (short)iconData.length);
                mapData.write(new FixedByteArrayType(iconData.length), iconData);
                mapData.send(Protocol1_7_6_10To1_8.class, true);
            }
            PacketWrapper mapData = PacketWrapper.create(ClientboundPackets1_8.MAP_DATA, wrapper.user());
            mapData.write(Type.VAR_INT, id);
            mapData.write(Type.SHORT, (short)2);
            mapData.write(new FixedByteArrayType(2), new byte[]{2, scale});
            mapData.send(Protocol1_7_6_10To1_8.class, true);
        });
    }
}
