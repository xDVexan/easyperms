//Created May 04, 2019.

package me.featureable.easyperms.listeners;

import me.featureable.easyperms.EasyPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    private EasyPerms plugin;

    public PlayerLeaveListener(EasyPerms plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        plugin.playerPermissions.remove(player.getUniqueId());
    }
}
