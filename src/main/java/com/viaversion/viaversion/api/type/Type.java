package com.viaversion.viaversion.api.type;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.EulerAngle;
import com.viaversion.viaversion.api.minecraft.GlobalPosition;
import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.minecraft.Quaternion;
import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.minecraft.Vector3f;
import com.viaversion.viaversion.api.minecraft.VillagerData;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.ChunkPosition;
import com.viaversion.viaversion.api.type.ByteBufReader;
import com.viaversion.viaversion.api.type.ByteBufWriter;
import com.viaversion.viaversion.api.type.types.ArrayType;
import com.viaversion.viaversion.api.type.types.BitSetType;
import com.viaversion.viaversion.api.type.types.BooleanType;
import com.viaversion.viaversion.api.type.types.ByteArrayType;
import com.viaversion.viaversion.api.type.types.ByteType;
import com.viaversion.viaversion.api.type.types.ComponentType;
import com.viaversion.viaversion.api.type.types.DoubleType;
import com.viaversion.viaversion.api.type.types.FloatType;
import com.viaversion.viaversion.api.type.types.IntType;
import com.viaversion.viaversion.api.type.types.LongArrayType;
import com.viaversion.viaversion.api.type.types.LongType;
import com.viaversion.viaversion.api.type.types.OptionalVarIntType;
import com.viaversion.viaversion.api.type.types.RemainingBytesType;
import com.viaversion.viaversion.api.type.types.ShortByteArrayType;
import com.viaversion.viaversion.api.type.types.ShortType;
import com.viaversion.viaversion.api.type.types.StringType;
import com.viaversion.viaversion.api.type.types.UUIDType;
import com.viaversion.viaversion.api.type.types.UnsignedByteType;
import com.viaversion.viaversion.api.type.types.UnsignedShortType;
import com.viaversion.viaversion.api.type.types.VarIntArrayType;
import com.viaversion.viaversion.api.type.types.VarIntType;
import com.viaversion.viaversion.api.type.types.VarLongType;
import com.viaversion.viaversion.api.type.types.block.BlockChangeRecordType;
import com.viaversion.viaversion.api.type.types.block.BlockEntityType1_18;
import com.viaversion.viaversion.api.type.types.block.BlockEntityType1_20_2;
import com.viaversion.viaversion.api.type.types.block.VarLongBlockChangeRecordType;
import com.viaversion.viaversion.api.type.types.item.ItemShortArrayType1_13;
import com.viaversion.viaversion.api.type.types.item.ItemShortArrayType1_13_2;
import com.viaversion.viaversion.api.type.types.item.ItemShortArrayType1_8;
import com.viaversion.viaversion.api.type.types.item.ItemType1_13;
import com.viaversion.viaversion.api.type.types.item.ItemType1_13_2;
import com.viaversion.viaversion.api.type.types.item.ItemType1_20_2;
import com.viaversion.viaversion.api.type.types.item.ItemType1_8;
import com.viaversion.viaversion.api.type.types.math.ChunkPositionType;
import com.viaversion.viaversion.api.type.types.math.EulerAngleType;
import com.viaversion.viaversion.api.type.types.math.GlobalPositionType;
import com.viaversion.viaversion.api.type.types.math.PositionType1_14;
import com.viaversion.viaversion.api.type.types.math.PositionType1_8;
import com.viaversion.viaversion.api.type.types.math.QuaternionType;
import com.viaversion.viaversion.api.type.types.math.Vector3fType;
import com.viaversion.viaversion.api.type.types.math.VectorType;
import com.viaversion.viaversion.api.type.types.misc.CompoundTagType;
import com.viaversion.viaversion.api.type.types.misc.NamedCompoundTagType;
import com.viaversion.viaversion.api.type.types.misc.PlayerMessageSignatureType;
import com.viaversion.viaversion.api.type.types.misc.ProfileKeyType;
import com.viaversion.viaversion.api.type.types.misc.TagType;
import com.viaversion.viaversion.api.type.types.misc.VillagerDataType;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.UUID;

