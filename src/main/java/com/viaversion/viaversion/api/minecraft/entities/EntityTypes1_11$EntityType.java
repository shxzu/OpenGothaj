package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum EntityTypes1_11$EntityType implements EntityType
{
    ENTITY(-1),
    DROPPED_ITEM(1, ENTITY),
    EXPERIENCE_ORB(2, ENTITY),
    LEASH_HITCH(8, ENTITY),
    PAINTING(9, ENTITY),
    ARROW(10, ENTITY),
    SNOWBALL(11, ENTITY),
    FIREBALL(12, ENTITY),
    SMALL_FIREBALL(13, ENTITY),
    ENDER_PEARL(14, ENTITY),
    ENDER_SIGNAL(15, ENTITY),
    THROWN_EXP_BOTTLE(17, ENTITY),
    ITEM_FRAME(18, ENTITY),
    WITHER_SKULL(19, ENTITY),
    PRIMED_TNT(20, ENTITY),
    FALLING_BLOCK(21, ENTITY),
    FIREWORK(22, ENTITY),
    SPECTRAL_ARROW(24, ARROW),
    SHULKER_BULLET(25, ENTITY),
    DRAGON_FIREBALL(26, FIREBALL),
    EVOCATION_FANGS(33, ENTITY),
    ENTITY_LIVING(-1, ENTITY),
    ENTITY_INSENTIENT(-1, ENTITY_LIVING),
    ENTITY_AGEABLE(-1, ENTITY_INSENTIENT),
    ENTITY_TAMEABLE_ANIMAL(-1, ENTITY_AGEABLE),
    ENTITY_HUMAN(-1, ENTITY_LIVING),
    ARMOR_STAND(30, ENTITY_LIVING),
    EVOCATION_ILLAGER(34, ENTITY_INSENTIENT),
    VEX(35, ENTITY_INSENTIENT),
    VINDICATION_ILLAGER(36, ENTITY_INSENTIENT),
    MINECART_ABSTRACT(-1, ENTITY),
    MINECART_COMMAND(40, MINECART_ABSTRACT),
    BOAT(41, ENTITY),
    MINECART_RIDEABLE(42, MINECART_ABSTRACT),
    MINECART_CHEST(43, MINECART_ABSTRACT),
    MINECART_FURNACE(44, MINECART_ABSTRACT),
    MINECART_TNT(45, MINECART_ABSTRACT),
    MINECART_HOPPER(46, MINECART_ABSTRACT),
    MINECART_MOB_SPAWNER(47, MINECART_ABSTRACT),
    CREEPER(50, ENTITY_INSENTIENT),
    ABSTRACT_SKELETON(-1, ENTITY_INSENTIENT),
    SKELETON(51, ABSTRACT_SKELETON),
    WITHER_SKELETON(5, ABSTRACT_SKELETON),
    STRAY(6, ABSTRACT_SKELETON),
    SPIDER(52, ENTITY_INSENTIENT),
    GIANT(53, ENTITY_INSENTIENT),
    ZOMBIE(54, ENTITY_INSENTIENT),
    HUSK(23, ZOMBIE),
    ZOMBIE_VILLAGER(27, ZOMBIE),
    SLIME(55, ENTITY_INSENTIENT),
    GHAST(56, ENTITY_INSENTIENT),
    PIG_ZOMBIE(57, ZOMBIE),
    ENDERMAN(58, ENTITY_INSENTIENT),
    CAVE_SPIDER(59, SPIDER),
    SILVERFISH(60, ENTITY_INSENTIENT),
    BLAZE(61, ENTITY_INSENTIENT),
    MAGMA_CUBE(62, SLIME),
    ENDER_DRAGON(63, ENTITY_INSENTIENT),
    WITHER(64, ENTITY_INSENTIENT),
    BAT(65, ENTITY_INSENTIENT),
    WITCH(66, ENTITY_INSENTIENT),
    ENDERMITE(67, ENTITY_INSENTIENT),
    GUARDIAN(68, ENTITY_INSENTIENT),
    ELDER_GUARDIAN(4, GUARDIAN),
    IRON_GOLEM(99, ENTITY_INSENTIENT),
    SHULKER(69, IRON_GOLEM),
    PIG(90, ENTITY_AGEABLE),
    SHEEP(91, ENTITY_AGEABLE),
    COW(92, ENTITY_AGEABLE),
    CHICKEN(93, ENTITY_AGEABLE),
    SQUID(94, ENTITY_INSENTIENT),
    WOLF(95, ENTITY_TAMEABLE_ANIMAL),
    MUSHROOM_COW(96, COW),
    SNOWMAN(97, IRON_GOLEM),
    OCELOT(98, ENTITY_TAMEABLE_ANIMAL),
    ABSTRACT_HORSE(-1, ENTITY_AGEABLE),
    HORSE(100, ABSTRACT_HORSE),
    SKELETON_HORSE(28, ABSTRACT_HORSE),
    ZOMBIE_HORSE(29, ABSTRACT_HORSE),
    CHESTED_HORSE(-1, ABSTRACT_HORSE),
    DONKEY(31, CHESTED_HORSE),
    MULE(32, CHESTED_HORSE),
    LIAMA(103, CHESTED_HORSE),
    RABBIT(101, ENTITY_AGEABLE),
    POLAR_BEAR(102, ENTITY_AGEABLE),
    VILLAGER(120, ENTITY_AGEABLE),
    ENDER_CRYSTAL(200, ENTITY),
    SPLASH_POTION(-1, ENTITY),
    LINGERING_POTION(-1, SPLASH_POTION),
    AREA_EFFECT_CLOUD(-1, ENTITY),
    EGG(-1, ENTITY),
    FISHING_HOOK(-1, ENTITY),
    LIGHTNING(-1, ENTITY),
    WEATHER(-1, ENTITY),
    PLAYER(-1, ENTITY_HUMAN),
    COMPLEX_PART(-1, ENTITY),
    LIAMA_SPIT(-1, ENTITY);

    private static final Map<Integer, EntityTypes1_11$EntityType> TYPES;
    private final int id;
    private final EntityTypes1_11$EntityType parent;

    private EntityTypes1_11$EntityType(int id) {
        this.id = id;
        this.parent = null;
    }

    private EntityTypes1_11$EntityType(int id, EntityTypes1_11$EntityType parent) {
        this.id = id;
        this.parent = parent;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public EntityTypes1_11$EntityType getParent() {
        return this.parent;
    }

    @Override
    public String identifier() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAbstractType() {
        return this.id != -1;
    }

    public static Optional<EntityTypes1_11$EntityType> findById(int id) {
        if (id == -1) {
            return Optional.empty();
        }
        return Optional.ofNullable(TYPES.get(id));
    }

    static {
        TYPES = new HashMap<Integer, EntityTypes1_11$EntityType>();
        for (EntityTypes1_11$EntityType type : EntityTypes1_11$EntityType.values()) {
            TYPES.put(type.id, type);
        }
    }
}
