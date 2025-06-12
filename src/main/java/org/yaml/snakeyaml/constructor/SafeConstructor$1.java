package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.NodeId;

class SafeConstructor$1 {
    static final int[] $SwitchMap$org$yaml$snakeyaml$nodes$NodeId;

    static {
        $SwitchMap$org$yaml$snakeyaml$nodes$NodeId = new int[NodeId.values().length];
        try {
            SafeConstructor$1.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[NodeId.mapping.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            SafeConstructor$1.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[NodeId.sequence.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
