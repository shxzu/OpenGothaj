package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityLavaFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.world.World;

public class EntityLavaFX$Factory
implements IParticleFactory {
    @Override
    public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_) {
        return new EntityLavaFX(worldIn, xCoordIn, yCoordIn, zCoordIn);
    }
}
