package aneagleinyourmind.foodexpansion;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {
    public static boolean enable_forbidden_fruit = false;
    public static boolean enable_horse_drops, enable_bat_drops, enable_squid_drops, enable_sheep_drops = true;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        //greeting = configuration.getString("greeting", Configuration.CATEGORY_GENERAL, greeting, "How shall I greet?");
        enable_forbidden_fruit = configuration.getBoolean("enableForbiddenFruit", "recipes", false, "Enable the forbidden fruit crafting recipe");

        enable_horse_drops = configuration.getBoolean("enableHorseDrops", "drops", true, "Whether horses will drop raw horse meat when killed");
        enable_bat_drops = configuration.getBoolean("enableBatDrops", "drops", true, "Whether bats will drop bat wings when killed");
        enable_squid_drops = configuration.getBoolean("enableSquidDrops", "drops", true, "Whether squid will drop raw squid when killed");
        enable_sheep_drops = configuration.getBoolean("enableSheepDrops", "drops", true, "Whether sheep will drop raw mutton when killed");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
