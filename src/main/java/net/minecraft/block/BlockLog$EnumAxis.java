package net.minecraft.block;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

public enum BlockLog$EnumAxis implements IStringSerializable
{
    X("x"),
    Y("y"),
    Z("z"),
    NONE("none");

    private final String name;

    private BlockLog$EnumAxis(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public static BlockLog$EnumAxis fromFacingAxis(EnumFacing.Axis axis) {
        switch (axis) {
            case X: {
                return X;
            }
            case Y: {
                return Y;
            }
            case Z: {
                return Z;
            }
        }
        return NONE;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
