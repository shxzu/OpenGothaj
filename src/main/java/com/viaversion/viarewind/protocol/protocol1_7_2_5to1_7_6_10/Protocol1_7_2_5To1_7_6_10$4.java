package com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Protocol1_7_2_5To1_7_6_10$4
extends PacketHandlers {
    Protocol1_7_2_5To1_7_6_10$4() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.BYTE);
        this.handler(packetWrapper -> {
            byte mode = packetWrapper.get(Type.BYTE, 0);
            if (mode == 0 || mode == 2) {
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.passthrough(Type.BYTE);
            }
            if (mode == 0 || mode == 3 || mode == 4) {
                List<Object> entryList = new ArrayList<String>();
                int size = packetWrapper.read(Type.SHORT).shortValue();
                for (int i = 0; i < size; ++i) {
                    entryList.add(packetWrapper.read(Type.STRING));
                }
                entryList = entryList.stream().map((? super T it) -> it.length() > 16 ? it.substring(0, 16) : it).distinct().collect(Collectors.toList());
                packetWrapper.write(Type.SHORT, (short)entryList.size());
                for (String string : entryList) {
                    packetWrapper.write(Type.STRING, string);
                }
            }
        });
    }
}
