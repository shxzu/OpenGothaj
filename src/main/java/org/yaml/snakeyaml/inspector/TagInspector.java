package org.yaml.snakeyaml.inspector;

import org.yaml.snakeyaml.nodes.Tag;

public interface TagInspector {
    public boolean isGlobalTagAllowed(Tag var1);
}
