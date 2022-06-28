package fun.mcpc.pilock.loreitemlevel.util;

import fun.mcpc.pilock.loreitemlevel.LoreItemLevel;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;

public class ItemUtil {


    //获取方案组list
    public static List getPlan(){
        return LoreItemLevel.api.getConfig().getList("planlist");
    }

    //判断手持物品是否是可升级物品
    public static boolean isLoreLevelItem(ItemStack itemStack) {
        if(itemStack.getType()!= Material.AIR&&itemStack.hasItemMeta()&&itemStack.getItemMeta().hasLore()) {
            if (LoreItemLevel.api.planslevel.containsValue(itemStack.getItemMeta().getLore().toString().replace("&", "§"))){
                return true;
            }
        }
        return false;
    }

    public static String getPlanName(ItemStack itemStack){
        for(String key : LoreItemLevel.api.planslevel.keySet()){
            if(itemStack.getItemMeta().getLore().equals(LoreItemLevel.api.planslevel.get(key))){
                System.out.println("key:"+key+"value:"+ LoreItemLevel.api.planslevel.get(key));
                return key;
            }

            }
        return "无";
        }


}
