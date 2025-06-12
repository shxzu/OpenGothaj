package xyz.cucumber.base.utils.game;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.commands.cmds.FriendsCommand;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.feat.other.FriendsModule;
import xyz.cucumber.base.module.feat.other.ReverseFriendsModule;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.InventoryUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

public class EntityUtils {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static int size = 0;
    public static Timer timer = new Timer();

    public static EntityLivingBase getTarget(double range, String targetMode, String attackMode, int switchTimer, boolean teams, boolean troughWalls, boolean dead, boolean invisible) {
        EntityLivingBase target;
        block58: {
            target = null;
            List targets = EntityUtils.mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
            targets = targets.stream().filter(entity -> (double)EntityUtils.mc.thePlayer.getDistanceToEntity((Entity)entity) < range && entity != EntityUtils.mc.thePlayer && !(entity instanceof EntityArmorStand)).collect(Collectors.toList());
            targets.removeIf(entity -> !invisible && entity.isInvisible());
            targets.removeIf(entity -> !dead && ((EntityLivingBase)entity).getHealth() <= 0.0f);
            KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
            targets.removeIf(entity -> (double)(Math.abs(RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0] - Minecraft.getMinecraft().thePlayer.rotationYaw) % 360.0f > 180.0f ? 360.0f - Math.abs(RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0] - Minecraft.getMinecraft().thePlayer.rotationYaw) % 360.0f : Math.abs(RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0] - Minecraft.getMinecraft().thePlayer.rotationYaw) % 360.0f) > killAuraModule.fov.getValue());
            if (Client.INSTANCE.getModuleManager().getModule(ReverseFriendsModule.class).isEnabled()) {
                targets.removeIf(entity -> !ReverseFriendsModule.allowed.contains(entity.getName()));
            } else {
                if (Client.INSTANCE.getModuleManager().getModule(FriendsModule.class).isEnabled()) {
                    for (String friend : FriendsCommand.friends) {
                        targets.removeIf(entity -> entity.getName().equalsIgnoreCase(friend));
                    }
                }
                targets.removeIf(entity -> teams && EntityUtils.mc.thePlayer.isOnSameTeam((EntityLivingBase)entity));
                try {
                    targets.removeIf(entity -> teams && entity instanceof EntityPlayer && EntityUtils.isInSameTeam((EntityPlayer)entity));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            targets.removeIf(entity -> !troughWalls && !EntityUtils.mc.thePlayer.canEntityBeSeen((Entity)entity));
            switch (ka.sort.getMode().toLowerCase()) {
                case "smart": {
                    int i;
                    if (targets.size() > 1) {
                        boolean isPlayer = false;
                        i = 0;
                        while (i < targets.size()) {
                            Entity ent = (Entity)targets.get(i);
                            if (ent != null) {
                                if (!(ent instanceof EntityPlayer)) {
                                    if (isPlayer) {
                                        targets.remove(ent);
                                    }
                                } else {
                                    isPlayer = true;
                                }
                            }
                            ++i;
                        }
                    }
                    targets.sort(Comparator.comparingDouble(entity -> EntityUtils.getSmartSort(entity)));
                    break;
                }
                case "strongest player": {
                    int i;
                    if (targets.size() > 1) {
                        boolean isPlayer = false;
                        i = 0;
                        while (i < targets.size()) {
                            Entity ent = (Entity)targets.get(i);
                            if (ent != null) {
                                if (!(ent instanceof EntityPlayer)) {
                                    if (isPlayer) {
                                        targets.remove(ent);
                                    }
                                } else {
                                    isPlayer = true;
                                }
                            }
                            ++i;
                        }
                    }
                    targets.sort(Comparator.comparingDouble(entity -> EntityUtils.getStrongestPlayerSort(entity)));
                    break;
                }
                case "distance": {
                    targets.sort(Comparator.comparingDouble(entity -> EntityUtils.mc.thePlayer.getDistanceToEntity((Entity)entity)));
                    break;
                }
                case "health": {
                    targets.sort(Comparator.comparingDouble(entity -> entity instanceof EntityPlayer ? ((EntityPlayer)entity).getHealth() : EntityUtils.mc.thePlayer.getDistanceToEntity((Entity)entity)));
                }
            }
            switch (targetMode.toLowerCase()) {
                case "players": {
                    targets = targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());
                }
            }
            if (targets.isEmpty()) break block58;
            switch (attackMode.toLowerCase()) {
                case "off": {
                    target = (EntityLivingBase)targets.get(0);
                    break;
                }
                case "timer": {
                    if (timer.hasTimeElapsed(switchTimer, true)) {
                        ++size;
                    }
                    if (targets.size() > 0 && size >= targets.size()) {
                        size = 0;
                    }
                    target = (EntityLivingBase)targets.get(size);
                    break;
                }
                case "hurt time": {
                    if (targets.size() <= 0) break;
                    targets.sort(Comparator.comparingDouble(entity -> entity.hurtResistantTime));
                    target = (EntityLivingBase)targets.get(0);
                }
            }
        }
        return target;
    }

    public static EntityLivingBase getTargetBox(double range, String targetMode, String attackMode, int switchTimer, boolean teams, boolean troughWalls, boolean dead, boolean invisible) {
        EntityLivingBase target;
        block58: {
            target = null;
            List targets = EntityUtils.mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
            targets = targets.stream().filter(entity -> EntityUtils.getDistanceToEntityBox(entity) < range && entity != EntityUtils.mc.thePlayer && !(entity instanceof EntityArmorStand)).collect(Collectors.toList());
            targets.removeIf(entity -> !invisible && entity.isInvisible());
            targets.removeIf(entity -> !dead && ((EntityLivingBase)entity).getHealth() <= 0.0f);
            KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
            targets.removeIf(entity -> (double)(Math.abs(RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0] - Minecraft.getMinecraft().thePlayer.rotationYaw) % 360.0f > 180.0f ? 360.0f - Math.abs(RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0] - Minecraft.getMinecraft().thePlayer.rotationYaw) % 360.0f : Math.abs(RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0] - Minecraft.getMinecraft().thePlayer.rotationYaw) % 360.0f) > killAuraModule.fov.getValue());
            if (Client.INSTANCE.getModuleManager().getModule(ReverseFriendsModule.class).isEnabled()) {
                targets.removeIf(entity -> !ReverseFriendsModule.allowed.contains(entity.getName()));
            } else {
                if (Client.INSTANCE.getModuleManager().getModule(FriendsModule.class).isEnabled()) {
                    for (String friend : FriendsCommand.friends) {
                        targets.removeIf(entity -> entity.getName().equalsIgnoreCase(friend));
                    }
                }
                targets.removeIf(entity -> teams && EntityUtils.mc.thePlayer.isOnSameTeam((EntityLivingBase)entity));
                try {
                    targets.removeIf(entity -> teams && entity instanceof EntityPlayer && EntityUtils.isInSameTeam((EntityPlayer)entity));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            targets.removeIf(entity -> !troughWalls && !EntityUtils.mc.thePlayer.canEntityBeSeen((Entity)entity));
            switch (ka.sort.getMode().toLowerCase()) {
                case "smart": {
                    int i;
                    if (targets.size() > 1) {
                        boolean isPlayer = false;
                        i = 0;
                        while (i < targets.size()) {
                            Entity ent = (Entity)targets.get(i);
                            if (ent != null) {
                                if (!(ent instanceof EntityPlayer)) {
                                    if (isPlayer) {
                                        targets.remove(ent);
                                    }
                                } else {
                                    isPlayer = true;
                                }
                            }
                            ++i;
                        }
                    }
                    targets.sort(Comparator.comparingDouble(entity -> EntityUtils.getSmartSort(entity)));
                    break;
                }
                case "strongest player": {
                    int i;
                    if (targets.size() > 1) {
                        boolean isPlayer = false;
                        i = 0;
                        while (i < targets.size()) {
                            Entity ent = (Entity)targets.get(i);
                            if (ent != null) {
                                if (!(ent instanceof EntityPlayer)) {
                                    if (isPlayer) {
                                        targets.remove(ent);
                                    }
                                } else {
                                    isPlayer = true;
                                }
                            }
                            ++i;
                        }
                    }
                    targets.sort(Comparator.comparingDouble(entity -> EntityUtils.getStrongestPlayerSort(entity)));
                    break;
                }
                case "distance": {
                    targets.sort(Comparator.comparingDouble(entity -> EntityUtils.mc.thePlayer.getDistanceToEntity((Entity)entity)));
                    break;
                }
                case "health": {
                    targets.sort(Comparator.comparingDouble(entity -> entity instanceof EntityPlayer ? ((EntityPlayer)entity).getHealth() : EntityUtils.mc.thePlayer.getDistanceToEntity((Entity)entity)));
                }
            }
            switch (targetMode.toLowerCase()) {
                case "players": {
                    targets = targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());
                }
            }
            if (targets.isEmpty()) break block58;
            switch (attackMode.toLowerCase()) {
                case "off": {
                    target = (EntityLivingBase)targets.get(0);
                    break;
                }
                case "timer": {
                    if (timer.hasTimeElapsed(switchTimer, true)) {
                        ++size;
                    }
                    if (targets.size() > 0 && size >= targets.size()) {
                        size = 0;
                    }
                    target = (EntityLivingBase)targets.get(size);
                    break;
                }
                case "hurt time": {
                    if (targets.size() <= 0) break;
                    targets.sort(Comparator.comparingDouble(entity -> entity.hurtResistantTime));
                    target = (EntityLivingBase)targets.get(0);
                }
            }
        }
        return target;
    }

    public static boolean isInSameTeam(EntityPlayer player) {
        try {
            String[] name = EntityUtils.mc.thePlayer.getDisplayName().getUnformattedText().split("");
            String[] parts = player.getDisplayName().getUnformattedText().split("");
            boolean b = Arrays.asList(name).contains("Â§") && Arrays.asList(parts).contains("Â§") && Arrays.asList(name).get(Arrays.asList(name).indexOf("Â§") + 1).equals(Arrays.asList(parts).get(Arrays.asList(parts).indexOf("Â§") + 1));
            return b;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static double getDistanceToEntityBox(Entity entity) {
        Vec3 eyes = EntityUtils.mc.thePlayer.getPositionEyes(1.0f);
        Vec3 pos = RotationUtils.getBestHitVec(entity);
        double xDist = Math.abs(pos.xCoord - eyes.xCoord);
        double yDist = Math.abs(pos.yCoord - eyes.yCoord);
        double zDist = Math.abs(pos.zCoord - eyes.zCoord);
        return Math.sqrt(Math.pow(xDist, 2.0) + Math.pow(yDist, 2.0) + Math.pow(zDist, 2.0));
    }

    public static double getDistanceToEntityBoxFromPosition(double posX, double posY, double posZ, Entity entity) {
        Vec3 eyes = EntityUtils.mc.thePlayer.getPositionEyes(1.0f);
        Vec3 pos = RotationUtils.getBestHitVec(entity);
        double xDist = Math.abs(pos.xCoord - posX);
        double yDist = Math.abs(pos.yCoord - posY + (double)EntityUtils.mc.thePlayer.getEyeHeight());
        double zDist = Math.abs(pos.zCoord - posZ);
        return Math.sqrt(Math.pow(xDist, 2.0) + Math.pow(yDist, 2.0) + Math.pow(zDist, 2.0));
    }

    public static double getSmartSort(Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            double playerDamage = 0.0;
            double targetDamage = 0.0;
            if (EntityUtils.mc.thePlayer.getHeldItem() != null) {
                playerDamage = Math.max(0.0f, InventoryUtils.getItemDamage(EntityUtils.mc.thePlayer.getHeldItem()));
            }
            if (player.getHeldItem() != null) {
                targetDamage = Math.max(0.0f, InventoryUtils.getItemDamage(player.getHeldItem()));
            }
            playerDamage = playerDamage * 20.0 / (double)(player.getTotalArmorValue() * 4);
            if (EntityUtils.mc.thePlayer.fallDistance > 0.0f) {
                playerDamage *= 1.5;
            }
            if (playerDamage >= (double)player.getHealth()) {
                return -1.0E8;
            }
            return targetDamage * -1.0;
        }
        return EntityUtils.mc.thePlayer.getDistanceToEntity(entity);
    }

    public static double getStrongestPlayerSort(Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            double targetDamage = 0.0;
            if (player.getHeldItem() != null) {
                targetDamage = Math.max(0.0f, InventoryUtils.getItemDamage(player.getHeldItem()));
            }
            return targetDamage * -1.0;
        }
        return EntityUtils.mc.thePlayer.getDistanceToEntity(entity);
    }
}
