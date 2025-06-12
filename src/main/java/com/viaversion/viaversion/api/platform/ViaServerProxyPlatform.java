package com.viaversion.viaversion.api.platform;

import com.viaversion.viaversion.api.platform.ProtocolDetectorService;
import com.viaversion.viaversion.api.platform.ViaPlatform;

public interface ViaServerProxyPlatform<T>
extends ViaPlatform<T> {
    public ProtocolDetectorService protocolDetectorService();
}
