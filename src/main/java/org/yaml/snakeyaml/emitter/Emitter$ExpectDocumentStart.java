package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.TreeSet;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.emitter.EmitterException;
import org.yaml.snakeyaml.emitter.EmitterState;
import org.yaml.snakeyaml.events.CommentEvent;
import org.yaml.snakeyaml.events.DocumentStartEvent;
import org.yaml.snakeyaml.events.StreamEndEvent;

class Emitter$ExpectDocumentStart
implements EmitterState {
    private final boolean first;

    public Emitter$ExpectDocumentStart(boolean first) {
        this.first = first;
    }

    @Override
    public void expect() throws IOException {
        if (Emitter.this.event instanceof DocumentStartEvent) {
            boolean implicit;
            DocumentStartEvent ev = (DocumentStartEvent)Emitter.this.event;
            if ((ev.getVersion() != null || ev.getTags() != null) && Emitter.this.openEnded) {
                Emitter.this.writeIndicator("...", true, false, false);
                Emitter.this.writeIndent();
            }
            if (ev.getVersion() != null) {
                String versionText = Emitter.this.prepareVersion(ev.getVersion());
                Emitter.this.writeVersionDirective(versionText);
            }
            Emitter.this.tagPrefixes = new LinkedHashMap(DEFAULT_TAG_PREFIXES);
            if (ev.getTags() != null) {
                TreeSet<String> handles = new TreeSet<String>(ev.getTags().keySet());
                for (String handle : handles) {
                    String prefix = ev.getTags().get(handle);
                    Emitter.this.tagPrefixes.put(prefix, handle);
                    String handleText = Emitter.this.prepareTagHandle(handle);
                    String prefixText = Emitter.this.prepareTagPrefix(prefix);
                    Emitter.this.writeTagDirective(handleText, prefixText);
                }
            }
            boolean bl = implicit = this.first && !ev.getExplicit() && Emitter.this.canonical == false && ev.getVersion() == null && (ev.getTags() == null || ev.getTags().isEmpty()) && !Emitter.this.checkEmptyDocument();
            if (!implicit) {
                Emitter.this.writeIndent();
                Emitter.this.writeIndicator("---", true, false, false);
                if (Emitter.this.canonical.booleanValue()) {
                    Emitter.this.writeIndent();
                }
            }
            Emitter.this.state = new Emitter.ExpectDocumentRoot(Emitter.this, null);
        } else if (Emitter.this.event instanceof StreamEndEvent) {
            Emitter.this.writeStreamEnd();
            Emitter.this.state = new Emitter.ExpectNothing(Emitter.this, null);
        } else if (Emitter.this.event instanceof CommentEvent) {
            Emitter.this.blockCommentsCollector.collectEvents(Emitter.this.event);
            Emitter.this.writeBlockComment();
        } else {
            throw new EmitterException("expected DocumentStartEvent, but got " + Emitter.this.event);
        }
    }
}
