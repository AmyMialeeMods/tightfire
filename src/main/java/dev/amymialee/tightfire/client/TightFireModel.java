package dev.amymialee.tightfire.client;

import dev.amymialee.tightfire.TightFire;
import net.fabricmc.fabric.api.client.model.loading.v1.wrapper.WrapperBlockStateModel;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.minecraft.core.Direction;

public class TightFireModel extends WrapperBlockStateModel {

    public TightFireModel(BlockStateModel wrapped) {
        super(wrapped);
    }

    @Override
    public void emitQuads(
            QuadEmitter emitter,
            BlockAndTintGetter level,
            BlockPos pos,
            BlockState state,
            RandomSource random,
            Predicate<@Nullable Direction> cullTest
    ) {
        var direction = TightFire.getDirection(state);

        if (direction == null) {
            super.emitQuads(
                    emitter,
                    level,
                    pos,
                    state,
                    random,
                    cullTest
            );
            return;
        }

        var sidePos = pos.relative(direction);
        var outline = level.getBlockState(sidePos).getShape(level, sidePos);
        var offset = TightFire.getOffset(direction, outline);

        emitter.pushTransform(quad -> {
            for (int i = 0; i < 4; i++) {
                quad.pos(
                        i,
                        quad.x(i) + (float) offset.x,
                        quad.y(i) + (float) offset.y,
                        quad.z(i) + (float) offset.z
                );
            }

            return true;
        });

        try {
            super.emitQuads(
                    emitter,
                    level,
                    pos,
                    state,
                    random,
                    cullTest
            );
        } finally {
            emitter.popTransform();
        }
    }
}