package dev.amymialee.tightfire.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import dev.amymialee.tightfire.TightFire;

@Mixin(FireBlock.class)
public class FireBlockMixin {
    @WrapMethod(method = "getShape")
    protected VoxelShape tightfire$fireoffsetter(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context, @NotNull Operation<VoxelShape> original) {
        var shape = original.call(state, world, pos, context);
        var direction = TightFire.getDirection(state);
        if (direction == null) return shape;
        var sidePos = pos.relative(direction);
        var outline = world.getBlockState(sidePos).getShape(world, sidePos);
        var offset = TightFire.getOffset(direction, outline);
        return switch (direction) {
            case UP -> Shapes.or(shape, Block.box(0.0, 16.0, 0.0, 16.0, 16.0 + offset.y * 16.0, 16.0));
            case DOWN -> Shapes.or(shape, Block.box(0.0, offset.y * 16.0, 0.0, 16.0, 0.0, 16.0));
            case WEST -> Shapes.or(shape, Block.box(offset.x * 16.0, 0.0, 0.0, 0.0, 16.0, 16.0));
            case EAST -> Shapes.or(shape, Block.box(16.0, 0.0, 0.0, 16.0 + offset.x * 16.0, 16.0, 16.0));
            case NORTH -> Shapes.or(shape, Block.box(0.0, 0.0, offset.z * 16.0, 16.0, 16.0, 0.0));
            case SOUTH -> Shapes.or(shape, Block.box(0.0, 0.0, 16.0, 16.0, 16.0, 16.0 + offset.z * 16.0));
        };
    }
}