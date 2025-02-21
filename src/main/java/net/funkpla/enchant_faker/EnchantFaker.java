package net.funkpla.enchant_faker;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.funkpla.enchant_faker.config.EnchantFakerConfig;
import net.funkpla.enchant_faker.handler.TaggedMobEffectHandler;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnchantFaker implements ModInitializer {

    public static final String MOD_ID = "enchant_faker";
    public static final EnchantFakerConfig config = AutoConfig.getConfigHolder(EnchantFakerConfig.class).getConfig();
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static ResourceLocation locate(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        AutoConfig.register(EnchantFakerConfig.class, JanksonConfigSerializer::new);
        EnchantFakerManager manager = EnchantFakerManager.getInstance();
        LOGGER.info("Loading handlers");
        manager.addHandler(new TaggedMobEffectHandler());
    }
}