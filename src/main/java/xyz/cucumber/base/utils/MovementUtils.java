package xyz.cucumber.base.utils;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.ext.EventHit;
import xyz.cucumber.base.events.ext.EventMoveFlying;
import xyz.cucumber.base.utils.math.RotationUtils;
import xyz.cucumber.base.utils.math.Vector3d;

public class MovementUtils {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static List<Vector3d> findPath(double tpX, double tpY, double tpZ, double offset) {
        ArrayList<Vector3d> positions = new ArrayList<Vector3d>();
        double steps = Math.ceil(MovementUtils.getDistance(MovementUtils.mc.thePlayer.posX, MovementUtils.mc.thePlayer.posY, MovementUtils.mc.thePlayer.posZ, tpX, tpY, tpZ) / offset);
        double dX = tpX - MovementUtils.mc.thePlayer.posX;
        double dY = tpY - MovementUtils.mc.thePlayer.posY;
        double dZ = tpZ - MovementUtils.mc.thePlayer.posZ;
        double d = 1.0;
        while (d <= steps) {
            positions.add(new Vector3d(MovementUtils.mc.thePlayer.posX + dX * d / steps, MovementUtils.mc.thePlayer.posY + dY * d / steps, MovementUtils.mc.thePlayer.posZ + dZ * d / steps));
            d += 1.0;
        }
        return positions;
    }

    public static float[] incrementMoveDirection(float forward, float strafe) {
        if (forward != 0.0f || strafe != 0.0f) {
            float value;
            float f = value = forward != 0.0f ? Math.abs(forward) : Math.abs(strafe);
            if (forward > 0.0f) {
                if (strafe > 0.0f) {
                    strafe = 0.0f;
                } else if (strafe == 0.0f) {
                    strafe = -value;
                } else if (strafe < 0.0f) {
                    forward = 0.0f;
                }
            } else if (forward == 0.0f) {
                forward = strafe > 0.0f ? value : -value;
            } else if (strafe < 0.0f) {
                strafe = 0.0f;
            } else if (strafe == 0.0f) {
                strafe = value;
            } else if (strafe > 0.0f) {
                forward = 0.0f;
            }
        }
        return new float[]{forward, strafe};
    }

    private static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double xDiff = x1 - x2;
        double yDiff = y1 - y2;
        double zDiff = z1 - z2;
        return MathHelper.sqrt_double(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }

