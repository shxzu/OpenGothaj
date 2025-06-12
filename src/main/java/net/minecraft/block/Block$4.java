package net.minecraft.block;

import net.minecraft.block.Block;

class Block$4
extends Block.SoundType {
    Block$4(String $anonymous0, float $anonymous1, float $anonymous2) {
        super($anonymous0, $anonymous1, $anonymous2);
    }

    @Override
    public String getBreakSound() {
        return "mob.slime.big";
    }

    @Override
    public String getPlaceSound() {
        return "mob.slime.big";
    }

    @Override
    public String getStepSound() {
        return "mob.slime.small";
    }
}
