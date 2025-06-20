package com.viaversion.viaversion.api.minecraft;

public interface BlockChangeRecord {
    public byte getSectionX();

    public byte getSectionY();

    public byte getSectionZ();

    public short getY(int var1);

    default public short getY() {
        return this.getY(-1);
    }

    public int getBlockId();

    public void setBlockId(int var1);
}
