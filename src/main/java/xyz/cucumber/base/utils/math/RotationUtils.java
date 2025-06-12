package xyz.cucumber.base.utils.math;

import com.google.common.base.Predicates;
import io.netty.util.internal.ThreadLocalRandom;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.optifine.reflect.Reflector;
import xyz.cucumber.base.utils.math.Vector3d;

public class RotationUtils {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static float serverYaw;
    public static float serverPitch;
    public static boolean customRots;

    public static float interpolateRotation(float current, float predicted, float percentage) {
        float f = MathHelper.wrapAngleTo180_float(predicted - current);
        if (f <= 10.0f && f >= -10.0f) {
            percentage = 1.0f;
        }
        return current + percentage * f;
    }

    public static double fovFromEntity(Entity en) {
        return ((double)(RotationUtils.mc.thePlayer.rotationYaw - RotationUtils.fovToEntity(en)) % 360.0 + 540.0) % 360.0 - 180.0;
    }

    public static double fovFromPosition(double[] pos) {
        return ((double)(RotationUtils.mc.thePlayer.rotationYaw - RotationUtils.fovToPosition(pos)) % 360.0 + 540.0) % 360.0 - 180.0;
    }

    public static float fovToEntity(Entity ent) {
        double x = ent.posX - RotationUtils.mc.thePlayer.posX;
        double z = ent.posZ - RotationUtils.mc.thePlayer.posZ;
        double yaw = (float)(MathHelper.atan2(z, x) * 180.0 / Math.PI - 90.0);
        return (float)yaw;
    }

    public static float getRotationDifference(float rot1, float rot2) {
        float angle = Math.abs(rot1 - rot2);
        if (angle > 180.0f) {
            return 360.0f - angle;
        }
        return angle;
    }

    public static float fovToPosition(double[] pos) {
        double x = pos[0] - RotationUtils.mc.thePlayer.posX;
        double z = pos[1] - RotationUtils.mc.thePlayer.posZ;
        double yaw = (float)(MathHelper.atan2(z, x) * 180.0 / Math.PI - 90.0);
        return (float)yaw;
    }

