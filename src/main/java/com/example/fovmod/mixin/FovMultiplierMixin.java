package com.example.fovmod.mixin;

import com.example.fovmod.FovModConfig;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class FovMultiplierMixin {

    @Inject(method = "getFovMultiplier", at = @At("RETURN"), cancellable = true)
    private void adjustFovMultiplier(CallbackInfoReturnable<Float> cir) {
        AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) (Object) this;
        float originalFov = cir.getReturnValue();
        float delta = originalFov - 1.0f;

        FovModConfig config = FovModConfig.INSTANCE;

        // Sprint FOV adjustment
        if (player.isSprinting()) {
            delta *= config.sprintFov;
        }

        // Flying FOV adjustment
        if (player.getAbilities().flying) {
            delta *= config.flyingFov;
        }

        // Swimming FOV adjustment
        if (player.isSwimming()) {
            delta *= config.swimmingFov;
        }

        // Status Effect adjustments
        if (player.hasStatusEffect(StatusEffects.SPEED) || player.hasStatusEffect(StatusEffects.SLOWNESS)) {
            delta *= config.potionFov;
        }

        if (player.hasStatusEffect(StatusEffects.DOLPHINS_GRACE)) {
            delta *= config.dolphinGraceFov;
        }

        if (player.hasStatusEffect(StatusEffects.WITHER)) {
            delta *= config.witherFov;
        }

        if (player.hasStatusEffect(StatusEffects.POISON)) {
            delta *= config.poisonFov;
        }

        if (player.hasStatusEffect(StatusEffects.NAUSEA)) {
            delta *= config.nauseaFov;
        }

        cir.setReturnValue(1.0f + delta);
    }
}
