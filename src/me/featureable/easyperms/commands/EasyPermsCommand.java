package me.featureable.easyperms.commands;

import me.featureable.easyperms.EasyPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EasyPermsCommand implements CommandExecutor {

    private EasyPerms plugin;

    public EasyPermsCommand(EasyPerms plugin) {
        this.plugin = plugin;

        plugin.getCommand("ep").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(plugin.epheader + ChatColor.RED + " Please use /ep help for further assistance.");
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
                    //Retrieve data for user
                    break;
            }
        }

        return true;
    }
}
