//Created May 04, 2019.

package me.featureable.easyperms;

import me.featureable.easyperms.commands.*;
import me.featureable.easyperms.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class EasyPerms extends JavaPlugin {

    File configFile = new File(this.getDataFolder(), "config.yml");

    FileConfiguration permissionscfg = null;
    File permfile = null;
    File permfilelocation = new File(this.getDataFolder(), "permissions.yml");

    public String epheader = ChatColor.translateAlternateColorCodes('&', "&8[&4EasyPerms&8]");

    public HashMap<UUID, PermissionAttachment> playerPermissions = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "EASYPERMS IS WORKING!");

        if  (!configFile.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        if (permfile == null) {
            permfile = new File(getDataFolder(), "permissions.yml");
        }
        if (!permfile.exists()) {
            this.saveResource("permissions.yml", false);
        }

        new BlockBreakListener(this);
        new BlockPlaceListener(this);
        new PlayerJoinListener(this);
        new PlayerLeaveListener(this);
        new TestPlayerPermCommand(this);
        new EasyPermsCommand(this);
    }

    @Override
    public void onDisable() {

    }

    public FileConfiguration getCustomConfig() {
        if (permissionscfg == null) {
            reloadCustomConfig();
        }
        return permissionscfg;
    }

    public void reloadCustomConfig() {
        if (permfilelocation.exists()) {
            return;
        }

        if (permissionscfg == null) {
            permfile = new File(getDataFolder(), "permissions.yml");
        }
        permissionscfg = YamlConfiguration.loadConfiguration(permfile);

        Reader defConfigStream = null;
        try {
            defConfigStream = new InputStreamReader(this.getResource("permissions.yml"), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            permissionscfg.setDefaults(defConfig);
        }
    }

    public void setupPermissions(Player player) {
        PermissionAttachment attachment = player.addAttachment(this);
        this.playerPermissions.put(player.getUniqueId(), attachment);
        permissionsSetter(player.getUniqueId());
    }

    private void permissionsSetter(UUID uuid) {
        PermissionAttachment attachment = this.playerPermissions.get(uuid);
        permissionscfg = YamlConfiguration.loadConfiguration(permfile);

        for (String users : permissionscfg.getConfigurationSection("Users").getKeys(false)) {
            for (String usergroups : permissionscfg.getStringList("Users." + users + ".groups")) {
                for (String permissions : permissionscfg.getStringList("Groups." + usergroups + ".permissions")) {
                    attachment.setPermission(permissions, true);
                }
            }
        }
    }

    public ArrayList<String> playersGroups(UUID uuid) {
        ArrayList<String> playerPerms = new ArrayList<>();

        for (String users : permissionscfg.getConfigurationSection("Users").getKeys(false)) {
            for (String usergroups : permissionscfg.getStringList("Users." + users + ".groups")) {
                for (String permissions : permissionscfg.getStringList("Groups." + usergroups + ".permissions")) {
                    playerPerms.add(permissions);
                }
            }
        }

        for (int i = 0; i < playerPerms.size(); i++) {
            Bukkit.getConsoleSender().sendMessage(playerPerms.get(i));
            Bukkit.getConsoleSender().sendMessage("F" + i);
            Bukkit.getPlayer("Featureable").kickPlayer("F");
        }
        return playerPerms;
    }
}
