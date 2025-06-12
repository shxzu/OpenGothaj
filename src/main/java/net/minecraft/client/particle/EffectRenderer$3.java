package net.minecraft.client.particle;

import java.util.concurrent.Callable;
import net.minecraft.client.particle.EntityFX;

class EffectRenderer$3
implements Callable<String> {
    private final EntityFX val$entityfx;

    EffectRenderer$3(EntityFX entityFX) {
        this.val$entityfx = entityFX;
    }

    @Override
    public String call() throws Exception {
        return this.val$entityfx.toString();
    }
}
