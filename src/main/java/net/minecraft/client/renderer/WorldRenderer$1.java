package net.minecraft.client.renderer;

import com.google.common.primitives.Floats;
import java.util.Comparator;

class WorldRenderer$1
implements Comparator<Integer> {
    private final float[] val$afloat;

    WorldRenderer$1(float[] fArray) {
        this.val$afloat = fArray;
    }

    @Override
    public int compare(Integer p_compare_1_, Integer p_compare_2_) {
        return Floats.compare(this.val$afloat[p_compare_2_], this.val$afloat[p_compare_1_]);
    }
}
