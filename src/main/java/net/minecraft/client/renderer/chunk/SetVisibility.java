package net.minecraft.client.renderer.chunk;

import java.util.Set;
import net.minecraft.util.EnumFacing;

public class SetVisibility {
    private static final int COUNT_FACES = EnumFacing.values().length;
    private long bits;

    public void setManyVisible(Set<EnumFacing> p_178620_1_) {
        for (EnumFacing enumfacing : p_178620_1_) {
            for (EnumFacing enumfacing1 : p_178620_1_) {
                this.setVisible(enumfacing, enumfacing1, true);
            }
        }
    }

    public void setVisible(EnumFacing facing, EnumFacing facing2, boolean p_178619_3_) {
        this.setBit(facing.ordinal() + facing2.ordinal() * COUNT_FACES, p_178619_3_);
        this.setBit(facing2.ordinal() + facing.ordinal() * COUNT_FACES, p_178619_3_);
    }

    public void setAllVisible(boolean visible) {
        this.bits = visible ? -1L : 0L;
    }

    public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
        return this.getBit(facing.ordinal() + facing2.ordinal() * COUNT_FACES);
    }

    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(' ');
        EnumFacing[] enumFacingArray = EnumFacing.values();
        int n = enumFacingArray.length;
        int n2 = 0;
        while (n2 < n) {
            EnumFacing enumfacing = enumFacingArray[n2];
            stringbuilder.append(' ').append(enumfacing.toString().toUpperCase().charAt(0));
            ++n2;
        }
        stringbuilder.append('\n');
        enumFacingArray = EnumFacing.values();
        n = enumFacingArray.length;
        n2 = 0;
        while (n2 < n) {
            EnumFacing enumfacing2 = enumFacingArray[n2];
            stringbuilder.append(enumfacing2.toString().toUpperCase().charAt(0));
            EnumFacing[] enumFacingArray2 = EnumFacing.values();
            int n3 = enumFacingArray2.length;
            int n4 = 0;
            while (n4 < n3) {
                EnumFacing enumfacing1 = enumFacingArray2[n4];
                if (enumfacing2 == enumfacing1) {
                    stringbuilder.append("  ");
                } else {
                    boolean flag = this.isVisible(enumfacing2, enumfacing1);
                    stringbuilder.append(' ').append(flag ? (char)'Y' : 'n');
                }
                ++n4;
            }
            stringbuilder.append('\n');
            ++n2;
        }
        return stringbuilder.toString();
    }

    private boolean getBit(int p_getBit_1_) {
        return (this.bits & (long)(1 << p_getBit_1_)) != 0L;
    }

    private void setBit(int p_setBit_1_, boolean p_setBit_2_) {
        if (p_setBit_2_) {
            this.setBit(p_setBit_1_);
        } else {
            this.clearBit(p_setBit_1_);
        }
    }

    private void setBit(int p_setBit_1_) {
        this.bits |= (long)(1 << p_setBit_1_);
    }

    private void clearBit(int p_clearBit_1_) {
        this.bits &= (long)(~(1 << p_clearBit_1_));
    }
}
