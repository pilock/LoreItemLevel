package fun.mcpc.pilock.loreitemlevel.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;
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

}
