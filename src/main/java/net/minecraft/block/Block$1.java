package net.minecraft.block;

import net.minecraft.block.Block;

class Block$1
extends Block.SoundType {
    Block$1(String $anonymous0, float $anonymous1, float $anonymous2) {
        super($anonymous0, $anonymous1, $anonymous2);
    }

    @Override
    public String getBreakSound() {
        return "dig.glass";
    }

    @Override
    public String getPlaceSound() {
        return "step.stone";
    }
}
