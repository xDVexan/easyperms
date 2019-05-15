package me.featureable.easyperms.commands;

import me.featureable.easyperms.EasyPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class EasyPermsCommand implements CommandExecutor {

    private EasyPerms plugin;

    public EasyPermsCommand(EasyPerms plugin) {
        this.plugin = plugin;

        plugin.getCommand("ep").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("test");
            return true;
        }

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "user":
                    sender.sendMessage(plugin.epheader + ChatColor.RED + " Please enter a username.");
                    break;
                case "help":
                    sender.sendMessage(plugin.epheader + ChatColor.RED + ChatColor.BOLD + " HELP");
                    break;
                default:
                    sender.sendMessage(plugin.epheader + ChatColor.RED + " Unknown command. Please use /ep help for further assistance.");
                    break;
            }
        }

        if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "user":
                    sender.sendMessage(plugin.epheader + ChatColor.RED + " User Info: " + args[1]);
                    ArrayList<String> playerPerms = new ArrayList<>();
                    Player targetPlayer = Bukkit.getPlayer(args[1]);
                    plugin.getPlayersGroups(sender, targetPlayer.getUniqueId());
//                    for (int i = 0; i < playerPerms.size(); i++) {
//                        Bukkit.getConsoleSender().sendMessage(playerPerms.get(i));
//                    }
                    break;
                default:
                    sender.sendMessage(plugin.epheader + ChatColor.RED + " Unknown command. Please use /ep help for further assistance.");
            }
        }

        return true;
    }
}
