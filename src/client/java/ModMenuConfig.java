import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.funkpla.enchant_faker.config.EnchantFakerConfig;

@Environment(EnvType.CLIENT)
public class ModMenuConfig implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        AutoConfig.register(EnchantFakerConfig.class, JanksonConfigSerializer::new);
        return parent -> AutoConfig.getConfigScreen(EnchantFakerConfig.class, parent).get();
    }

}