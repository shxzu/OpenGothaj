package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockRedstoneComparator$Mode implements IStringSerializable
{
    COMPARE("compare"),
    SUBTRACT("subtract");

    private final String name;

    private BlockRedstoneComparator$Mode(String name) {
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
