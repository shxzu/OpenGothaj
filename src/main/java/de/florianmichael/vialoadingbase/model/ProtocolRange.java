package de.florianmichael.vialoadingbase.model;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.vialoadingbase.model.ComparableProtocolVersion;

public class ProtocolRange {
    private final ComparableProtocolVersion lowerBound;
    private final ComparableProtocolVersion upperBound;

    public ProtocolRange(ProtocolVersion lowerBound, ProtocolVersion upperBound) {
        this(ViaLoadingBase.fromProtocolVersion(lowerBound), ViaLoadingBase.fromProtocolVersion(upperBound));
    }

    public ProtocolRange(ComparableProtocolVersion lowerBound, ComparableProtocolVersion upperBound) {
        if (lowerBound == null && upperBound == null) {
            throw new RuntimeException("Invalid protocol range");
        }
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public static ProtocolRange andNewer(ProtocolVersion version) {
        return new ProtocolRange(null, version);
    }

    public static ProtocolRange singleton(ProtocolVersion version) {
        return new ProtocolRange(version, version);
    }

    public static ProtocolRange andOlder(ProtocolVersion version) {
        return new ProtocolRange(version, null);
    }

    public boolean contains(ComparableProtocolVersion protocolVersion) {
        if (this.lowerBound != null && protocolVersion.getIndex() < this.lowerBound.getIndex()) {
            return false;
        }
        return this.upperBound == null || protocolVersion.getIndex() <= this.upperBound.getIndex();
    }

    public String toString() {
        if (this.lowerBound == null) {
            return String.valueOf(this.upperBound.getName()) + "+";
        }
        if (this.upperBound == null) {
            return String.valueOf(this.lowerBound.getName()) + "-";
        }
        if (this.lowerBound == this.upperBound) {
            return this.lowerBound.getName();
        }
        return String.valueOf(this.lowerBound.getName()) + " - " + this.upperBound.getName();
    }
}
