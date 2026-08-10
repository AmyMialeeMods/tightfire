package dev.amymialee.tightfire.client;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.world.level.block.Blocks;

public class TightFireModelPlugin implements ModelLoadingPlugin {
    @Override
    public void initialize(Context context) {
        context.modifyBlockModelAfterBake().register(
                (model, bakeContext) -> {
                    if (bakeContext.state().is(Blocks.FIRE)) {
                        return new TightFireModel(model);
                    }

                    return model;
                }
        );
    }
}