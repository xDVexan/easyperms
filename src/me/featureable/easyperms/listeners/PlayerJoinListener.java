//Created May 04, 2019.

package me.featureable.easyperms.listeners;

import me.featureable.easyperms.EasyPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private EasyPerms plugin;

    public PlayerJoinListener(EasyPerms plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        Bukkit.getConsoleSender().sendMessage(e.getPlayer().getDisplayName() + " has joined.");
        Player player = e.getPlayer();
        plugin.setupPermissions(player);
    }
}
