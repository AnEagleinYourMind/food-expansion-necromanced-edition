package aneagleinyourmind.foodexpansion;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.Random;

import static aneagleinyourmind.foodexpansion.CommonProxy.ITEM_META_FOOD;

public class AnimalDropEventHandler {
    private static final Random random = new Random();
    public AnimalDropEventHandler() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);  // he has such a way with words
    }

    @SubscribeEvent
    public void onMobDrop(LivingDropsEvent event) {
        Entity diedEntity = event.entity;
        int quantity = random.nextInt(3); // TODO: looting enchantment?

        // Java 17 pattern matching can achieve this
        if (diedEntity instanceof EntitySheep && Config.enable_sheep_drops) {
            diedEntity.entityDropItem(new ItemStack(ITEM_META_FOOD, quantity, 6), 0);
        } else if (diedEntity instanceof EntitySquid && Config.enable_squid_drops) {
            diedEntity.entityDropItem(new ItemStack(ITEM_META_FOOD, quantity, 8), 0);
        } else if (diedEntity instanceof EntityHorse && Config.enable_horse_drops) {
            diedEntity.entityDropItem(new ItemStack(ITEM_META_FOOD, quantity, 15), 0);
        } else if (diedEntity instanceof EntityBat && Config.enable_bat_drops) {
            diedEntity.entityDropItem(new ItemStack(ITEM_META_FOOD, quantity, 20), 0);
        }
    }
}
