package dev.amymialee.tightfire;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TightFire {
    public static Vec3d getOffset(Direction direction, VoxelShape outline) {
        return switch (direction) {
            case null -> Vec3d.ZERO;
            case UP -> new Vec3d(0, Math.max(0, outline.getMin(Direction.Axis.Y)), 0);
            case DOWN -> new Vec3d(0, Math.min(0, -1 + outline.getMax(Direction.Axis.Y)), 0);
            case WEST -> new Vec3d(Math.min(0, -outline.getMin(Direction.Axis.X)), 0, 0);
            case EAST -> new Vec3d(Math.max(0, 1 - outline.getMax(Direction.Axis.X)), 0, 0);
            case NORTH -> new Vec3d(0, 0, Math.min(0, -outline.getMin(Direction.Axis.Z)));
            case SOUTH -> new Vec3d(0, 0, Math.max(0, 1 - outline.getMax(Direction.Axis.Z)));
        };
    }

    public static @Nullable Direction getDirection(@NotNull BlockState state) {
        var dir = Direction.DOWN;
        if (!state.contains(ConnectingBlock.UP)) return null;
        var north = state.get(ConnectingBlock.NORTH);
        if (north) dir = Direction.NORTH;
        var east = state.get(ConnectingBlock.EAST);
        if (east) {
            if (dir != Direction.DOWN) return null;
            dir = Direction.EAST;
        }
        var south = state.get(ConnectingBlock.SOUTH);
        if (south) {
            if (dir != Direction.DOWN) return null;
            dir = Direction.SOUTH;
        }
        var west = state.get(ConnectingBlock.WEST);
        if (west) {
            if (dir != Direction.DOWN) return null;
            dir = Direction.WEST;
        }
        var up = state.get(ConnectingBlock.UP);
        if (up) {
            if (dir != Direction.DOWN) return null;
            dir = Direction.UP;
        }
        return dir;
    }

    public static AbstractBlock.@NotNull Offsetter createOffsetter() {
        return (state, pos) -> {
            var direction = getDirection(state);
            if (direction == null) return Vec3d.ZERO;
            var world = MinecraftClient.getInstance().world;
            if (world == null) return Vec3d.ZERO;
            var sidePos = pos.offset(direction);
            var outline = world.getBlockState(sidePos).getOutlineShape(world, sidePos);
            return getOffset(direction, outline);
        };
    }

    public static AbstractBlock.@NotNull Settings setSettings(AbstractBlock.@NotNull Settings original) {
        original.offsetter = createOffsetter();
        return original;
    }
}