package net.funkpla.enchant_faker.handler;

import net.funkpla.enchant_faker.EnchantFaker;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TaggedMobEffectHandler implements EnchantHandler {
    private final Map<Enchantment, Map<TagKey<MobEffect>, Integer>> enchantmentMap = new HashMap<>();

    public TaggedMobEffectHandler() {
        buildMap();
    }

    private void buildMap() {
        EnchantFaker.config.tagConfig.forEach(element -> {
            Optional<Enchantment> enchantment = BuiltInRegistries.ENCHANTMENT.getOptional(new ResourceLocation(element.getEnchantment()));
            if (enchantment.isPresent()) {
                TagKey<MobEffect> tagKey = TagKey.create(Registries.MOB_EFFECT, EnchantFaker.locate(element.getTag()));
                enchantmentMap.computeIfAbsent(enchantment.get(), k -> new HashMap<>());
                enchantmentMap.get(enchantment.get()).put(tagKey, element.getLevel());
            }
        });

    }

    @Override
    public int getLevel(Enchantment enchantment, LivingEntity entity) {
        var effects = entity.getActiveEffects();
        var tagMap = enchantmentMap.get(enchantment);
        if ((tagMap == null) || tagMap.isEmpty()) return 0;
        return effects.stream().map(effect -> tagMap.keySet().stream().map(tag -> {
            var holder = getHolder(effect.getEffect());
            if (holder.isPresent() && (holder.get().is(tag))) {
                return tagMap.get(tag);
            }
            return 0;
        }).max(Integer::compare).orElse(0)).max(Integer::compare).orElse(0);
    }

    public Optional<Holder.Reference<MobEffect>> getHolder(MobEffect effect) {
        var registryKey = BuiltInRegistries.MOB_EFFECT.getResourceKey(effect);
        return registryKey.flatMap(BuiltInRegistries.MOB_EFFECT::getHolder);
    }
}