package aneagleinyourmind.item;

import aneagleinyourmind.foodexpansion.ClientProxy;
import aneagleinyourmind.foodexpansion.FoodExpansion;
import com.github.bsideup.jabel.Desugar;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemMetaFood extends ItemFood {
    @SideOnly(Side.CLIENT)
    public IIcon[] textures;

    private static final Set<Integer> NEGATIVE_POTION_IDS = new HashSet<>(Arrays.asList(2, 4, 7, 9, 15, 17, 18, 19, 20));

    public static final MetaFoodEntry[] FOOD_ENTRIES = {
        // saturation is represented on a 0-1 scale
        new MetaFoodEntry("jelly", 4, 0.25f),  // 0
        new MetaFoodEntry("bacon_raw", 1, 0.025f), // 1
        new MetaFoodEntry("bacon_cooked", 3, 0.125f), // 2
        new MetaFoodEntry("fried_egg", 2, 0.075f), // 3
        new MetaFoodEntry("bacon_and_eggs", 7, .6f), // 4
        new MetaFoodEntry("carrot_soup", 10, 1f), // 5
        new MetaFoodEntry("mutton_raw", 2, 0.075f), // 6
        new MetaFoodEntry("mutton_cooked", 6, 0.45f), // 7
        new MetaFoodEntry("squid_raw", 1, 0.025f), // 8
        new MetaFoodEntry("squid_cooked", 3, 0.15f), // 9
        new MetaFoodEntry("dried_flesh", 6, 0.125f), // 10
        new MetaFoodEntry("chocolate_bar", 8, 0.8f), // 11
        new MetaFoodEntry("spider_eye_stew", 4, 0.2f), // 12
        new MetaFoodEntry("nether_wart_stew", 4, 0.175f), // 13
        new MetaFoodEntry("cactus_fruit", 1, 0.025f), // 14
        new MetaFoodEntry("horse_meat_raw", 3, 0.075f), // 15
        new MetaFoodEntry("horse_meat_cooked", 8, 0.65f), // 16
        new MetaFoodEntry("forbidden_fruit", 20, 1f), // 17
        new MetaFoodEntry("cooked_mushroom", 2, 0.1f), // 18
        new MetaFoodEntry("carrot_pie", 8, 0.65f), // 19
        new MetaFoodEntry("bat_wing_raw", 1, 0.025f), // 20
        new MetaFoodEntry("bat_wing_cooked", 3, 0.075f), // 21
        new MetaFoodEntry("blaze_cream_soup", 4, 0.175f), // 22
        new MetaFoodEntry("melon_salad", 6, 0.375f), // 23
        new MetaFoodEntry("roasted_seeds", 1, 0.025f), // 24
        new MetaFoodEntry("dough", 0, 0.025f), // 25
        new MetaFoodEntry("apple_sauce", 4, 0.35f), // 26
        new MetaFoodEntry("caramel_apple", 5, 0.4f), // 27
    };

    public ItemMetaFood(String unlocalizedName, boolean isDogFood) {
        super(0, isDogFood);
        setUnlocalizedName(unlocalizedName);
        setHasSubtypes(true);
        setCreativeTab(ClientProxy.tabFoodExpansion);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean hasEffect(ItemStack stack) { // the enchantment shimmer effect
        return (stack.getItemDamage() == 17);
    }

    @Override
    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            int meta = stack.getItemDamage();

            switch (meta) {
                case 12, 13, 19, 22, 23, 26 -> { // foods with bowls
                    ItemStack bowl = new ItemStack(Items.bowl);

                   returnItem(player, bowl);
                }
                case 27 -> {
                    ItemStack stick = new ItemStack(Items.stick);

                    returnItem(player, stick);
                }
            }

            applyPotionEffectsForFood(meta, world, player);
        }
    }

    private void returnItem(EntityPlayer player, ItemStack returnedItem) {
        boolean couldAddItem = player.inventory.addItemStackToInventory(returnedItem);

        if (!couldAddItem) {
            // throw the item on the ground if there is no inventory space
            player.func_146097_a(returnedItem, false, false);
        }
    }

    private void applyPotionEffectsForFood(int meta, World world, EntityPlayer player) {
        switch(meta) {
            // jelly
            case 0 -> player.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 200, 1));


            case 12 -> { // spider eye stew
                player.addPotionEffect(new PotionEffect(Potion.poison.getId(), 80, 1));
                player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 300, 1));
            }

            // netherwart soup
            case 13 -> {
                var potionEffects = player.getActivePotionEffects();
                var idsToRemove = new HashSet<Integer>();

                player.setFire(2);

                potionEffects.forEach(effect -> {
                    int potionId = effect.getPotionID();
                    if (NEGATIVE_POTION_IDS.contains(potionId)) {
                        idsToRemove.add(potionId);
                    }
                });

                // avoid ConcurrentModificationException
                idsToRemove.forEach(player::removePotionEffect);
            }

            case 20 -> { // raw bat
                if (world.rand.nextBoolean()) {
                    player.addPotionEffect(new PotionEffect(Potion.hunger.getId(), 400, 1));
                }
            }

            case 21 -> { // cooked bat
                if (world.rand.nextInt(10) == 1) {
                    player.addPotionEffect(new PotionEffect(Potion.hunger.getId(), 400, 1));
                }
            }

            // blaze cream
            case 22 -> player.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 300, 1));
        }
    }

    // number of food "shanks" restored
    @Override
    public int func_150905_g(ItemStack stack) {
        return FOOD_ENTRIES[stack.getItemDamage()].foodAmount();
    }

    // saturation
    @Override
    public float func_150906_h(ItemStack stack) {
        return FOOD_ENTRIES[stack.getItemDamage()].saturation();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int meta = 0; meta < FOOD_ENTRIES.length; ++meta)
            list.add(new ItemStack(item, 1, meta));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        textures = new IIcon[FOOD_ENTRIES.length];

        for (int i = 0; i < FOOD_ENTRIES.length; ++i) {
            System.out.println(FOOD_ENTRIES[i].textureName());
            textures[i] = register.registerIcon(FoodExpansion.RESOURCE_ID + FOOD_ENTRIES[i].textureName());
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        int j = MathHelper.clamp_int(meta, 0, FOOD_ENTRIES.length);
        return textures[j];
    }

    @Desugar
    public record MetaFoodEntry(String textureName, int foodAmount, float saturation) {}
}
