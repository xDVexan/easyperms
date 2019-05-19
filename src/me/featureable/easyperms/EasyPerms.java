//Created May 04, 2019.

package me.featureable.easyperms;

import me.featureable.easyperms.commands.*;
import me.featureable.easyperms.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EasyPerms extends JavaPlugin {

    File configFile = new File(this.getDataFolder(), "config.yml");
    File configFileLoc = new File(this.getDataFolder(), "config.yml");

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
        permissionscfg = YamlConfiguration.loadConfiguration(permfile);

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            setupPermissions(player);
        }
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            removeAllPlayersPerms(player);
        }
        playerPermissions.clear();
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

        for (String users : permissionscfg.getConfigurationSection("Users").getKeys(false)) {
            for (String userperms : permissionscfg.getStringList("Users." + users + ".permissions")) {
                attachment.setPermission(userperms, true);
            }
        }
    }

    public void getPlayersGroups(CommandSender cmdsender, UUID uuid, String chatColor) {
        PermissionAttachment attachment = this.playerPermissions.get(uuid);
        permissionscfg = YamlConfiguration.loadConfiguration(permfile);

        for (String users : permissionscfg.getConfigurationSection("Users").getKeys(false)) {
            for (String usergroups : permissionscfg.getStringList("Users." + users + ".groups")) {
                cmdsender.sendMessage(ChatColor.valueOf(chatColor) + usergroups);
            }
        }
    }

    public void getGroupsPerms(CommandSender cmdsender, String group) {
        permissionscfg = YamlConfiguration.loadConfiguration(permfile);

        for (String groupperms : permissionscfg.getStringList("Groups." + group + ".permissions")) {
            cmdsender.sendMessage(groupperms);
        }
    }

    public void getPlayersPerms(CommandSender cmdsender, UUID uuid, String chatColor) {
        PermissionAttachment attachment = this.playerPermissions.get(uuid);
        permissionscfg = YamlConfiguration.loadConfiguration(permfile);

        for (String users : permissionscfg.getConfigurationSection("Users").getKeys(false)) {
            for (String userperms : permissionscfg.getStringList("Users." + users + ".permissions")) {
                cmdsender.sendMessage(ChatColor.valueOf(chatColor) + userperms);
            }
        }
    }

    public void refreshAllConfigs() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            removeAllPlayersPerms(player);

            setupPermissions(player);
        }
    }

    public void refreshPlayersPerms(Player player) {
        removeAllPlayersPerms(player);

        setupPermissions(player);
    }

    public void removeAllPlayersPerms(Player player) {
        PermissionAttachment attachment = this.playerPermissions.get(player.getUniqueId());
        player.removeAttachment(attachment);
    }

    public void addPermToPlayer(Player player, String permission) {
        PermissionAttachment attachment = this.playerPermissions.get(player.getUniqueId());
        permissionscfg = YamlConfiguration.loadConfiguration(permfile);

        //permissionscfg.set("Users." + player.getName() + ".permissions", permission);

        try {
            List<String> permissions = permissionscfg.getStringList("Users." + player.getName() + ".permissions");
            permissions.add(permission);
            permissionscfg.set("Users." + player.getName() + ".permissions", permissions);
            permissionscfg.save(permfilelocation);
            refreshPlayersPerms(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
