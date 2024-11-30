package xyz.amymialee.tightfire.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import xyz.amymialee.tightfire.TightFire;

@Mixin(FireBlock.class)
public class FireBlockMixin {
    @WrapMethod(method = "getOutlineShape")
    protected VoxelShape tightfire$fireoffsetter(BlockState state, BlockView world, BlockPos pos, ShapeContext context, @NotNull Operation<VoxelShape> original) {
        var shape = original.call(state, world, pos, context);
        var direction = TightFire.getDirection(state);
        if (direction == null) return shape;
        var sidePos = pos.offset(direction);
        var outline = world.getBlockState(sidePos).getOutlineShape(world, sidePos);
        var offset = TightFire.getOffset(direction, outline);
        return switch (direction) {
            case UP -> VoxelShapes.union(shape, Block.createCuboidShape(0.0, 16.0, 0.0, 16.0, 16.0 + offset.y * 16.0, 16.0));
            case DOWN -> VoxelShapes.union(shape, Block.createCuboidShape(0.0, offset.y * 16.0, 0.0, 16.0, 0.0, 16.0));
            case WEST -> VoxelShapes.union(shape, Block.createCuboidShape(offset.x * 16.0, 0.0, 0.0, 0.0, 16.0, 16.0));
            case EAST -> VoxelShapes.union(shape, Block.createCuboidShape(16.0, 0.0, 0.0, 16.0 + offset.x * 16.0, 16.0, 16.0));
            case NORTH -> VoxelShapes.union(shape, Block.createCuboidShape(0.0, 0.0, offset.z * 16.0, 16.0, 16.0, 0.0));
            case SOUTH -> VoxelShapes.union(shape, Block.createCuboidShape(0.0, 0.0, 16.0, 16.0, 16.0, 16.0 + offset.z * 16.0));
        };
    }
}