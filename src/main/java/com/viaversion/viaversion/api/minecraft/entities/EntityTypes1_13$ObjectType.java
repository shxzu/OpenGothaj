package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.minecraft.entities.ObjectType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum EntityTypes1_13$ObjectType implements ObjectType
{
    BOAT(1, EntityTypes1_13.EntityType.BOAT),
    ITEM(2, EntityTypes1_13.EntityType.ITEM),
    AREA_EFFECT_CLOUD(3, EntityTypes1_13.EntityType.AREA_EFFECT_CLOUD),
    MINECART(10, EntityTypes1_13.EntityType.MINECART),
    TNT_PRIMED(50, EntityTypes1_13.EntityType.TNT),
    ENDER_CRYSTAL(51, EntityTypes1_13.EntityType.END_CRYSTAL),
    TIPPED_ARROW(60, EntityTypes1_13.EntityType.ARROW),
    SNOWBALL(61, EntityTypes1_13.EntityType.SNOWBALL),
    EGG(62, EntityTypes1_13.EntityType.EGG),
    FIREBALL(63, EntityTypes1_13.EntityType.FIREBALL),
    SMALL_FIREBALL(64, EntityTypes1_13.EntityType.SMALL_FIREBALL),
    ENDER_PEARL(65, EntityTypes1_13.EntityType.ENDER_PEARL),
    WITHER_SKULL(66, EntityTypes1_13.EntityType.WITHER_SKULL),
    SHULKER_BULLET(67, EntityTypes1_13.EntityType.SHULKER_BULLET),
    LLAMA_SPIT(68, EntityTypes1_13.EntityType.LLAMA_SPIT),
    FALLING_BLOCK(70, EntityTypes1_13.EntityType.FALLING_BLOCK),
    ITEM_FRAME(71, EntityTypes1_13.EntityType.ITEM_FRAME),
    EYE_OF_ENDER(72, EntityTypes1_13.EntityType.EYE_OF_ENDER),
    POTION(73, EntityTypes1_13.EntityType.POTION),
    EXPERIENCE_BOTTLE(75, EntityTypes1_13.EntityType.EXPERIENCE_BOTTLE),
    FIREWORK_ROCKET(76, EntityTypes1_13.EntityType.FIREWORK_ROCKET),
    LEASH(77, EntityTypes1_13.EntityType.LEASH_KNOT),
    ARMOR_STAND(78, EntityTypes1_13.EntityType.ARMOR_STAND),
    EVOKER_FANGS(79, EntityTypes1_13.EntityType.EVOKER_FANGS),
    FISHIHNG_HOOK(90, EntityTypes1_13.EntityType.FISHING_BOBBER),
    SPECTRAL_ARROW(91, EntityTypes1_13.EntityType.SPECTRAL_ARROW),
    DRAGON_FIREBALL(93, EntityTypes1_13.EntityType.DRAGON_FIREBALL),
    TRIDENT(94, EntityTypes1_13.EntityType.TRIDENT);

    private static final Map<Integer, EntityTypes1_13$ObjectType> TYPES;
    private final int id;
    private final EntityTypes1_13.EntityType type;

    private EntityTypes1_13$ObjectType(int id, EntityTypes1_13.EntityType type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public EntityTypes1_13.EntityType getType() {
        return this.type;
    }

    public static Optional<EntityTypes1_13$ObjectType> findById(int id) {
        if (id == -1) {
            return Optional.empty();
        }
        return Optional.ofNullable(TYPES.get(id));
    }

    public static Optional<EntityTypes1_13.EntityType> getPCEntity(int id) {
        Optional<EntityTypes1_13$ObjectType> output = EntityTypes1_13$ObjectType.findById(id);
        if (!output.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(output.get().type);
    }

    public static Optional<EntityTypes1_13$ObjectType> fromEntityType(EntityTypes1_13.EntityType type) {
        for (EntityTypes1_13$ObjectType ent : EntityTypes1_13$ObjectType.values()) {
            if (ent.type != type) continue;
            return Optional.of(ent);
        }
        return Optional.empty();
    }

    static {
        TYPES = new HashMap<Integer, EntityTypes1_13$ObjectType>();
        for (EntityTypes1_13$ObjectType type : EntityTypes1_13$ObjectType.values()) {
            TYPES.put(type.id, type);
        }
    }
}
