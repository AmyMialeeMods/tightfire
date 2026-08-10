package dev.amymialee.tightfire;

import dev.amymialee.tightfire.client.TightFireModelPlugin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;

public class TightFireClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLoadingPlugin.register(new TightFireModelPlugin());
    }
}