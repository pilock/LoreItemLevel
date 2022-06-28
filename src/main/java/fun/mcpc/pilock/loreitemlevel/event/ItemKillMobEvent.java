package fun.mcpc.pilock.loreitemlevel.event;

import fun.mcpc.pilock.loreitemlevel.LoreItemLevel;
import fun.mcpc.pilock.loreitemlevel.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class ItemKillMobEvent implements Listener {

    @EventHandler
    public void killMob(EntityDeathEvent event) {
        if ((event.getEntity().getKiller() == null) || (!(event.getEntity().getKiller() instanceof Player))) {
            return;
        }
        Player player = event.getEntity().getKiller();
        ItemStack item = player.getInventory().getItemInMainHand();

            if(!ItemUtil.getPlanName(item).equals("无")){
                if(LoreItemLevel.api.plansMap.get(ItemUtil.getPlanName(item)).getStringList("blackmob").contains(event.getEntity().getType().toString())) {
                    return;
                }
                ItemUtil.itemLoreExpUp(ItemUtil.getPlanName(item),item,ItemUtil.getCustomeMobExp(ItemUtil.getPlanName(item),event.getEntity().getName().toLowerCase()));

            }


    }
}