    public static double getPredictedPlayerDistance(double x, double y, double z, int predict) {
        double posX = MovementUtils.mc.thePlayer.posX + (MovementUtils.mc.thePlayer.posX - MovementUtils.mc.thePlayer.lastTickPosX) * (double)predict;
        double posY = MovementUtils.mc.thePlayer.posY;
        double posZ = MovementUtils.mc.thePlayer.posZ + (MovementUtils.mc.thePlayer.posZ - MovementUtils.mc.thePlayer.lastTickPosZ) * (double)predict;
        double d0 = posX - x;
        double d1 = posY - y;
        double d2 = posZ - z;
        return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public static boolean isGoingDiagonally() {
        return Math.abs(MovementUtils.mc.thePlayer.motionX) > 0.04 && Math.abs(MovementUtils.mc.thePlayer.motionZ) > 0.04;
    }

    public static boolean isLookingDiagonally() {
        return MathHelper.wrapAngleTo180_float(MovementUtils.mc.thePlayer.rotationYaw) >= 160.0f || MathHelper.wrapAngleTo180_float(MovementUtils.mc.thePlayer.rotationYaw) <= 20.0f || MathHelper.wrapAngleTo180_float(MovementUtils.mc.thePlayer.rotationYaw) >= 70.0f && MathHelper.wrapAngleTo180_float(MovementUtils.mc.thePlayer.rotationYaw) <= 110.0f;
    }

    public static int depthStriderLevel() {
        return EnchantmentHelper.getDepthStriderModifier(MovementUtils.mc.thePlayer);
    }

    public static double jumpBoostMotion(double motionY) {
        if (MovementUtils.mc.thePlayer.isPotionActive(Potion.jump)) {
            return motionY + (double)((float)(MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f);
        }
        return motionY;
    }

    public static boolean canSprint(boolean legit) {
        return legit ? !(!(MovementUtils.mc.thePlayer.moveForward >= 0.8f) || MovementUtils.mc.thePlayer.isCollidedHorizontally || MovementUtils.mc.thePlayer.getFoodStats().getFoodLevel() <= 6 && !MovementUtils.mc.thePlayer.capabilities.allowFlying || MovementUtils.mc.thePlayer.isPotionActive(Potion.blindness) || MovementUtils.mc.thePlayer.isUsingItem() || MovementUtils.mc.thePlayer.isSneaking()) : MovementUtils.enoughMovementForSprinting();
    }

    public static boolean enoughMovementForSprinting() {
        return Math.abs(MovementUtils.mc.thePlayer.moveForward) >= 0.8f || Math.abs(MovementUtils.mc.thePlayer.moveStrafing) >= 0.8f;
    }

    public static double getPredictedMotionY(double motionY) {
        return (motionY - 0.08) * (double)0.98f;
    }

    public static void forward(double length) {
        double angleA = Math.toRadians(MovementUtils.normalizeAngle(MovementUtils.mc.thePlayer.rotationYawHead - 90.0f));
        MovementUtils.mc.thePlayer.setPosition(MovementUtils.mc.thePlayer.posX - Math.cos(angleA) * length, MovementUtils.mc.thePlayer.posY, MovementUtils.mc.thePlayer.posZ - Math.sin(angleA) * length);
    }

    public static double normalizeAngle(double angle) {
        return (angle + 360.0) % 360.0;
    }

    public static double roundToOnGround(double posY) {
        return posY - posY % 0.015625;
    }

    public static double distanceToGround(Vec3 vec3) {
        double playerY = vec3.yCoord;
        return playerY - (double)MovementUtils.getBlockBellow(vec3).getY() - 1.0;
    }

    public static double distanceToGround() {
        Vec3 vec3 = MovementUtils.mc.thePlayer.getPositionVector();
        double playerY = vec3.yCoord;
        return playerY - (double)MovementUtils.getBlockBellow(vec3).getY() - 1.0;
    }

    public static BlockPos getBlockBellow(Vec3 playerPos) {
        while (playerPos.yCoord > 0.0) {
            BlockPos blockPos = new BlockPos(playerPos);
            if (!MovementUtils.isAir(blockPos)) {
                return blockPos;
            }
            playerPos = playerPos.addVector(0.0, -1.0, 0.0);
        }
        return BlockPos.ORIGIN;
    }

    public static boolean isAir(BlockPos blockPos) {
        return MovementUtils.mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.air;
    }

    public static double getDirection(float yaw) {
        float rotationYaw = yaw;
        if (MovementUtils.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (MovementUtils.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (MovementUtils.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (MovementUtils.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (MovementUtils.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }

    public static double getDirectionKeybinds(float yaw) {
        float rotationYaw = yaw;
        if (!Keyboard.isKeyDown((int)MovementUtils.mc.gameSettings.keyBindForward.getKeyCode()) && Keyboard.isKeyDown((int)MovementUtils.mc.gameSettings.keyBindBack.getKeyCode())) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (!Keyboard.isKeyDown((int)MovementUtils.mc.gameSettings.keyBindForward.getKeyCode()) && Keyboard.isKeyDown((int)MovementUtils.mc.gameSettings.keyBindBack.getKeyCode())) {
            forward = -0.5f;
        } else if (Keyboard.isKeyDown((int)MovementUtils.mc.gameSettings.keyBindForward.getKeyCode()) && !Keyboard.isKeyDown((int)MovementUtils.mc.gameSettings.keyBindBack.getKeyCode())) {
            forward = 0.5f;
        }
        if (Keyboard.isKeyDown((int)MovementUtils.mc.gameSettings.keyBindLeft.getKeyCode()) && !Keyboard.isKeyDown((int)MovementUtils.mc.gameSettings.keyBindRight.getKeyCode())) {
            rotationYaw -= 90.0f * forward;
        }
        if (!Keyboard.isKeyDown((int)MovementUtils.mc.gameSettings.keyBindLeft.getKeyCode()) && Keyboard.isKeyDown((int)MovementUtils.mc.gameSettings.keyBindRight.getKeyCode())) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }

    public static double getDirection(float yaw, float moveForward, float moveStrafing) {
        float rotationYaw = yaw;
        if (moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (moveForward < 0.0f) {
            forward = -0.5f;
        } else if (moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (MovementUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.2 * (double)(MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }

    public static double getSpeed() {
        return Math.hypot(MovementUtils.mc.thePlayer.motionX, MovementUtils.mc.thePlayer.motionZ);
    }

    public static double getEntitySpeed(Entity entity) {
        return Math.hypot(entity.lastTickPosX - entity.posX, entity.lastTickPosZ - entity.posZ);
    }

    public static boolean isMoving() {
        return Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown() || Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown() || Minecraft.getMinecraft().gameSettings.keyBindRight.isKeyDown() || Minecraft.getMinecraft().gameSettings.keyBindLeft.isKeyDown();
    }

    public static void strafe(float speed) {
        double yaw = MovementUtils.getDirection(MovementUtils.mc.thePlayer.rotationYaw);
        MovementUtils.mc.thePlayer.motionX = -Math.sin((float)yaw) * (double)speed;
        MovementUtils.mc.thePlayer.motionZ = Math.cos((float)yaw) * (double)speed;
    }

    public static void strafe(float speed, float yaw) {
        MovementUtils.mc.thePlayer.motionX = -Math.sin(yaw) * (double)speed;
        MovementUtils.mc.thePlayer.motionZ = Math.cos(yaw) * (double)speed;
    }

    public static void strafe(float speed, float yaw, float moveForward, float moveStrafing) {
        yaw = (float)MovementUtils.getDirection(yaw, moveForward, moveStrafing);
        MovementUtils.mc.thePlayer.motionX = -Math.sin(yaw) * (double)speed;
        MovementUtils.mc.thePlayer.motionZ = Math.cos(yaw) * (double)speed;
    }

    public static void strafe() {
        double yaw = MovementUtils.getDirection(MovementUtils.mc.thePlayer.rotationYaw);
        MovementUtils.mc.thePlayer.motionX = -Math.sin((float)yaw) * MovementUtils.getSpeed();
        MovementUtils.mc.thePlayer.motionZ = Math.cos((float)yaw) * MovementUtils.getSpeed();
    }

    public static void silentMoveFix(EventMoveFlying event) {
        float d;
        int dif = (int)((MathHelper.wrapAngleTo180_float(MovementUtils.mc.thePlayer.rotationYaw - RotationUtils.serverYaw - 23.5f - 135.0f) + 180.0f) / 45.0f);
        float yaw = RotationUtils.serverYaw;
        float strafe = event.getStrafe();
        float forward = event.getForward();
        float friction = event.getFriction();
        float calcForward = 0.0f;
        float calcStrafe = 0.0f;
        switch (dif) {
            case 0: {
                calcForward = forward;
                calcStrafe = strafe;
                break;
            }
            case 1: {
                calcForward += forward;
                calcStrafe -= forward;
                calcForward += strafe;
                calcStrafe += strafe;
                break;
            }
            case 2: {
                calcForward = strafe;
                calcStrafe = -forward;
                break;
            }
            case 3: {
                calcForward -= forward;
                calcStrafe -= forward;
                calcForward += strafe;
                calcStrafe -= strafe;
                break;
            }
            case 4: {
                calcForward = -forward;
                calcStrafe = -strafe;
                break;
            }
            case 5: {
                calcForward -= forward;
                calcStrafe += forward;
                calcForward -= strafe;
                calcStrafe -= strafe;
                break;
            }
            case 6: {
                calcForward = -strafe;
                calcStrafe = forward;
                break;
            }
            case 7: {
                calcForward += forward;
                calcStrafe += forward;
                calcForward -= strafe;
                calcStrafe += strafe;
            }
        }
        if (calcForward > 1.0f || calcForward < 0.9f && calcForward > 0.3f || calcForward < -1.0f || calcForward > -0.9f && calcForward < -0.3f) {
            calcForward *= 0.5f;
        }
        if (calcStrafe > 1.0f || calcStrafe < 0.9f && calcStrafe > 0.3f || calcStrafe < -1.0f || calcStrafe > -0.9f && calcStrafe < -0.3f) {
            calcStrafe *= 0.5f;
        }
        if ((d = calcStrafe * calcStrafe + calcForward * calcForward) >= 1.0E-4f) {
            if ((d = MathHelper.sqrt_float(d)) < 1.0f) {
                d = 1.0f;
            }
            d = friction / d;
            float yawSin = MathHelper.sin((float)((double)yaw * Math.PI / 180.0));
            float yawCos = MathHelper.cos((float)((double)yaw * Math.PI / 180.0));
            MovementUtils.mc.thePlayer.motionX += (double)((calcStrafe *= d) * yawCos - (calcForward *= d) * yawSin);
            MovementUtils.mc.thePlayer.motionZ += (double)(calcForward * yawCos + calcStrafe * yawSin);
        }
    }

    public static boolean isOnGround(double height) {
        return !MovementUtils.mc.theWorld.getCollidingBoundingBoxes(MovementUtils.mc.thePlayer, MovementUtils.mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }

    public static double[] getPredictedPos(float forward, float strafe, double motionX, double motionY, double motionZ, double posX, double posY, double posZ, boolean isJumping) {
        strafe *= 0.98f;
        forward *= 0.98f;
        float f4 = 0.91f;
        boolean isSprinting = MovementUtils.mc.thePlayer.isSprinting();
        if (isJumping && MovementUtils.mc.thePlayer.onGround && MovementUtils.mc.thePlayer.jumpTicks == 0) {
            motionY = 0.42;
            if (MovementUtils.mc.thePlayer.isPotionActive(Potion.jump)) {
                motionY += (double)((float)(MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f);
            }
            if (isSprinting) {
                float f5 = MovementUtils.mc.thePlayer.rotationYaw * ((float)Math.PI / 180);
                motionX -= (double)(MathHelper.sin(f5) * 0.2f);
                motionZ += (double)(MathHelper.cos(f5) * 0.2f);
            }
        }
        if (MovementUtils.mc.thePlayer.onGround) {
            f4 = MovementUtils.mc.thePlayer.worldObj.getBlockState((BlockPos)new BlockPos((int)MathHelper.floor_double((double)posX), (int)(MathHelper.floor_double((double)posY) - 1), (int)MathHelper.floor_double((double)posZ))).getBlock().slipperiness * 0.91f;
        }
        float f6 = 0.16277136f / (f4 * f4 * f4);
        float friction = MovementUtils.mc.thePlayer.onGround ? MovementUtils.mc.thePlayer.getAIMoveSpeed() * f6 : MovementUtils.mc.thePlayer.jumpMovementFactor;
        float f7 = strafe * strafe + forward * forward;
        if (f7 >= 1.0E-4f) {
            if ((f7 = MathHelper.sqrt_float(f7)) < 1.0f) {
                f7 = 1.0f;
            }
            f7 = friction / f7;
            float f8 = MathHelper.sin(MovementUtils.mc.thePlayer.rotationYaw * (float)Math.PI / 180.0f);
            float f9 = MathHelper.cos(MovementUtils.mc.thePlayer.rotationYaw * (float)Math.PI / 180.0f);
            motionX += (double)((strafe *= f7) * f9 - (forward *= f7) * f8);
            motionZ += (double)(forward * f9 + strafe * f8);
        }
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        f4 = 0.91f;
        if (MovementUtils.mc.thePlayer.onGround) {
            f4 = MovementUtils.mc.thePlayer.worldObj.getBlockState((BlockPos)new BlockPos((int)MathHelper.floor_double((double)posX), (int)(MathHelper.floor_double((double)MovementUtils.mc.thePlayer.getEntityBoundingBox().minY) - 1), (int)MathHelper.floor_double((double)posZ))).getBlock().slipperiness * 0.91f;
        }
        motionY = !(!MovementUtils.mc.thePlayer.worldObj.isRemote || MovementUtils.mc.thePlayer.worldObj.isBlockLoaded(new BlockPos((int)posX, 0, (int)posZ)) && MovementUtils.mc.thePlayer.worldObj.getChunkFromBlockCoords(new BlockPos((int)posX, 0, (int)posZ)).isLoaded()) ? (posY > 0.0 ? -0.1 : 0.0) : (motionY -= 0.08);
        return new double[]{posX, posY, posZ, motionX *= (double)f4, motionY *= (double)0.98f, motionZ *= (double)f4};
    }

    public static Vec3 getPredictedPos(boolean isHitting, Entity targetEntity, float forward, float strafe) {
        float f5;
        strafe *= 0.98f;
        forward *= 0.98f;
        float f4 = 0.91f;
        double motionX = MovementUtils.mc.thePlayer.motionX;
        double motionZ = MovementUtils.mc.thePlayer.motionZ;
        double motionY = MovementUtils.mc.thePlayer.motionY;
        boolean isSprinting = MovementUtils.mc.thePlayer.isSprinting();
        if (isHitting) {
            f5 = (float)MovementUtils.mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            float f6 = 0.0f;
            f6 = targetEntity instanceof EntityLivingBase ? EnchantmentHelper.getModifierForCreature(MovementUtils.mc.thePlayer.getHeldItem(), ((EntityLivingBase)targetEntity).getCreatureAttribute()) : EnchantmentHelper.getModifierForCreature(MovementUtils.mc.thePlayer.getHeldItem(), EnumCreatureAttribute.UNDEFINED);
            if (f5 > 0.0f || f6 > 0.0f) {
                boolean flag2;
                int i = EnchantmentHelper.getKnockbackModifier(MovementUtils.mc.thePlayer);
                if (MovementUtils.mc.thePlayer.isSprinting()) {
                    ++i;
                }
                if ((flag2 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(MovementUtils.mc.thePlayer), f5)) && i > 0) {
                    EventHit event = new EventHit(false);
                    Client.INSTANCE.getEventBus().call(event);
                    motionX *= 0.6;
                    motionZ *= 0.6;
                    isSprinting = false;
                }
            }
        }
        if (MovementUtils.mc.thePlayer.isJumping && MovementUtils.mc.thePlayer.onGround && MovementUtils.mc.thePlayer.jumpTicks == 0) {
            motionY = 0.42;
            if (MovementUtils.mc.thePlayer.isPotionActive(Potion.jump)) {
                motionY += (double)((float)(MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f);
            }
            if (isSprinting) {
                f5 = MovementUtils.mc.thePlayer.rotationYaw * ((float)Math.PI / 180);
                motionX -= (double)(MathHelper.sin(f5) * 0.2f);
                motionZ += (double)(MathHelper.cos(f5) * 0.2f);
            }
        }
        if (MovementUtils.mc.thePlayer.onGround) {
            f4 = MovementUtils.mc.thePlayer.worldObj.getBlockState((BlockPos)new BlockPos((int)MathHelper.floor_double((double)MovementUtils.mc.thePlayer.posX), (int)(MathHelper.floor_double((double)MovementUtils.mc.thePlayer.getEntityBoundingBox().minY) - 1), (int)MathHelper.floor_double((double)MovementUtils.mc.thePlayer.posZ))).getBlock().slipperiness * 0.91f;
        }
        float f7 = 0.16277136f / (f4 * f4 * f4);
        float friction = MovementUtils.mc.thePlayer.onGround ? MovementUtils.mc.thePlayer.getAIMoveSpeed() * f7 : MovementUtils.mc.thePlayer.jumpMovementFactor;
        float f8 = strafe * strafe + forward * forward;
        if (f8 >= 1.0E-4f) {
            if ((f8 = MathHelper.sqrt_float(f8)) < 1.0f) {
                f8 = 1.0f;
            }
            f8 = friction / f8;
            float f9 = MathHelper.sin(MovementUtils.mc.thePlayer.rotationYaw * (float)Math.PI / 180.0f);
            float f10 = MathHelper.cos(MovementUtils.mc.thePlayer.rotationYaw * (float)Math.PI / 180.0f);
            motionX += (double)((strafe *= f8) * f10 - (forward *= f8) * f9);
            motionZ += (double)(forward * f10 + strafe * f9);
        }
        f4 = 0.91f;
        if (MovementUtils.mc.thePlayer.onGround) {
            f4 = MovementUtils.mc.thePlayer.worldObj.getBlockState((BlockPos)new BlockPos((int)MathHelper.floor_double((double)MovementUtils.mc.thePlayer.posX), (int)(MathHelper.floor_double((double)MovementUtils.mc.thePlayer.getEntityBoundingBox().minY) - 1), (int)MathHelper.floor_double((double)MovementUtils.mc.thePlayer.posZ))).getBlock().slipperiness * 0.91f;
        }
        return new Vec3(motionX *= (double)f4, motionY *= (double)0.98f, motionZ *= (double)f4);
    }

    public static double[] getMotion(double speed, float strafe, float forward, float yaw) {
        float friction = (float)speed;
        float f1 = MathHelper.sin((float)Math.toRadians(yaw));
        float f2 = MathHelper.cos((float)Math.toRadians(yaw));
        double motionX = strafe * friction * f2 - forward * friction * f1;
        double motionZ = forward * friction * f2 + strafe * friction * f1;
        return new double[]{motionX, motionZ};
    }

    public static float[] silentStrafe(float strafe, float forward, float yaw, boolean advanced) {
        int dif = (int)((MathHelper.wrapAngleTo180_float(MovementUtils.mc.thePlayer.rotationYaw - RotationUtils.serverYaw - 23.5f - 135.0f) + 180.0f) / 45.0f);
        float calcForward = forward;
        float calcStrafe = strafe;
        switch (dif) {
            case 0: {
                calcForward = forward;
                calcStrafe = strafe;
                break;
            }
            case 1: {
                calcForward += forward;
                calcStrafe -= forward;
                calcForward += strafe;
                calcStrafe += strafe;
                break;
            }
            case 2: {
                calcForward = strafe;
                calcStrafe = -forward;
                break;
            }
            case 3: {
                calcForward -= forward;
                calcStrafe -= forward;
                calcForward += strafe;
                calcStrafe -= strafe;
                break;
            }
            case 4: {
                calcForward = -forward;
                calcStrafe = -strafe;
                break;
            }
            case 5: {
                calcForward -= forward;
                calcStrafe += forward;
                calcForward -= strafe;
                calcStrafe -= strafe;
                break;
            }
            case 6: {
                calcForward = -strafe;
                calcStrafe = forward;
                break;
            }
            case 7: {
                calcForward += forward;
                calcStrafe += forward;
                calcForward -= strafe;
                calcStrafe += strafe;
            }
        }
        if (calcForward > 1.0f || calcForward < 0.9f && calcForward > 0.3f || calcForward < -1.0f || calcForward > -0.9f && calcForward < -0.3f) {
            calcForward *= 0.5f;
        }
        if (calcStrafe > 1.0f || calcStrafe < 0.9f && calcStrafe > 0.3f || calcStrafe < -1.0f || calcStrafe > -0.9f && calcStrafe < -0.3f) {
            calcStrafe *= 0.5f;
        }
        return new float[]{calcStrafe, calcForward};
    }

    public static double predictedMotion(double motion, int ticks) {
        if (ticks == 0) {
            return motion;
        }
        double predicted = motion;
        int i = 0;
        while (i < ticks) {
            predicted = (predicted - 0.08) * (double)0.98f;
            ++i;
        }
        return predicted;
    }
}
