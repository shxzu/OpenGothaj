package com.viaversion.viabackwards.api.rewriters;

final class LegacyBlockItemRewriter$Pos {
    private final int x;
    private final short y;
    private final int z;

    private LegacyBlockItemRewriter$Pos(int x, int y, int z) {
        this.x = x;
        this.y = (short)y;
        this.z = z;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        LegacyBlockItemRewriter$Pos pos = (LegacyBlockItemRewriter$Pos)o;
        if (this.x != pos.x) {
            return false;
        }
        if (this.y != pos.y) {
            return false;
        }
        return this.z == pos.z;
    }

    public int hashCode() {
        int result = this.x;
        result = 31 * result + this.y;
        result = 31 * result + this.z;
        return result;
    }

    public String toString() {
        return "Pos{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
    }
}
