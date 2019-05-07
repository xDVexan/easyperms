package me.featureable.easyperms.commands;

import me.featureable.easyperms.EasyPerms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestPlayerPermCommand implements CommandExecutor {

    private EasyPerms plugin;

    public TestPlayerPermCommand(EasyPerms plugin) {
        this.plugin = plugin;

        plugin.getCommand("testperm").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        if (player.hasPermission("modifyworld.blockbreak")) {
            player.sendMessage("HAVE PERM");
        } else {
            player.sendMessage("DONT HAVE PERM");
        }
        return true;
    }
}
