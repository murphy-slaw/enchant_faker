package net.funkpla.enchant_faker.mixin;

import net.funkpla.enchant_faker.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
abstract class EnchantmentHelperMixin {
    @Inject(
            method = "getEnchantmentLevel(Lnet/minecraft/world/item/enchantment/Enchantment;Lnet/minecraft/world/entity/LivingEntity;)I",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void enchant_faker$getEnchantmentLevel(Enchantment enchantment, LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        int prevLevel = cir.getReturnValue();
        int level = EnchantFakerManager.getInstance().getLevel(enchantment, entity);
        if (level > prevLevel) {
            cir.setReturnValue(level);
        }
    }
}