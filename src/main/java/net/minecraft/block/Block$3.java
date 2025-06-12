package net.minecraft.block;

import net.minecraft.block.Block;

class Block$3
extends Block.SoundType {
    Block$3(String $anonymous0, float $anonymous1, float $anonymous2) {
        super($anonymous0, $anonymous1, $anonymous2);
    }

    @Override
    public String getBreakSound() {
        return "dig.stone";
    }

    @Override
    public String getPlaceSound() {
        return "random.anvil_land";
    }
}
