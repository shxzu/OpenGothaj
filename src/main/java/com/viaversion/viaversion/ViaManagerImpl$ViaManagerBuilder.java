package com.viaversion.viaversion;

import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.commands.ViaCommandHandler;

public final class ViaManagerImpl$ViaManagerBuilder {
    private ViaPlatform<?> platform;
    private ViaInjector injector;
    private ViaCommandHandler commandHandler;
    private ViaPlatformLoader loader;

    public ViaManagerImpl$ViaManagerBuilder platform(ViaPlatform<?> platform) {
        this.platform = platform;
        return this;
    }

    public ViaManagerImpl$ViaManagerBuilder injector(ViaInjector injector) {
        this.injector = injector;
        return this;
    }

    public ViaManagerImpl$ViaManagerBuilder loader(ViaPlatformLoader loader) {
        this.loader = loader;
        return this;
    }

    public ViaManagerImpl$ViaManagerBuilder commandHandler(ViaCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        return this;
    }

    public ViaManagerImpl build() {
        return new ViaManagerImpl(this.platform, this.injector, this.commandHandler, this.loader);
    }
}
