package aneagleinyourmind.foodexpansion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy {
    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.
    public static CreativeTabs tabFoodExpansion = new CreativeTabs("foodExpansion") {

        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return CommonProxy.ITEM_META_FOOD;
        }

        public int func_151243_f()
        {
            return 1;
        }
    };
}
