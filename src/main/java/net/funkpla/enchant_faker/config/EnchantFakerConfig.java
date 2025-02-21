package net.funkpla.enchant_faker.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.funkpla.enchant_faker.EnchantFaker;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

@Config(name = "enchant_faker")
public class EnchantFakerConfig implements ConfigData {
    @ConfigEntry.Gui.PrefixText
    @Comment("Tag To Enchantment Mappings")
    @ConfigEntry.Category("tag_map")
    @ConfigEntry.Gui.RequiresRestart
    public final List<TagToEnchantmentConfig> tagConfig = new ArrayList<>();

    private boolean enchantmentExists(String enchantment) {
        boolean exists = BuiltInRegistries.ENCHANTMENT.containsKey(new ResourceLocation(enchantment));
        if (!exists) {
            EnchantFaker.LOGGER.warn("Enchantment {} not found in registry, removing", enchantment);
        }
        return exists;
    }

    private void validateTagMap(List<TagToEnchantmentConfig> tagMap) {
        List<TagToEnchantmentConfig> missing = new ArrayList<>();
        tagMap.forEach(entry -> {
            if (!enchantmentExists(entry.getEnchantment())) {
                missing.add(entry);
            }
            tagMap.removeAll(missing);
        });
    }

    @Override
    public void validatePostLoad() {
        validateTagMap(tagConfig);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TagToEnchantmentConfig {
        private String tag;
        private String enchantment;
        @ConfigEntry.BoundedDiscrete(min=1,max=255)
        private int level;
    }

}