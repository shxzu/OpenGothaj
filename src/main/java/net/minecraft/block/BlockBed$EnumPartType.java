package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockBed$EnumPartType implements IStringSerializable
{
    HEAD("head"),
    FOOT("foot");

    private final String name;

    private BlockBed$EnumPartType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
