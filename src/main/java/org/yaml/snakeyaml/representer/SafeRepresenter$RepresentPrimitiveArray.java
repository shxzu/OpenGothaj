package org.yaml.snakeyaml.representer;

import java.util.ArrayList;
import java.util.List;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;

class SafeRepresenter$RepresentPrimitiveArray
implements Represent {
    protected SafeRepresenter$RepresentPrimitiveArray() {
    }

    @Override
    public Node representData(Object data) {
        Class<?> type = data.getClass().getComponentType();
        if (Byte.TYPE == type) {
            return SafeRepresenter.this.representSequence(Tag.SEQ, this.asByteList(data), DumperOptions.FlowStyle.AUTO);
        }
        if (Short.TYPE == type) {
            return SafeRepresenter.this.representSequence(Tag.SEQ, this.asShortList(data), DumperOptions.FlowStyle.AUTO);
        }
        if (Integer.TYPE == type) {
            return SafeRepresenter.this.representSequence(Tag.SEQ, this.asIntList(data), DumperOptions.FlowStyle.AUTO);
        }
        if (Long.TYPE == type) {
            return SafeRepresenter.this.representSequence(Tag.SEQ, this.asLongList(data), DumperOptions.FlowStyle.AUTO);
        }
        if (Float.TYPE == type) {
            return SafeRepresenter.this.representSequence(Tag.SEQ, this.asFloatList(data), DumperOptions.FlowStyle.AUTO);
        }
        if (Double.TYPE == type) {
            return SafeRepresenter.this.representSequence(Tag.SEQ, this.asDoubleList(data), DumperOptions.FlowStyle.AUTO);
        }
        if (Character.TYPE == type) {
            return SafeRepresenter.this.representSequence(Tag.SEQ, this.asCharList(data), DumperOptions.FlowStyle.AUTO);
        }
        if (Boolean.TYPE == type) {
            return SafeRepresenter.this.representSequence(Tag.SEQ, this.asBooleanList(data), DumperOptions.FlowStyle.AUTO);
        }
        throw new YAMLException("Unexpected primitive '" + type.getCanonicalName() + "'");
    }

    private List<Byte> asByteList(Object in) {
        byte[] array = (byte[])in;
        ArrayList<Byte> list = new ArrayList<Byte>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return list;
    }

    private List<Short> asShortList(Object in) {
        short[] array = (short[])in;
        ArrayList<Short> list = new ArrayList<Short>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return list;
    }

    private List<Integer> asIntList(Object in) {
        int[] array = (int[])in;
        ArrayList<Integer> list = new ArrayList<Integer>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return list;
    }

    private List<Long> asLongList(Object in) {
        long[] array = (long[])in;
        ArrayList<Long> list = new ArrayList<Long>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return list;
    }

    private List<Float> asFloatList(Object in) {
        float[] array = (float[])in;
        ArrayList<Float> list = new ArrayList<Float>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(Float.valueOf(array[i]));
        }
        return list;
    }

    private List<Double> asDoubleList(Object in) {
        double[] array = (double[])in;
        ArrayList<Double> list = new ArrayList<Double>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return list;
    }

    private List<Character> asCharList(Object in) {
        char[] array = (char[])in;
        ArrayList<Character> list = new ArrayList<Character>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(Character.valueOf(array[i]));
        }
        return list;
    }

    private List<Boolean> asBooleanList(Object in) {
        boolean[] array = (boolean[])in;
        ArrayList<Boolean> list = new ArrayList<Boolean>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return list;
    }
}
