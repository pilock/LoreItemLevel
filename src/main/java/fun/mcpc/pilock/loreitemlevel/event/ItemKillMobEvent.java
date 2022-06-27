package fun.mcpc.pilock.loreitemlevel.event;

import fun.mcpc.pilock.loreitemlevel.LoreItemLevel;
import fun.mcpc.pilock.loreitemlevel.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class ItemKillMobEvent implements Listener {

    @EventHandler
    public void killMob(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        player.sendMessage("1:"+ItemUtil.getPlan().toString());
        player.sendMessage("2:"+ LoreItemLevel.api.plansloresign.entrySet());
        LoreItemLevel.api.getLogger().info("1:"+ LoreItemLevel.api.plansloresign.entrySet());
    }
}
