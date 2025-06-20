package xyz.cucumber.base.events.ext;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import xyz.cucumber.base.events.Event;

public class EventAirCollide
extends Event {
    public AxisAlignedBB returnValue;
    public World worldIn;
    public BlockPos pos;
    public IBlockState state;
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;

    public EventAirCollide(World worldIn, BlockPos pos, IBlockState state, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.worldIn = worldIn;
        this.pos = pos;
        this.state = state;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public AxisAlignedBB getReturnValue() {
        return this.returnValue;
    }

    public void setReturnValue(AxisAlignedBB returnValue) {
        this.returnValue = returnValue;
    }

    public World getWorldIn() {
        return this.worldIn;
    }

    public void setWorldIn(World worldIn) {
        this.worldIn = worldIn;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public IBlockState getState() {
        return this.state;
    }

    public void setState(IBlockState state) {
        this.state = state;
    }

    public double getMinX() {
        return this.minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMinY() {
        return this.minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMinZ() {
        return this.minZ;
    }

    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    public double getMaxX() {
        return this.maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMaxY() {
        return this.maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public double getMaxZ() {
        return this.maxZ;
    }

    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }
}
