package fun.mcpc.pilock.loreitemlevel.util;

import fun.mcpc.pilock.loreitemlevel.LoreItemLevel;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class ItemUtil {


    public static List getPlan(){
        return LoreItemLevel.api.getConfig().getList("planlist");
    }
    
}
