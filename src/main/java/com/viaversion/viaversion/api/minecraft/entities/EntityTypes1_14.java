package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.util.EntityTypeUtil;

public enum EntityTypes1_14 implements EntityType
{
    ENTITY(-1),
    AREA_EFFECT_CLOUD(0, ENTITY),
    END_CRYSTAL(17, ENTITY),
    EVOKER_FANGS(21, ENTITY),
    EXPERIENCE_ORB(23, ENTITY),
    EYE_OF_ENDER(24, ENTITY),
    FALLING_BLOCK(25, ENTITY),
    FIREWORK_ROCKET(26, ENTITY),
    ITEM(34, ENTITY),
    LLAMA_SPIT(39, ENTITY),
    TNT(58, ENTITY),
    SHULKER_BULLET(63, ENTITY),
    FISHING_BOBBER(101, ENTITY),
    LIVINGENTITY(-1, ENTITY),
    ARMOR_STAND(1, LIVINGENTITY),
    PLAYER(100, LIVINGENTITY),
    ABSTRACT_INSENTIENT(-1, LIVINGENTITY),
    ENDER_DRAGON(18, ABSTRACT_INSENTIENT),
    ABSTRACT_CREATURE(-1, ABSTRACT_INSENTIENT),
    ABSTRACT_AGEABLE(-1, ABSTRACT_CREATURE),
    VILLAGER(84, ABSTRACT_AGEABLE),
    WANDERING_TRADER(88, ABSTRACT_AGEABLE),
    ABSTRACT_ANIMAL(-1, ABSTRACT_AGEABLE),
    DOLPHIN(13, ABSTRACT_INSENTIENT),
    CHICKEN(8, ABSTRACT_ANIMAL),
    COW(10, ABSTRACT_ANIMAL),
    MOOSHROOM(49, COW),
    PANDA(52, ABSTRACT_INSENTIENT),
    PIG(54, ABSTRACT_ANIMAL),
    POLAR_BEAR(57, ABSTRACT_ANIMAL),
    RABBIT(59, ABSTRACT_ANIMAL),
    SHEEP(61, ABSTRACT_ANIMAL),
    TURTLE(77, ABSTRACT_ANIMAL),
    FOX(27, ABSTRACT_ANIMAL),
    ABSTRACT_TAMEABLE_ANIMAL(-1, ABSTRACT_ANIMAL),
    CAT(6, ABSTRACT_TAMEABLE_ANIMAL),
    OCELOT(50, ABSTRACT_TAMEABLE_ANIMAL),
    WOLF(93, ABSTRACT_TAMEABLE_ANIMAL),
    ABSTRACT_PARROT(-1, ABSTRACT_TAMEABLE_ANIMAL),
    PARROT(53, ABSTRACT_PARROT),
    ABSTRACT_HORSE(-1, ABSTRACT_ANIMAL),
    CHESTED_HORSE(-1, ABSTRACT_HORSE),
    DONKEY(12, CHESTED_HORSE),
    MULE(48, CHESTED_HORSE),
    LLAMA(38, CHESTED_HORSE),
    TRADER_LLAMA(75, CHESTED_HORSE),
    HORSE(31, ABSTRACT_HORSE),
    SKELETON_HORSE(66, ABSTRACT_HORSE),
    ZOMBIE_HORSE(95, ABSTRACT_HORSE),
    ABSTRACT_GOLEM(-1, ABSTRACT_CREATURE),
    SNOW_GOLEM(69, ABSTRACT_GOLEM),
    IRON_GOLEM(85, ABSTRACT_GOLEM),
    SHULKER(62, ABSTRACT_GOLEM),
    ABSTRACT_FISHES(-1, ABSTRACT_CREATURE),
    COD(9, ABSTRACT_FISHES),
    PUFFERFISH(55, ABSTRACT_FISHES),
    SALMON(60, ABSTRACT_FISHES),
    TROPICAL_FISH(76, ABSTRACT_FISHES),
    ABSTRACT_MONSTER(-1, ABSTRACT_CREATURE),
    BLAZE(4, ABSTRACT_MONSTER),
    CREEPER(11, ABSTRACT_MONSTER),
    ENDERMITE(20, ABSTRACT_MONSTER),
    ENDERMAN(19, ABSTRACT_MONSTER),
    GIANT(29, ABSTRACT_MONSTER),
    SILVERFISH(64, ABSTRACT_MONSTER),
    VEX(83, ABSTRACT_MONSTER),
    WITCH(89, ABSTRACT_MONSTER),
    WITHER(90, ABSTRACT_MONSTER),
    RAVAGER(98, ABSTRACT_MONSTER),
    ABSTRACT_ILLAGER_BASE(-1, ABSTRACT_MONSTER),
    ABSTRACT_EVO_ILLU_ILLAGER(-1, ABSTRACT_ILLAGER_BASE),
    EVOKER(22, ABSTRACT_EVO_ILLU_ILLAGER),
    ILLUSIONER(33, ABSTRACT_EVO_ILLU_ILLAGER),
    VINDICATOR(86, ABSTRACT_ILLAGER_BASE),
    PILLAGER(87, ABSTRACT_ILLAGER_BASE),
    ABSTRACT_SKELETON(-1, ABSTRACT_MONSTER),
    SKELETON(65, ABSTRACT_SKELETON),
    STRAY(74, ABSTRACT_SKELETON),
    WITHER_SKELETON(91, ABSTRACT_SKELETON),
    GUARDIAN(30, ABSTRACT_MONSTER),
    ELDER_GUARDIAN(16, GUARDIAN),
    SPIDER(72, ABSTRACT_MONSTER),
    CAVE_SPIDER(7, SPIDER),
    ZOMBIE(94, ABSTRACT_MONSTER),
    DROWNED(15, ZOMBIE),
    HUSK(32, ZOMBIE),
    ZOMBIE_PIGMAN(56, ZOMBIE),
    ZOMBIE_VILLAGER(96, ZOMBIE),
    ABSTRACT_FLYING(-1, ABSTRACT_INSENTIENT),
    GHAST(28, ABSTRACT_FLYING),
    PHANTOM(97, ABSTRACT_FLYING),
    ABSTRACT_AMBIENT(-1, ABSTRACT_INSENTIENT),
    BAT(3, ABSTRACT_AMBIENT),
    ABSTRACT_WATERMOB(-1, ABSTRACT_INSENTIENT),
    SQUID(73, ABSTRACT_WATERMOB),
    SLIME(67, ABSTRACT_INSENTIENT),
    MAGMA_CUBE(40, SLIME),
    ABSTRACT_HANGING(-1, ENTITY),
    LEASH_KNOT(37, ABSTRACT_HANGING),
    ITEM_FRAME(35, ABSTRACT_HANGING),
    PAINTING(51, ABSTRACT_HANGING),
    ABSTRACT_LIGHTNING(-1, ENTITY),
    LIGHTNING_BOLT(99, ABSTRACT_LIGHTNING),
    ABSTRACT_ARROW(-1, ENTITY),
    ARROW(2, ABSTRACT_ARROW),
    SPECTRAL_ARROW(71, ABSTRACT_ARROW),
    TRIDENT(82, ABSTRACT_ARROW),
    ABSTRACT_FIREBALL(-1, ENTITY),
    DRAGON_FIREBALL(14, ABSTRACT_FIREBALL),
    FIREBALL(36, ABSTRACT_FIREBALL),
    SMALL_FIREBALL(68, ABSTRACT_FIREBALL),
    WITHER_SKULL(92, ABSTRACT_FIREBALL),
    PROJECTILE_ABSTRACT(-1, ENTITY),
    SNOWBALL(70, PROJECTILE_ABSTRACT),
    ENDER_PEARL(79, PROJECTILE_ABSTRACT),
    EGG(78, PROJECTILE_ABSTRACT),
    POTION(81, PROJECTILE_ABSTRACT),
    EXPERIENCE_BOTTLE(80, PROJECTILE_ABSTRACT),
    MINECART_ABSTRACT(-1, ENTITY),
    CHESTED_MINECART_ABSTRACT(-1, MINECART_ABSTRACT),
    CHEST_MINECART(42, CHESTED_MINECART_ABSTRACT),
    HOPPER_MINECART(45, CHESTED_MINECART_ABSTRACT),
    MINECART(41, MINECART_ABSTRACT),
    FURNACE_MINECART(44, MINECART_ABSTRACT),
    COMMAND_BLOCK_MINECART(43, MINECART_ABSTRACT),
    TNT_MINECART(47, MINECART_ABSTRACT),
    SPAWNER_MINECART(46, MINECART_ABSTRACT),
    BOAT(5, ENTITY);

    private static final EntityType[] TYPES;
    private final int id;
    private final EntityType parent;

    private EntityTypes1_14(int id) {
        this.id = id;
        this.parent = null;
    }

    private EntityTypes1_14(int id, EntityType parent) {
        this.id = id;
        this.parent = parent;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public EntityType getParent() {
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

    public static EntityType getTypeFromId(int typeId) {
        return EntityTypeUtil.getTypeFromId(TYPES, typeId, ENTITY);
    }

    static {
        TYPES = EntityTypeUtil.toOrderedArray(EntityTypes1_14.values());
    }
}
