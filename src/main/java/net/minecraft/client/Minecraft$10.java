package net.minecraft.client;

import java.util.concurrent.Callable;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;

class Minecraft$10
implements Callable<String> {
    Minecraft$10() {
    }

    @Override
    public String call() throws Exception {
        String s = ClientBrandRetriever.getClientModName();
        return !s.equals("vanilla") ? "Definitely; Client brand changed to '" + s + "'" : (Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.");
    }
}
