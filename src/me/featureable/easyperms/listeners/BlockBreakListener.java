//Created May 04, 2019.

package me.featureable.easyperms.listeners;

import me.featureable.easyperms.EasyPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    private EasyPerms plugin;

    public BlockBreakListener(EasyPerms plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        Player player = e.getPlayer();

        if (!player.hasPermission("modifyworld.blockbreak")) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You do not have permission to do that!");
        }
    }
}