public abstract class Type<T>
implements ByteBufReader<T>,
ByteBufWriter<T> {
    public static final ByteType BYTE = new ByteType();
    public static final UnsignedByteType UNSIGNED_BYTE = new UnsignedByteType();
    public static final Type<byte[]> BYTE_ARRAY_PRIMITIVE = new ByteArrayType();
    public static final Type<byte[]> OPTIONAL_BYTE_ARRAY_PRIMITIVE = new ByteArrayType.OptionalByteArrayType();
    public static final Type<byte[]> SHORT_BYTE_ARRAY = new ShortByteArrayType();
    public static final Type<byte[]> REMAINING_BYTES = new RemainingBytesType();
    public static final ShortType SHORT = new ShortType();
    public static final UnsignedShortType UNSIGNED_SHORT = new UnsignedShortType();
    public static final IntType INT = new IntType();
    public static final FloatType FLOAT = new FloatType();
    public static final FloatType.OptionalFloatType OPTIONAL_FLOAT = new FloatType.OptionalFloatType();
    public static final DoubleType DOUBLE = new DoubleType();
    public static final LongType LONG = new LongType();
    public static final Type<long[]> LONG_ARRAY_PRIMITIVE = new LongArrayType();
    public static final BooleanType BOOLEAN = new BooleanType();
    public static final Type<JsonElement> COMPONENT = new ComponentType();
    public static final Type<JsonElement> OPTIONAL_COMPONENT = new ComponentType.OptionalComponentType();
    public static final Type<String> STRING = new StringType();
    public static final Type<String> OPTIONAL_STRING = new StringType.OptionalStringType();
    public static final Type<String[]> STRING_ARRAY = new ArrayType<String>(STRING);
    public static final Type<UUID> UUID = new UUIDType();
    public static final Type<UUID> OPTIONAL_UUID = new UUIDType.OptionalUUIDType();
    public static final Type<UUID[]> UUID_ARRAY = new ArrayType<UUID>(UUID);
    public static final VarIntType VAR_INT = new VarIntType();
    public static final OptionalVarIntType OPTIONAL_VAR_INT = new OptionalVarIntType();
    public static final Type<int[]> VAR_INT_ARRAY_PRIMITIVE = new VarIntArrayType();
    public static final VarLongType VAR_LONG = new VarLongType();
    public static final Type<Position> POSITION1_8 = new PositionType1_8();
    public static final Type<Position> OPTIONAL_POSITION1_8 = new PositionType1_8.OptionalPositionType();
    public static final Type<Position> POSITION1_14 = new PositionType1_14();
    public static final Type<Position> OPTIONAL_POSITION_1_14 = new PositionType1_14.OptionalPosition1_14Type();
    public static final Type<EulerAngle> ROTATION = new EulerAngleType();
    public static final Type<Vector> VECTOR = new VectorType();
    public static final Type<Vector3f> VECTOR3F = new Vector3fType();
    public static final Type<Quaternion> QUATERNION = new QuaternionType();
    public static final Type<CompoundTag> NAMED_COMPOUND_TAG = new NamedCompoundTagType();
    public static final Type<CompoundTag[]> NAMED_COMPOUND_TAG_ARRAY = new ArrayType<CompoundTag>(NAMED_COMPOUND_TAG);
    public static final Type<CompoundTag> COMPOUND_TAG = new CompoundTagType();
    public static final Type<CompoundTag> OPTIONAL_COMPOUND_TAG = new CompoundTagType.OptionalCompoundTagType();
    public static final Type<Tag> TAG = new TagType();
    public static final Type<Tag> OPTIONAL_TAG = new TagType.OptionalTagType();
    @Deprecated
    public static final Type<CompoundTag> NBT = NAMED_COMPOUND_TAG;
    @Deprecated
    public static final Type<CompoundTag[]> NBT_ARRAY = NAMED_COMPOUND_TAG_ARRAY;
    public static final Type<GlobalPosition> GLOBAL_POSITION = new GlobalPositionType();
    public static final Type<GlobalPosition> OPTIONAL_GLOBAL_POSITION = new GlobalPositionType.OptionalGlobalPositionType();
    public static final Type<ChunkPosition> CHUNK_POSITION = new ChunkPositionType();
    public static final Type<BlockEntity> BLOCK_ENTITY1_18 = new BlockEntityType1_18();
    public static final Type<BlockEntity> BLOCK_ENTITY1_20_2 = new BlockEntityType1_20_2();
    public static final Type<BlockChangeRecord> BLOCK_CHANGE_RECORD = new BlockChangeRecordType();
    public static final Type<BlockChangeRecord[]> BLOCK_CHANGE_RECORD_ARRAY = new ArrayType<BlockChangeRecord>(BLOCK_CHANGE_RECORD);
    public static final Type<BlockChangeRecord> VAR_LONG_BLOCK_CHANGE_RECORD = new VarLongBlockChangeRecordType();
    public static final Type<BlockChangeRecord[]> VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY = new ArrayType<BlockChangeRecord>(VAR_LONG_BLOCK_CHANGE_RECORD);
    public static final Type<VillagerData> VILLAGER_DATA = new VillagerDataType();
    public static final Type<ProfileKey> PROFILE_KEY = new ProfileKeyType();
    public static final Type<ProfileKey> OPTIONAL_PROFILE_KEY = new ProfileKeyType.OptionalProfileKeyType();
    public static final Type<PlayerMessageSignature> PLAYER_MESSAGE_SIGNATURE = new PlayerMessageSignatureType();
    public static final Type<PlayerMessageSignature> OPTIONAL_PLAYER_MESSAGE_SIGNATURE = new PlayerMessageSignatureType.OptionalPlayerMessageSignatureType();
    public static final Type<PlayerMessageSignature[]> PLAYER_MESSAGE_SIGNATURE_ARRAY = new ArrayType<PlayerMessageSignature>(PLAYER_MESSAGE_SIGNATURE);
    public static final BitSetType PROFILE_ACTIONS_ENUM = new BitSetType(6);
    public static final ByteArrayType SIGNATURE_BYTES = new ByteArrayType(256);
    public static final ByteArrayType.OptionalByteArrayType OPTIONAL_SIGNATURE_BYTES = new ByteArrayType.OptionalByteArrayType(256);
    public static final Type<Item> ITEM1_8 = new ItemType1_8();
    public static final Type<Item> ITEM1_13 = new ItemType1_13();
    public static final Type<Item> ITEM1_13_2 = new ItemType1_13_2();
    public static final Type<Item> ITEM1_20_2 = new ItemType1_20_2();
    public static final Type<Item[]> ITEM1_8_SHORT_ARRAY = new ItemShortArrayType1_8();
    public static final Type<Item[]> ITEM1_13_SHORT_ARRAY = new ItemShortArrayType1_13();
    public static final Type<Item[]> ITEM1_13_2_SHORT_ARRAY = new ItemShortArrayType1_13_2();
    public static final Type<Item[]> ITEM1_13_ARRAY = new ArrayType<Item>(ITEM1_13);
    public static final Type<Item[]> ITEM1_13_2_ARRAY = new ArrayType<Item>(ITEM1_13_2);
    public static final Type<Item[]> ITEM1_20_2_ARRAY = new ArrayType<Item>(ITEM1_20_2);
    @Deprecated
    public static final Type<Item> ITEM = ITEM1_8;
    @Deprecated
    public static final Type<Item> FLAT_ITEM = ITEM1_13;
    @Deprecated
    public static final Type<Item> FLAT_VAR_INT_ITEM = ITEM1_13_2;
    @Deprecated
    public static final Type<Item[]> ITEM_ARRAY = ITEM1_8_SHORT_ARRAY;
    @Deprecated
    public static final Type<Item[]> FLAT_ITEM_ARRAY = ITEM1_13_SHORT_ARRAY;
    @Deprecated
    public static final Type<Item[]> FLAT_VAR_INT_ITEM_ARRAY = ITEM1_13_2_SHORT_ARRAY;
    @Deprecated
    public static final Type<Item[]> FLAT_ITEM_ARRAY_VAR_INT = ITEM1_13_ARRAY;
    @Deprecated
    public static final Type<Item[]> FLAT_VAR_INT_ITEM_ARRAY_VAR_INT = ITEM1_13_2_ARRAY;
    private final Class<? super T> outputClass;
    private final String typeName;

    protected Type(Class<? super T> outputClass) {
        this(null, outputClass);
    }

    protected Type(String typeName, Class<? super T> outputClass) {
        this.outputClass = outputClass;
        this.typeName = typeName;
    }

    public Class<? super T> getOutputClass() {
        return this.outputClass;
    }

    public String getTypeName() {
        return this.typeName != null ? this.typeName : this.getClass().getSimpleName();
    }

    public Class<? extends Type> getBaseClass() {
        return this.getClass();
    }

    public String toString() {
        return this.getTypeName();
    }
}
