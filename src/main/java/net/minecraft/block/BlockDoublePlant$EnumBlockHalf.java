package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockDoublePlant$EnumBlockHalf implements IStringSerializable
{
    UPPER,
    LOWER;


    public String toString() {
        return this.getName();
    }

    @Override
    public String getName() {
        return this == UPPER ? "upper" : "lower";
    }
}
