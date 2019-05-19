package me.featureable.easyperms.commands;

import me.featureable.easyperms.EasyPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EasyPermsCommand implements CommandExecutor {

    private EasyPerms plugin;

    public EasyPermsCommand(EasyPerms plugin) {
        this.plugin = plugin;

        plugin.getCommand("ep").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(plugin.epheader + ChatColor.RED + " Please enter arguments.");
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
                case "reload":
                    plugin.refreshAllConfigs();
                    sender.sendMessage(plugin.epheader + ChatColor.RED + " Reloaded the config files.");
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
                    Player targetPlayer = Bukkit.getPlayer(args[1]);
                    sender.sendMessage(ChatColor.RED + "Groups:");
                    plugin.getPlayersGroups(sender, targetPlayer.getUniqueId(), "WHITE");
                    sender.sendMessage("");
                    sender.sendMessage(ChatColor.RED + "Permissions:");
                    plugin.getPlayersPerms(sender, targetPlayer.getUniqueId(), "WHITE");
                    break;
                case "group":
                    sender.sendMessage(plugin.epheader + ChatColor.RED + " Group Info: " + args[1]);
                    String targetGroup = args[1];
                    plugin.getGroupsPerms(sender, targetGroup);
                    break;
                default:
                    sender.sendMessage(plugin.epheader + ChatColor.RED + " Unknown command. Please use /ep help for further assistance.");
                    break;
            }
        }

        if (args.length == 4) {
            switch (args[0].toLowerCase()) {
                case "user":
                    if (args[2].equalsIgnoreCase("add")) {
                        if (!args[3].equalsIgnoreCase("")) {
                            sender.sendMessage(plugin.epheader + ChatColor.RED + " Added permission '" + args[3] + "' to player " + args[1]);
                            plugin.addPermToPlayer(Bukkit.getPlayer(args[1]), args[3]);
                        }
                    }
                    break;
                default:
                    sender.sendMessage(plugin.epheader + ChatColor.RED + " Unknown command. Please use /ep help for further assistance.");
                    break;
            }
        }

        return true;
    }
}
