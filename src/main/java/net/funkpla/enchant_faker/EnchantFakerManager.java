package net.funkpla.enchant_faker;

import lombok.Getter;
import net.funkpla.enchant_faker.handler.EnchantHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnchantFakerManager {
    @Getter
    private static final EnchantFakerManager instance = new EnchantFakerManager();
    private final List<EnchantHandler> handlers;

    private EnchantFakerManager() {
        handlers = new ArrayList<>();
    }

    public void addHandler(EnchantHandler handler) {
        handlers.add(handler);
    }

    public int getLevel(Enchantment enchantment, LivingEntity entity) {
        Optional<Integer> level =
                handlers.stream().map(handler -> handler.getLevel(enchantment, entity)).max(Integer::compare);
        return level.orElse(0);
    }

}
