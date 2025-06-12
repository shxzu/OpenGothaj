package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage;

public enum WorldBorderEmulator$Side {
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0);

    public final int modX;
    public final int modZ;

    private WorldBorderEmulator$Side(int modX, int modZ) {
        this.modX = modX;
        this.modZ = modZ;
    }
}
