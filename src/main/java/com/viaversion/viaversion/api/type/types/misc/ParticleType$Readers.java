package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import com.viaversion.viaversion.util.Key;

public final class ParticleType$Readers {
    public static final ParticleType.ParticleReader BLOCK = (buf, particle) -> particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
    public static final ParticleType.ParticleReader ITEM1_13 = ParticleType.itemHandler(Type.ITEM1_13);
    public static final ParticleType.ParticleReader ITEM1_13_2 = ParticleType.itemHandler(Type.ITEM1_13_2);
    public static final ParticleType.ParticleReader ITEM1_20_2 = ParticleType.itemHandler(Type.ITEM1_20_2);
    public static final ParticleType.ParticleReader DUST = (buf, particle) -> {
        particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
    };
    public static final ParticleType.ParticleReader DUST_TRANSITION = (buf, particle) -> {
        particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
    };
    public static final ParticleType.ParticleReader VIBRATION = (buf, particle) -> {
        particle.add(Type.POSITION1_14, Type.POSITION1_14.read(buf));
        String resourceLocation = (String)Type.STRING.read(buf);
        particle.add(Type.STRING, resourceLocation);
        resourceLocation = Key.stripMinecraftNamespace(resourceLocation);
        if (resourceLocation.equals("block")) {
            particle.add(Type.POSITION1_14, Type.POSITION1_14.read(buf));
        } else if (resourceLocation.equals("entity")) {
            particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
        } else {
            Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + resourceLocation);
        }
        particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
    };
    public static final ParticleType.ParticleReader VIBRATION1_19 = (buf, particle) -> {
        String resourceLocation = (String)Type.STRING.read(buf);
        particle.add(Type.STRING, resourceLocation);
        resourceLocation = Key.stripMinecraftNamespace(resourceLocation);
        if (resourceLocation.equals("block")) {
            particle.add(Type.POSITION1_14, Type.POSITION1_14.read(buf));
        } else if (resourceLocation.equals("entity")) {
            particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        } else {
            Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + resourceLocation);
        }
        particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
    };
    public static final ParticleType.ParticleReader VIBRATION1_20_3 = (buf, particle) -> {
        int sourceTypeId = Type.VAR_INT.readPrimitive(buf);
        particle.add(Type.VAR_INT, sourceTypeId);
        if (sourceTypeId == 0) {
            particle.add(Type.POSITION1_14, Type.POSITION1_14.read(buf));
        } else if (sourceTypeId == 1) {
            particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        } else {
            Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + sourceTypeId);
        }
        particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
    };
    public static final ParticleType.ParticleReader SCULK_CHARGE = (buf, particle) -> particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
    public static final ParticleType.ParticleReader SHRIEK = (buf, particle) -> particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
}
