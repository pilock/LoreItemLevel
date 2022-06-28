package fun.mcpc.pilock.loreitemlevel.command;

import fun.mcpc.pilock.loreitemlevel.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Commands implements TabExecutor {

    /**
     * lil up升级物品
     * lil add 1 player
     * @param sender
     * @param command
     * @param label
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (((sender instanceof Player)) && (label.equalsIgnoreCase("lil"))) {
            if((args.length == 1) && (args[0].equalsIgnoreCase("up"))) {
                ItemUtil.updateItem(player, player.getInventory().getItemInMainHand(),false);
                return true;
            }
            if((args.length == 3)) {
                if (args[0].equalsIgnoreCase("add")){
                    Player player1 = Bukkit.getPlayerExact(args[2]);
                    ItemUtil.addItemExp(player1,args[1]);
                    player.sendMessage("添加经验成功 +"+args[1]);
                    return true;
                }
                if (args[0].equalsIgnoreCase("remove")){
                    Player player1 = Bukkit.getPlayerExact(args[2]);
                    ItemUtil.delItemExp(player1,args[1]);
                    player.sendMessage("删除经验成功 +"+args[1]);
                    return true;
                }

            }

        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] strings) {
        List<String> tabCommands = new ArrayList<>();
        switch (strings.length) {
            case 1:
                tabCommands.add("help");
                tabCommands.add("join");
                tabCommands.add("up");
                tabCommands.add("gui");
                tabCommands.add("repair");
                if (!sender.isOp()) {
                    return tabCommands;
                }
                tabCommands.add("create");
                tabCommands.add("edit");
                tabCommands.add("addexp");
                tabCommands.add("setexp");
                tabCommands.add("addlevel");
                tabCommands.add("setlevel");
                tabCommands.add("danlu");
                tabCommands.add("reload");
                return tabCommands;

        }
        return null;
    }
}
