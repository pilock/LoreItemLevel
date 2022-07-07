package fun.mcpc.pilock.loreitemlevel.util;

import fun.mcpc.pilock.loreitemlevel.LoreItemLevel;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemUtil {


    //获取方案组list
    public static List getPlan(){
        return LoreItemLevel.api.getConfig().getList("planlist");
    }

    //判断手持物品是否是可升级物品
//    public static boolean isLoreLevelItem(ItemStack itemStack) {
//        if(itemStack.getType()!= Material.AIR&&itemStack.hasItemMeta()&&itemStack.getItemMeta().hasLore()) {
//
//            if (LoreItemLevel.api.planslevel.containsValue(itemStack.getItemMeta().getLore().toString().replace("&", "§"))){
//                return true;
//            }
//        }
//        return false;
//    }

    //获取手持物品方案id
    public static String getPlanName(ItemStack itemStack){
        if(itemStack.getType()!= Material.AIR&&itemStack.hasItemMeta()&&itemStack.getItemMeta().hasLore()) {
            for(String key : LoreItemLevel.api.planslevel.keySet()){
                if(BasicUtil.removeColor(itemStack.getItemMeta().getLore().toString()).contains(BasicUtil.removeColor(LoreItemLevel.api.planslevel.get(key)))){
                    return key;
                }
            }
        }
        return "无";
        }

    //升级物品积分
    public static void itemLoreExpUp(Player player,String plan,ItemStack itemStack,Integer addExp) {
        if(!ItemUtil.getPlanName(itemStack).equals("无")){
            String oldLoreExpString="",newLoreExpString="";
            YamlConfiguration planYaml = LoreItemLevel.api.plansMap.get(plan);
            for(String loreString : itemStack.getItemMeta().getLore()) {
                if(BasicUtil.removeColor(loreString).contains(BasicUtil.removeColor(planYaml.getString("levelexp")))) {
                    oldLoreExpString=loreString;
                    String newLoreExp=planYaml.getString("levelexp")+String.valueOf((BasicUtil.getInt(BasicUtil.removeColor(loreString)))+addExp);
                    newLoreExpString=newLoreExp;
                }
            }
            List loreList = itemStack.getItemMeta().getLore();
            BasicUtil.replaceAll(loreList, oldLoreExpString, newLoreExpString);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setLore(loreList);
            itemStack.setItemMeta(itemMeta);
            autoUpItem(player,itemStack,LoreItemLevel.api.plansMap.get(plan).getBoolean("autoup"));
        }
    }

    //获取方案生物名积分
    public static int getCustomeMobExp(String plan,String mobName){
        YamlConfiguration planYaml = LoreItemLevel.api.plansMap.get(plan);
        if(planYaml.getConfigurationSection("mobexp").contains(BasicUtil.removeColor(mobName))){
            return planYaml.getConfigurationSection("mobexp").getInt(BasicUtil.removeColor(mobName));
        }
        return planYaml.getInt("defaultmobexp");
    }


    //获取手持物品等级
    public static int getItemLevel(String plan,ItemStack itemStack) {
        if(!ItemUtil.getPlanName(itemStack).equals("无")){
            for(String value : itemStack.getItemMeta().getLore()) {
                if(BasicUtil.removeColor(value).contains(BasicUtil.removeColor(LoreItemLevel.api.plansMap.get(plan).getString("level")))) {
                    return BasicUtil.getInt(BasicUtil.removeColor(value));
                }
            }
        }
        return 0;
    }
    //获取手持物品经验
    public static int getItemExp(String plan,ItemStack itemStack) {
        if(!ItemUtil.getPlanName(itemStack).equals("无")){
            for(String value : itemStack.getItemMeta().getLore()) {
                if(BasicUtil.removeColor(value).contains(BasicUtil.removeColor(LoreItemLevel.api.plansMap.get(plan).getString("levelexp")))) {
                    return BasicUtil.getInt(BasicUtil.removeColor(value));
                }
            }
        }
        return 0;
    }

    //增加手持物品经验
    public static void addItemExp(Player player, String exp) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if(!ItemUtil.getPlanName(itemStack).equals("无")){

            if(BasicUtil.isNumeric(exp)) {
                itemLoreExpUp(player,getPlanName(itemStack),itemStack, Integer.parseInt(exp));
                player.sendMessage("添加经验成功 +"+exp);
            }else {
                player.sendMessage("§f[§c§l!§f] §c数量必须是数字");
            }
        }else {
            player.sendMessage("§f[§c§l!§f] §c玩家手持物品无法设置");
        }
    }
    //减少手持物品经验
    public static void delItemExp(Player player,String exp) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if(!ItemUtil.getPlanName(itemStack).equals("无")){
            if(BasicUtil.isNumeric(exp)) {
                itemLoreExpDel(itemStack, Integer.parseInt(exp));
                player.sendMessage("删除经验成功 +"+exp);
            }else {
                player.sendMessage("§f[§c§l!§f] §c数量必须是数字");
            }
        }else {
            player.sendMessage("§f[§c§l!§f] §c玩家手持物品无法设置");
        }
    }

    //
    public static void itemLoreExpDel(ItemStack itemStack,Integer delExp) {
        if(!ItemUtil.getPlanName(itemStack).equals("无")){
            String oldLoreExpString="",newLoreExpString="";
            for(String loreString : itemStack.getItemMeta().getLore()) {
                if(BasicUtil.removeColor(loreString).contains(BasicUtil.removeColor(LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(itemStack)).getString("levelexp")))) {
                    oldLoreExpString=loreString;
                    String newLoreExp="0";
                    if(BasicUtil.getInt(BasicUtil.removeColor(loreString))<=delExp) {
                        newLoreExp="0";
                    }else {
                        newLoreExp=String.valueOf((BasicUtil.getInt(BasicUtil.removeColor(loreString)))-delExp);
                    }

                    newLoreExpString=loreString.replace(String.valueOf(BasicUtil.getInt(BasicUtil.removeColor(loreString))),newLoreExp);

                }
            }
            List loreList = itemStack.getItemMeta().getLore();
            BasicUtil.replaceAll(loreList, oldLoreExpString, newLoreExpString);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setLore(loreList);
            itemStack.setItemMeta(itemMeta);
            //getServer().broadcastMessage("§4!§a武器升级经验+1");
        }
    }

    //升级物品
    public static void updateItem(Player player,ItemStack itemStack,boolean auto) {
        if(itemStack.getType()== Material.AIR) {
            player.sendMessage("§c§l错误 §3§l必须手持物品才可以升级");
            return;
        }
        if(getPlanName(itemStack).equals("无")){
            player.sendMessage("§c§l错误 §3§l手持物品不可以升级");
            return;
        }
        Map<String,String> LevelExp=new HashMap<String,String>();
        for (String vaule : LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(itemStack)).getConfigurationSection("exp").getKeys(false)) {
            LevelExp.put(vaule,LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(itemStack)).getConfigurationSection("exp").getString(vaule));
        }
        String itemLevel=String.valueOf(getItemLevel(ItemUtil.getPlanName(itemStack),itemStack)),itemLevelExp=String.valueOf(getItemExp(ItemUtil.getPlanName(itemStack),itemStack));
        if(!LevelExp.containsKey(String.valueOf((Integer.parseInt(itemLevel)+1)))) {
            if(!auto) {
                player.sendMessage("§c§l错误 §3§l物品等级不存在，请联系管理员");
            }
            return;
        }
        String needExp = LevelExp.get(itemLevel);
        if(Integer.parseInt(itemLevelExp)<Integer.parseInt(needExp)) {
            player.sendMessage("§c§l错误 §3§l物品经验不足，还差"+(Integer.parseInt(needExp)-Integer.parseInt(itemLevelExp))+"经验");
            return;
        }
        List itemLore = itemStack.getItemMeta().getLore();
        int charu = 0,charubool=0;
        for(String lore : itemStack.getItemMeta().getLore()) {
            charubool++;
            if(BasicUtil.removeColor(lore).contains(BasicUtil.removeColor(LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(itemStack)).getString("level")))) {

                String newLevelLoreString=LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(itemStack)).getString("level")+String.valueOf((Integer.parseInt(itemLevel)+1));

                BasicUtil.replaceAll(itemLore, lore, newLevelLoreString);
            }
            if(BasicUtil.removeColor(lore).contains(BasicUtil.removeColor(LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(itemStack)).getString("levelexp")))) {
                String newLevelexp=LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(itemStack)).getString("levelexp")+String.valueOf(Integer.parseInt(itemLevelExp)-Integer.parseInt(needExp));
                BasicUtil.replaceAll(itemLore, lore, newLevelexp);
            }
            if(BasicUtil.removeColor(lore).contains(BasicUtil.removeColor(LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(itemStack)).getString("loresign")))) {
                itemLore.remove(lore);
            }
            if(BasicUtil.removeColor(lore).contains(BasicUtil.removeColor(LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(itemStack)).getString("levelexp")))) {
                charu=charubool;
            }
        }
        if(charu==0) {
            player.sendMessage("§f[§c§l!§f] §c升级失败");
            return;
        }
        itemLore.addAll(charu, getUpLore(itemStack));
        player.sendMessage("§f[§6§l!§f] §a升级成功");
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);

    }

    public static List getUpLore(ItemStack itemStack) {
        if(!ItemUtil.getPlanName(itemStack).equals("无")){
            String itemLevel=String.valueOf(getItemLevel(ItemUtil.getPlanName(itemStack),itemStack));
            Map<String,List> LevelLore=new HashMap<String,List>();
            for (String vaule : LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(itemStack)).getConfigurationSection("levellore").getKeys(false)) {
                LevelLore.put(vaule, LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(itemStack)).getConfigurationSection("levellore").getStringList(vaule));
            }
            List<String> addItemLore=LevelLore.get(itemLevel);

            for(String lore : addItemLore) {


                    addItemLore.set(addItemLore.indexOf(lore), LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(itemStack)).getString("loresign")+lore);


            }
            return addItemLore;
        }
        return null;
    }

    public static void autoUpItem(Player player,ItemStack itemStack,boolean auto) {
        if(auto) {
            if(!ItemUtil.getPlanName(itemStack).equals("无")){
                Map<String,String> LevelExp=new HashMap<String,String>();
                for (String vaule : LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(itemStack)).getConfigurationSection("exp").getKeys(false)) {
                    LevelExp.put(vaule,LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(itemStack)).getConfigurationSection("exp").getString(vaule));
                }
                String itemLevel=String.valueOf(getItemLevel(ItemUtil.getPlanName(itemStack),itemStack)),itemLevelExp=String.valueOf(getItemExp(ItemUtil.getPlanName(itemStack),itemStack));
                String needExp = LevelExp.get(itemLevel);
                if(getItemExp(ItemUtil.getPlanName(itemStack),itemStack)>=Integer.parseInt(needExp)) {
                    updateItem(player, itemStack,true);
                }
            }
        }
    }

}
