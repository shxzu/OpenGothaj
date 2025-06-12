package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockPistonExtension$EnumPistonType implements IStringSerializable
{
    DEFAULT("normal"),
    STICKY("sticky");

    private final String VARIANT;

    private BlockPistonExtension$EnumPistonType(String name) {
        this.VARIANT = name;
    }

    public String toString() {
        return this.VARIANT;
    }

    @Override
    public String getName() {
        return this.VARIANT;
    }
}
