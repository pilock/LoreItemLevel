package fun.mcpc.pilock.loreitemlevel.util;

import com.pxpmc.pxrpg.api.Module;
import com.pxpmc.pxrpg.api.PxRpgAPI;
import com.pxpmc.pxrpg.api.event.PxRpgEvent;
import com.pxpmc.pxrpg.api.modules.equip.EquipManager;
import com.pxpmc.pxrpg.api.modules.equip.EquipModule;
import com.pxpmc.pxrpg.api.modules.equipcontainer.EquipContainer;
import com.pxpmc.pxrpg.api.util.PxRpgUtil;
import com.pxpmc.pxrpg.bukkit.PxRpg;
import fun.mcpc.pilock.loreitemlevel.LoreItemLevel;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicUtil {


    public static String removeColor(String msg)
    {
        return ChatColor.stripColor(msg);
    }


    public static int getInt(String lore)
    {
        return Integer.parseInt(lore.replaceAll("[^0-9.+-]", ""));
    }

    public static <E> void replaceAll(List<E> list, E oldObject, E newObject) {
        for (int i = 0; i < list.size(); i++) {
            if(oldObject.equals(list.get(i))) {
                list.set(i, newObject);
            }
        }
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[1-9]\\d*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static List getRewardList(ItemStack itemStack,String num){
        Map<String,List> RewardCommand=new HashMap<String,List>();
        ConfigurationSection command = LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(itemStack)).getConfigurationSection("command");
        for (String vaule : command.getKeys(false)) {
            RewardCommand.put(vaule, command.getStringList(vaule));
        }
        if(!RewardCommand.containsKey(num)) return null;
        return RewardCommand.get(num);

    }

    public static void getReward(Player player,String num)
    {
        if(!ItemUtil.getPlanName(player.getInventory().getItemInMainHand()).equals("无")){


        if(getRewardList(player.getInventory().getItemInMainHand(),num) != null){

        }
        for (int i = 0; i < getRewardList(player.getInventory().getItemInMainHand(),num).size(); i++)
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player, String.valueOf(getRewardList(player.getInventory().getItemInMainHand(),num).get(i))));
    }

    }


}
