package fun.mcpc.pilock.loreitemlevel.command;

import fun.mcpc.pilock.loreitemlevel.LoreItemLevel;
import fun.mcpc.pilock.loreitemlevel.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
            if((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
                LoreItemLevel.api.init();
                player.sendMessage("插件重载成功");
                return true;
            }
            if((args.length == 3)) {
                if (player.isOp()&&args[0].equalsIgnoreCase("add")){
                    Player player1 = Bukkit.getPlayerExact(args[2]);
                    ItemUtil.addItemExp(player1,args[1]);
                    //player.sendMessage("添加经验成功 +"+args[1]);
                    return true;
                }
                if (player.isOp()&&args[0].equalsIgnoreCase("remove")){
                    Player player1 = Bukkit.getPlayerExact(args[2]);
                    ItemUtil.delItemExp(player1,args[1]);
                    //player.sendMessage("删除经验成功 +"+args[1]);
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
                tabCommands.add("up");
                if (!sender.isOp()) {
                    return tabCommands;
                }
                tabCommands.add("reload");
                tabCommands.add("add");
                tabCommands.add("remove");
                return tabCommands;
            case 2:
                if (!sender.isOp()) {
                    return null;
                }
                switch (strings[0]) {
                    case "add":
                        tabCommands.add("<数量>");
                        break;
                    case "remove":
                        tabCommands.add("<数量>");
                        break;
                }
                return tabCommands;
            case 3:
                if (!sender.isOp()) {
                    return null;
                }
                for (OfflinePlayer player1 : LoreItemLevel.api.getServer().getOnlinePlayers()) {
                    tabCommands.add(player1.getName());
                }
                return tabCommands;

        }
        return null;
    }
}
