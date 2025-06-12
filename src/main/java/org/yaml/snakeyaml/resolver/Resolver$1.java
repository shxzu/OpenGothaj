package org.yaml.snakeyaml.resolver;

import org.yaml.snakeyaml.nodes.NodeId;

class Resolver$1 {
    static final int[] $SwitchMap$org$yaml$snakeyaml$nodes$NodeId;

    static {
        $SwitchMap$org$yaml$snakeyaml$nodes$NodeId = new int[NodeId.values().length];
        try {
            Resolver$1.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[NodeId.scalar.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Resolver$1.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[NodeId.sequence.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
