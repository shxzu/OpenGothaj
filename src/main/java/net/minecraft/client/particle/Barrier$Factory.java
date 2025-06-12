package net.minecraft.client.particle;

import net.minecraft.client.particle.Barrier;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class Barrier$Factory
implements IParticleFactory {
    @Override
    public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_) {
        return new Barrier(worldIn, xCoordIn, yCoordIn, zCoordIn, Item.getItemFromBlock(Blocks.barrier));
    }
}
