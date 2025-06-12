package net.minecraft.client.audio;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import net.minecraft.client.audio.SoundList;

class SoundHandler$1
implements ParameterizedType {
    SoundHandler$1() {
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[]{String.class, SoundList.class};
    }

    @Override
    public Type getRawType() {
        return Map.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
