package org.jsoup.nodes;

import org.jsoup.nodes.Entities;

class Entities$1 {
    static final int[] $SwitchMap$org$jsoup$nodes$Entities$CoreCharset;

    static {
        $SwitchMap$org$jsoup$nodes$Entities$CoreCharset = new int[Entities.CoreCharset.values().length];
        try {
            Entities$1.$SwitchMap$org$jsoup$nodes$Entities$CoreCharset[Entities.CoreCharset.ascii.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Entities$1.$SwitchMap$org$jsoup$nodes$Entities$CoreCharset[Entities.CoreCharset.utf.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
