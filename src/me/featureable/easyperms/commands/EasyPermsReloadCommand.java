package me.featureable.easyperms.commands;

import me.featureable.easyperms.EasyPerms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EasyPermsReloadCommand implements CommandExecutor {

    private EasyPerms plugin;

    public EasyPermsReloadCommand(EasyPerms plugin) {
        this.plugin = plugin;

        plugin.getCommand("epreload").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        plugin.refreshAllConfigs();
        return true;
    }
}
