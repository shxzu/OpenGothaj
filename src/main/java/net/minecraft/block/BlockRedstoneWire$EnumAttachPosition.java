package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

enum BlockRedstoneWire$EnumAttachPosition implements IStringSerializable
{
    UP("up"),
    SIDE("side"),
    NONE("none");

    private final String name;

    private BlockRedstoneWire$EnumAttachPosition(String name) {
        this.name = name;
    }

    public String toString() {
        return this.getName();
    }

    @Override
    public String getName() {
        return this.name;
    }
}
