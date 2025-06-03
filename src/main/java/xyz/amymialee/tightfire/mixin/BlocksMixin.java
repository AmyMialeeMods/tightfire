package xyz.amymialee.tightfire.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.amymialee.tightfire.TightFire;

@Mixin(Blocks.class)
public class BlocksMixin {
    @WrapOperation(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/AbstractBlock$Settings;create()Lnet/minecraft/block/AbstractBlock$Settings;", ordinal = 130))
    private static AbstractBlock.@NotNull Settings tightfire$fireoffsetter(@NotNull Operation<AbstractBlock.Settings> original) {
        return TightFire.setSettings(original.call());
    }
}