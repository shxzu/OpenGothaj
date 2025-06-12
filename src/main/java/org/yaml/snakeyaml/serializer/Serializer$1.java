package org.yaml.snakeyaml.serializer;

import org.yaml.snakeyaml.nodes.NodeId;

class Serializer$1 {
    static final int[] $SwitchMap$org$yaml$snakeyaml$nodes$NodeId;

    static {
        $SwitchMap$org$yaml$snakeyaml$nodes$NodeId = new int[NodeId.values().length];
        try {
            Serializer$1.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[NodeId.sequence.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Serializer$1.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[NodeId.mapping.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Serializer$1.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[NodeId.scalar.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
