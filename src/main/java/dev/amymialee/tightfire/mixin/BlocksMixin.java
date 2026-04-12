package dev.amymialee.tightfire.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import dev.amymialee.tightfire.TightFire;

@Mixin(Blocks.class)
public class BlocksMixin {
    @WrapOperation(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;of()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;", ordinal = 143))
    private static BlockBehaviour.@NotNull Properties tightfire$fireoffsetter(@NotNull Operation<BlockBehaviour.Properties> original) {
        return TightFire.setSettings(original.call());
    }
}