package net.funkpla.enchant_faker.handler;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;

public interface EnchantHandler {
    int getLevel(Enchantment enchantment, LivingEntity entity);
}
