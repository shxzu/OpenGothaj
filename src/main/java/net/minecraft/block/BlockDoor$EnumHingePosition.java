package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockDoor$EnumHingePosition implements IStringSerializable
{
    LEFT,
    RIGHT;


    public String toString() {
        return this.getName();
    }

    @Override
    public String getName() {
        return this == LEFT ? "left" : "right";
    }
}
