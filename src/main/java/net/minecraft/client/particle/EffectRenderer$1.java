package net.minecraft.client.particle;

import java.util.concurrent.Callable;
import net.minecraft.client.particle.EntityFX;

class EffectRenderer$1
implements Callable<String> {
    private final EntityFX val$particle;

    EffectRenderer$1(EntityFX entityFX) {
        this.val$particle = entityFX;
    }

    @Override
    public String call() throws Exception {
        return this.val$particle.toString();
    }
}
