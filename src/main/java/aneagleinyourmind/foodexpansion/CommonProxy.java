package aneagleinyourmind.foodexpansion;

import aneagleinyourmind.item.ItemMetaFood;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public static ItemMetaFood ITEM_META_FOOD;

    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        GameRegistry.registerItem(ITEM_META_FOOD = new ItemMetaFood("fe_food", false), "fe_food");

        // green jelly
        GameRegistry.addShapelessRecipe(new ItemStack(ITEM_META_FOOD), Items.sugar, Items.slime_ball, Items.slime_ball, Items.slime_ball, Items.slime_ball); // TODO EFFECT
        // raw bacon
        GameRegistry.addShapelessRecipe(new ItemStack(ITEM_META_FOOD, 2, 1), Items.porkchop);
        // bacon and eggs
        GameRegistry.addShapelessRecipe(new ItemStack(ITEM_META_FOOD, 1, 4), new ItemStack(ITEM_META_FOOD, 1, 2), new ItemStack(ITEM_META_FOOD, 1, 3));
        // carrot soup
        GameRegistry.addShapelessRecipe(new ItemStack(ITEM_META_FOOD,1, 5), Items.carrot, Items.wheat, Items.egg);
        // dried flesh (wow! I can't believe it's another mod that turns rotten flesh into leather!)
        GameRegistry.addShapelessRecipe(new ItemStack(ITEM_META_FOOD, 1, 10), Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh, Items.sugar);
        // chocolate bar
        GameRegistry.addShapelessRecipe(new ItemStack(ITEM_META_FOOD, 1, 11), new ItemStack(Items.dye, 1, 3), new ItemStack(Items.dye, 1, 3), Items.sugar, Items.sugar, Items.milk_bucket);
        // spider eye stew
        GameRegistry.addShapelessRecipe(new ItemStack(ITEM_META_FOOD, 1, 12), Items.spider_eye, Items.spider_eye, Items.bowl);
        // nether wart stew
        GameRegistry.addShapelessRecipe(new ItemStack(ITEM_META_FOOD, 1, 13), Items.nether_wart, Items.nether_wart, Items.sugar, Items.bowl);
        // cactus fruit
        GameRegistry.addShapelessRecipe(new ItemStack(ITEM_META_FOOD, 1, 14), Blocks.cactus);
        // carrot pie
        GameRegistry.addShapelessRecipe(new ItemStack(ITEM_META_FOOD, 1, 19), Items.carrot, Items.wheat, Items.egg);
        // blaze cream soup
        GameRegistry.addShapelessRecipe(new ItemStack(ITEM_META_FOOD, 1, 22), Items.blaze_powder, Items.blaze_powder, Items.milk_bucket, Items.bowl); // TODO EFFECT
        // melon salad
        GameRegistry.addShapelessRecipe(new ItemStack(ITEM_META_FOOD, 1, 23), Items.melon, Items.melon, Items.melon, Items.bowl);

        // dough
        GameRegistry.addShapedRecipe(new ItemStack(ITEM_META_FOOD, 4, 25), "WWW", "WAW", "WWW", 'W', Items.wheat, 'A', Items.water_bucket);

        float xp = 0.35f; // the same across all meat smelting recipes in Minecraft
        // bacon
        GameRegistry.addSmelting(new ItemStack(ITEM_META_FOOD, 1, 1), new ItemStack(ITEM_META_FOOD, 1, 2), xp);
        // fried egg
        GameRegistry.addSmelting(Items.egg, new ItemStack(ITEM_META_FOOD, 1, 3), xp);
        // cooked mutton
        GameRegistry.addSmelting(new ItemStack(ITEM_META_FOOD, 1, 6), new ItemStack(ITEM_META_FOOD, 1, 7), xp);
        // cooked squid
        GameRegistry.addSmelting(new ItemStack(ITEM_META_FOOD, 1, 8), new ItemStack(ITEM_META_FOOD, 1, 9), xp);
        // cooked horse meat
        GameRegistry.addSmelting(new ItemStack(ITEM_META_FOOD, 1, 15), new ItemStack(ITEM_META_FOOD, 1, 16), xp);
        // cooked bat wing
        GameRegistry.addSmelting(new ItemStack(ITEM_META_FOOD, 1, 20), new ItemStack(ITEM_META_FOOD, 1, 21), xp);
        // cooked mushroom
        GameRegistry.addSmelting(Blocks.brown_mushroom, new ItemStack(ITEM_META_FOOD, 1, 18), xp);
        // roasted seeds
        GameRegistry.addSmelting(Items.wheat_seeds, new ItemStack(ITEM_META_FOOD, 1, 24), xp);

        // TODO forbidden fruit (default off in config)
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {}

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}
}
