package aneagleinyourmind.foodexpansion;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {
    /* TODO config options:

     1) toggle individual recipes (forbidden fruit recipe off by default)
     2) toggle mob drops
     */

    public static String greeting = "Hello World";

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        greeting = configuration.getString("greeting", Configuration.CATEGORY_GENERAL, greeting, "How shall I greet?");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
