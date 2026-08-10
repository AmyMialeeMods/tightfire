package dev.amymialee.tightfire;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TightFire {
    public static Vec3 getOffset(Direction direction, VoxelShape outline) {
        return switch (direction) {
            case null -> Vec3.ZERO;
            case UP -> new Vec3(0, Math.max(0, outline.min(Direction.Axis.Y)), 0);
            case DOWN -> new Vec3(0, Math.min(0, -1 + outline.max(Direction.Axis.Y)), 0);
            case WEST -> new Vec3(Math.min(0, -outline.min(Direction.Axis.X)), 0, 0);
            case EAST -> new Vec3(Math.max(0, 1 - outline.max(Direction.Axis.X)), 0, 0);
            case NORTH -> new Vec3(0, 0, Math.min(0, -outline.min(Direction.Axis.Z)));
            case SOUTH -> new Vec3(0, 0, Math.max(0, 1 - outline.max(Direction.Axis.Z)));
        };
    }

    public static @Nullable Direction getDirection(@NotNull BlockState state) {
        var dir = Direction.DOWN;
        if (!state.hasProperty(PipeBlock.UP)) return null;
        var north = state.getValue(PipeBlock.NORTH);
        if (north) dir = Direction.NORTH;
        var east = state.getValue(PipeBlock.EAST);
        if (east) {
            if (dir != Direction.DOWN) return null;
            dir = Direction.EAST;
        }
        var south = state.getValue(PipeBlock.SOUTH);
        if (south) {
            if (dir != Direction.DOWN) return null;
            dir = Direction.SOUTH;
        }
        var west = state.getValue(PipeBlock.WEST);
        if (west) {
            if (dir != Direction.DOWN) return null;
            dir = Direction.WEST;
        }
        var up = state.getValue(PipeBlock.UP);
        if (up) {
            if (dir != Direction.DOWN) return null;
            dir = Direction.UP;
        }
        return dir;
    }
}