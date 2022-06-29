package fun.mcpc.pilock.loreitemlevel;

import fun.mcpc.pilock.loreitemlevel.command.Commands;
import fun.mcpc.pilock.loreitemlevel.event.ItemKillMobEvent;
import fun.mcpc.pilock.loreitemlevel.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LoreItemLevel extends JavaPlugin {


    public static LoreItemLevel api= null;
    private static YamlConfiguration config;
    public Map<String, YamlConfiguration> plansMap = new HashMap<>();
    public Map<String, String> plansloresign = new HashMap<>();
    public Map<String, String> planslevel = new HashMap<>();

    @Override
    public void onEnable() {
        super.getLogger().info("LoreItemLevel插件开启");
        api=this;
        saveDefaultConfig();
        init();
        Bukkit.getServer().getPluginManager().registerEvents(new ItemKillMobEvent(), this);
        this.getCommand("lil").setExecutor(new Commands());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void init(){
        plansMap.clear();
        plansloresign.clear();
        planslevel.clear();
        config = getCustomConfig("config.yml");
        for(int i = 0; i< ItemUtil.getPlan().size();i++){
            plansMap.put(String.valueOf(ItemUtil.getPlan().get(i)),getPlanConfig(ItemUtil.getPlan().get(i)+".yml"));
            plansloresign.put(String.valueOf(ItemUtil.getPlan().get(i)),getPlanConfig(ItemUtil.getPlan().get(i)+".yml").getString("loresign"));
            planslevel.put(String.valueOf(ItemUtil.getPlan().get(i)),getPlanConfig(ItemUtil.getPlan().get(i)+".yml").getString("level"));
        }
    }

    private YamlConfiguration getCustomConfig(String configName) {
        File file = new File(getDataFolder(), configName);
        return YamlConfiguration.loadConfiguration(file);
    }

    private YamlConfiguration getPlanConfig(String configName) {
        File file = new File(getDataFolder()+ File.separator + "plan", configName);
        return YamlConfiguration.loadConfiguration(file);
    }


}
