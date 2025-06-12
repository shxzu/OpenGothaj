package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_12;
import com.viaversion.viaversion.api.minecraft.entities.ObjectType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum EntityTypes1_12$ObjectType implements ObjectType
{
    BOAT(1, EntityTypes1_12.EntityType.BOAT),
    ITEM(2, EntityTypes1_12.EntityType.DROPPED_ITEM),
    AREA_EFFECT_CLOUD(3, EntityTypes1_12.EntityType.AREA_EFFECT_CLOUD),
    MINECART(10, EntityTypes1_12.EntityType.MINECART_RIDEABLE),
    TNT_PRIMED(50, EntityTypes1_12.EntityType.PRIMED_TNT),
    ENDER_CRYSTAL(51, EntityTypes1_12.EntityType.ENDER_CRYSTAL),
    TIPPED_ARROW(60, EntityTypes1_12.EntityType.ARROW),
    SNOWBALL(61, EntityTypes1_12.EntityType.SNOWBALL),
    EGG(62, EntityTypes1_12.EntityType.EGG),
    FIREBALL(63, EntityTypes1_12.EntityType.FIREBALL),
    SMALL_FIREBALL(64, EntityTypes1_12.EntityType.SMALL_FIREBALL),
    ENDER_PEARL(65, EntityTypes1_12.EntityType.ENDER_PEARL),
    WITHER_SKULL(66, EntityTypes1_12.EntityType.WITHER_SKULL),
    SHULKER_BULLET(67, EntityTypes1_12.EntityType.SHULKER_BULLET),
    LIAMA_SPIT(68, EntityTypes1_12.EntityType.LIAMA_SPIT),
    FALLING_BLOCK(70, EntityTypes1_12.EntityType.FALLING_BLOCK),
    ITEM_FRAME(71, EntityTypes1_12.EntityType.ITEM_FRAME),
    ENDER_SIGNAL(72, EntityTypes1_12.EntityType.ENDER_SIGNAL),
    POTION(73, EntityTypes1_12.EntityType.SPLASH_POTION),
    THROWN_EXP_BOTTLE(75, EntityTypes1_12.EntityType.THROWN_EXP_BOTTLE),
    FIREWORK(76, EntityTypes1_12.EntityType.FIREWORK),
    LEASH(77, EntityTypes1_12.EntityType.LEASH_HITCH),
    ARMOR_STAND(78, EntityTypes1_12.EntityType.ARMOR_STAND),
    EVOCATION_FANGS(79, EntityTypes1_12.EntityType.EVOCATION_FANGS),
    FISHIHNG_HOOK(90, EntityTypes1_12.EntityType.FISHING_HOOK),
    SPECTRAL_ARROW(91, EntityTypes1_12.EntityType.SPECTRAL_ARROW),
    DRAGON_FIREBALL(93, EntityTypes1_12.EntityType.DRAGON_FIREBALL);

    private static final Map<Integer, EntityTypes1_12$ObjectType> TYPES;
    private final int id;
    private final EntityTypes1_12.EntityType type;

    private EntityTypes1_12$ObjectType(int id, EntityTypes1_12.EntityType type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public EntityTypes1_12.EntityType getType() {
        return this.type;
    }

    public static Optional<EntityTypes1_12$ObjectType> findById(int id) {
        if (id == -1) {
            return Optional.empty();
        }
        return Optional.ofNullable(TYPES.get(id));
    }

    public static Optional<EntityTypes1_12.EntityType> getPCEntity(int id) {
        Optional<EntityTypes1_12$ObjectType> output = EntityTypes1_12$ObjectType.findById(id);
        if (!output.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(output.get().type);
    }

    static {
        TYPES = new HashMap<Integer, EntityTypes1_12$ObjectType>();
        for (EntityTypes1_12$ObjectType type : EntityTypes1_12$ObjectType.values()) {
            TYPES.put(type.id, type);
        }
    }
}