    public static Entity rayTrace(double range, float[] rotations) {
        if (RotationUtils.mc.objectMouseOver.entityHit != null) {
            return RotationUtils.mc.objectMouseOver.entityHit;
        }
        Vec3 vec3 = Minecraft.getMinecraft().thePlayer.getPositionEyes(1.0f);
        Vec3 vec31 = RotationUtils.mc.thePlayer.getVectorForRotation(rotations[1], rotations[0]);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);
        Entity pointedEntity = null;
        float f = 1.0f;
        List<Entity> list = Minecraft.getMinecraft().theWorld.getEntitiesInAABBexcluding(Minecraft.getMinecraft().getRenderViewEntity(), Minecraft.getMinecraft().getRenderViewEntity().getEntityBoundingBox().addCoord(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range).expand(f, f, f), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
        double d2 = range;
        Iterator<Entity> iterator = list.iterator();
        while (iterator.hasNext()) {
            double d3;
            Entity o;
            Entity entity1 = o = iterator.next();
            float f1 = entity1.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
            if (axisalignedbb.isVecInside(vec3)) {
                if (!(d2 >= 0.0)) continue;
                pointedEntity = entity1;
                d2 = 0.0;
                continue;
            }
            if (movingobjectposition == null || !((d3 = vec3.distanceTo(movingobjectposition.hitVec)) < d2) && d2 != 0.0) continue;
            boolean flag2 = false;
            if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
            }
            if (entity1 == Minecraft.getMinecraft().getRenderViewEntity().ridingEntity && !flag2) {
                if (d2 != 0.0) continue;
                pointedEntity = entity1;
                continue;
            }
            pointedEntity = entity1;
            d2 = d3;
        }
        return pointedEntity;
    }

    public static float[] positionRotation(double posX, double posY, double posZ, float[] lastRots, float yawSpeed, float pitchSpeed, boolean random) {
        double x = posX - RotationUtils.mc.thePlayer.posX;
        double y = posY - (RotationUtils.mc.thePlayer.posY + (double)RotationUtils.mc.thePlayer.getEyeHeight());
        double z = posZ - RotationUtils.mc.thePlayer.posZ;
        float calcYaw = (float)(MathHelper.atan2(z, x) * 180.0 / Math.PI - 90.0);
        float calcPitch = (float)(-(MathHelper.atan2(y, MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
        float yaw = RotationUtils.updateRotation(lastRots[0], calcYaw, yawSpeed);
        float pitch = RotationUtils.updateRotation(lastRots[1], calcPitch, pitchSpeed);
        if (random) {
            yaw += (float)ThreadLocalRandom.current().nextGaussian();
            pitch += (float)ThreadLocalRandom.current().nextGaussian();
        }
        return new float[]{yaw, pitch};
    }

    public static boolean lookingAtBlock(BlockPos blockPos, float yaw, float pitch, EnumFacing enumFacing, boolean strict) {
        MovingObjectPosition movingObjectPosition = RotationUtils.mc.thePlayer.rayTraceCustom(RotationUtils.mc.playerController.getBlockReachDistance(), RotationUtils.mc.timer.renderPartialTicks, yaw, pitch);
        if (movingObjectPosition == null) {
            return false;
        }
        Vec3 hitVec = movingObjectPosition.hitVec;
        if (hitVec == null) {
            return false;
        }
        return movingObjectPosition.getBlockPos().equals(blockPos) && (!strict || movingObjectPosition.sideHit == enumFacing && movingObjectPosition.sideHit != null);
    }

    public static float[] getRotationsToBlock(BlockPos blockPos, EnumFacing enumFacing) {
        double x = (double)blockPos.getX() + 0.5 - RotationUtils.mc.thePlayer.posX + (double)enumFacing.getFrontOffsetX() / 2.0;
        double z = (double)blockPos.getZ() + 0.5 - RotationUtils.mc.thePlayer.posZ + (double)enumFacing.getFrontOffsetZ() / 2.0;
        double y = (double)blockPos.getY() + 0.5;
        double dist = RotationUtils.mc.thePlayer.getDistance((double)blockPos.getX() + 0.5 + (double)enumFacing.getFrontOffsetX() / 2.0, blockPos.getY(), (double)blockPos.getZ() + 0.5 + (double)enumFacing.getFrontOffsetZ() / 2.0);
        double d1 = RotationUtils.mc.thePlayer.posY + (double)RotationUtils.mc.thePlayer.getEyeHeight() - (y += 0.5);
        double d3 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(Math.atan2(d1, d3) * 180.0 / Math.PI);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return new float[]{yaw, pitch};
    }

    public static float getYawBasedPitch(BlockPos blockPos, EnumFacing facing, float currentYaw, float lastPitch, int maxPitch) {
        float validPitch = lastPitch;
        float increment = (float)(Math.random() / 20.0) + 0.05f;
        float i = maxPitch;
        while (i > 45.0f) {
            MovingObjectPosition ray = RotationUtils.rayCast(1.0f, new float[]{currentYaw, i}, RotationUtils.mc.playerController.getBlockReachDistance(), 2.0);
            if (ray.getBlockPos() == null || ray.sideHit == null) {
                return validPitch;
            }
            if (ray.getBlockPos().equalsBlockPos(blockPos) && ray.sideHit == facing) {
                return i;
            }
            i -= increment;
        }
        return validPitch;
    }

    public static MovingObjectPosition rayCast(float partialTicks, float[] rots, double range, double hitBoxExpand) {
        MovingObjectPosition objectMouseOver = null;
        Entity entity = mc.getRenderViewEntity();
        if (entity != null && RotationUtils.mc.theWorld != null) {
            RotationUtils.mc.mcProfiler.startSection("pick");
            RotationUtils.mc.pointedEntity = null;
            double d0 = range;
            objectMouseOver = entity.rayTraceCustom(d0, partialTicks, rots[0], rots[1]);
            double d2 = d0;
            Vec3 vec3 = entity.getPositionEyes(partialTicks);
            boolean flag = false;
            boolean flag2 = true;
            if (RotationUtils.mc.playerController.extendedReach()) {
                d0 = 6.0;
                d2 = 6.0;
            } else if (d0 > 3.0) {
                flag = true;
            }
            if (objectMouseOver != null) {
                d2 = objectMouseOver.hitVec.distanceTo(vec3);
            }
            Vec3 vec4 = entity.getLookCustom(partialTicks, rots[0], rots[1]);
            Vec3 vec5 = vec3.addVector(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0);
            Entity pointedEntity = null;
            Vec3 vec6 = null;
            float f = 1.0f;
            List<Entity> list = RotationUtils.mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0).expand(1.0, 1.0, 1.0), Predicates.and(EntitySelectors.NOT_SPECTATING));
            double d3 = d2;
            Object realBB = null;
            int i = 0;
            while (i < list.size()) {
                double d4;
                Entity entity2 = list.get(i);
                float f2 = (float)((double)entity2.getCollisionBorderSize() + hitBoxExpand);
                AxisAlignedBB axisalignedbb = entity2.getEntityBoundingBox().expand(f2, f2, f2);
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec5);
                if (axisalignedbb.isVecInside(vec3)) {
                    if (d3 >= 0.0) {
                        pointedEntity = entity2;
                        vec6 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                        d3 = 0.0;
                    }
                } else if (movingobjectposition != null && ((d4 = vec3.distanceTo(movingobjectposition.hitVec)) < d3 || d3 == 0.0)) {
                    boolean flag3 = false;
                    if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                        flag3 = Reflector.callBoolean(entity2, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                    }
                    if (entity2 == entity.ridingEntity && !flag3) {
                        if (d3 == 0.0) {
                            pointedEntity = entity2;
                            vec6 = movingobjectposition.hitVec;
                        }
                    } else {
                        pointedEntity = entity2;
                        vec6 = movingobjectposition.hitVec;
                        d3 = d4;
                    }
                }
                ++i;
            }
            if (pointedEntity != null && flag && vec3.distanceTo(vec6) > range) {
                pointedEntity = null;
                objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec6, null, new BlockPos(vec6));
            }
            if (pointedEntity != null && (d3 < d2 || objectMouseOver == null)) {
                objectMouseOver = new MovingObjectPosition(pointedEntity, vec6);
            }
        }
        return objectMouseOver;
    }

    public static float[] getFixedRotation(float[] rotations, float[] lastRotations) {
        float yaw = rotations[0];
        float pitch = rotations[1];
        float lastYaw = lastRotations[0];
        float lastPitch = lastRotations[1];
        float f = RotationUtils.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f1 = f * f * f * 8.0f;
        float deltaYaw = yaw - lastYaw;
        float deltaPitch = pitch - lastPitch;
        float fixedDeltaYaw = deltaYaw - deltaYaw % f1;
        float fixedDeltaPitch = deltaPitch - deltaPitch % f1;
        float fixedYaw = lastYaw + fixedDeltaYaw;
        float fixedPitch = lastPitch + fixedDeltaPitch;
        return new float[]{fixedYaw, fixedPitch};
    }

    public static double getDirectionWrappedTo90() {
        float rotationYaw = RotationUtils.mc.thePlayer.rotationYaw;
        if (RotationUtils.mc.thePlayer.moveForward < 0.0f && RotationUtils.mc.thePlayer.moveStrafing == 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (RotationUtils.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f;
        }
        if (RotationUtils.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f;
        }
        return Math.toRadians(rotationYaw);
    }

    public static float[] getDirectionToBlock(double x, double y, double z, EnumFacing enumfacing) {
        EntityEgg face = new EntityEgg(RotationUtils.mc.theWorld);
        face.posX = x + 0.5;
        face.posY = y + 0.5;
        face.posZ = z + 0.5;
        face.posX += (double)enumfacing.getDirectionVec().getX() * 0.5;
        face.posY += (double)enumfacing.getDirectionVec().getY() * 0.5;
        face.posZ += (double)enumfacing.getDirectionVec().getZ() * 0.5;
        return RotationUtils.getRotationFromPosition(face.posX, face.posY, face.posZ);
    }

    public static double getMouseGCD() {
        float sens = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float pow = sens * sens * sens * 8.0f;
        return (double)pow * 0.15;
    }

    public static float[] getNormalAuraRotations(float yaw, float pitch, Entity target, double x, double y, double z, float yawSpeed, float pitchSpeed, boolean hitVec) {
        if (target != null) {
            pitchSpeed = (float)((double)pitchSpeed * 0.5);
            if (yawSpeed < 0.0f) {
                yawSpeed *= -1.0f;
            }
            if (pitchSpeed < 0.0f) {
                pitchSpeed *= -1.0f;
            }
            float sYaw = RotationUtils.updateRotation(yaw, RotationUtils.getInstantTargetRotation(target, x, y, z, hitVec)[0], yawSpeed);
            float sPitch = RotationUtils.updateRotation(pitch, RotationUtils.getInstantTargetRotation(target, x, y, z, hitVec)[1], pitchSpeed);
            yaw = RotationUtils.updateRotation(yaw, sYaw, 360.0f);
            if ((pitch = RotationUtils.updateRotation(pitch, sPitch, 360.0f)) > 90.0f) {
                pitch = 90.0f;
            } else if (pitch < -90.0f) {
                pitch = -90.0f;
            }
        }
        return new float[]{yaw, pitch};
    }

    public static float updateRotation(float current, float intended, float factor) {
        float var4 = MathHelper.wrapAngleTo180_float(intended - current);
        if (var4 > factor) {
            var4 = factor;
        }
        if (var4 < -factor) {
            var4 = -factor;
        }
        return current + var4;
    }

    public static float[] getInstantTargetRotation(Entity ent, double x, double y, double z, boolean hitVec) {
        double eyeHeight = ent.getEyeHeight();
        double playerY = RotationUtils.mc.thePlayer.posY + (double)RotationUtils.mc.thePlayer.getEyeHeight();
        if (playerY >= y + eyeHeight) {
            y += eyeHeight;
            y -= 0.4;
        } else if (!(playerY < y)) {
            y = playerY;
            y -= 0.4;
        }
        if (hitVec) {
            Vec3 best = RotationUtils.getBestHitVec(ent);
            double nearest = 15.0;
            AxisAlignedBB boundingBox = ent.getEntityBoundingBox();
            double x1 = boundingBox.minX;
            while (x1 <= boundingBox.maxX) {
                double z1 = boundingBox.minZ;
                while (z1 <= boundingBox.maxZ) {
                    double y1 = boundingBox.minY;
                    while (y1 <= boundingBox.maxY) {
                        Vec3 pos = new Vec3(x1, y1, z1);
                        if (RotationUtils.mc.thePlayer.canPosBeSeen(pos)) {
                            Vec3 eyes = RotationUtils.mc.thePlayer.getPositionEyes(1.0f);
                            double dist = Math.sqrt(Math.pow(x1 - eyes.xCoord, 2.0) + Math.pow(y1 - eyes.yCoord, 2.0) + Math.pow(z1 - eyes.zCoord, 2.0));
                            if (dist <= nearest) {
                                nearest = dist;
                                best = pos;
                            }
                        }
                        y1 += 0.07;
                    }
                    z1 += 0.07;
                }
                x1 += 0.07;
            }
            return RotationUtils.getRotationFromPosition(best.xCoord, y, best.zCoord);
        }
        return RotationUtils.getRotationFromPosition(x, y, z);
    }

    public static Vec3 getBestHitVec(Entity entity) {
        Vec3 positionEyes = RotationUtils.mc.thePlayer.getPositionEyes(1.0f);
        float f11 = entity.getCollisionBorderSize();
        AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox().expand(f11, f11, f11);
        double ex = MathHelper.clamp_double(positionEyes.xCoord, entityBoundingBox.minX, entityBoundingBox.maxX);
        double ey = MathHelper.clamp_double(positionEyes.yCoord, entityBoundingBox.minY, entityBoundingBox.maxY);
        double ez = MathHelper.clamp_double(positionEyes.zCoord, entityBoundingBox.minZ, entityBoundingBox.maxZ);
        return new Vec3(ex, ey - 0.4, ez);
    }

    public static float[] getRotationFromPosition(double x, double y, double z) {
        double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2;
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static float[] getRotationsFromPositionToPosition(double startX, double startY, double startZ, double posX, double posY, double posZ) {
        Vector3d to = new Vector3d(posX, posY, posZ);
        Vector3d from = new Vector3d(startX, startY, startZ);
        Vector3d diff = to.subtract(from);
        double distance = Math.hypot(diff.getX(), diff.getZ());
        float yaw = (float)Math.toDegrees(MathHelper.atan2(diff.getZ(), diff.getX())) - 90.0f;
        float pitch = (float)(-Math.toDegrees(MathHelper.atan2(diff.getY(), distance)));
        return new float[]{yaw, pitch};
    }

    public static Vec3 getVec3(BlockPos pos, EnumFacing face) {
        double x = (double)pos.getX() + 0.5;
        double y = (double)pos.getY() + 0.5;
        double z = (double)pos.getZ() + 0.5;
        x += (double)face.getFrontOffsetX() / 2.0;
        z += (double)face.getFrontOffsetZ() / 2.0;
        y += (double)face.getFrontOffsetY() / 2.0;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += new Random().nextDouble() / 2.0 - 0.25;
            z += new Random().nextDouble() / 2.0 - 0.25;
        } else {
            y += new Random().nextDouble() / 2.0 - 0.25;
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += new Random().nextDouble() / 2.0 - 0.25;
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += new Random().nextDouble() / 2.0 - 0.25;
        }
        return new Vec3(x, y, z);
    }
}
