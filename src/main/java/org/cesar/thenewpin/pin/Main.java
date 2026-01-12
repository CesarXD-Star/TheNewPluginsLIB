package org.cesar.thenewpin.pin;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private PinManager pinManager;
    private PlayerData playerData;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        playerData = new PlayerData(this);
        pinManager = new PinManager();

        // Registrar todos los listeners
        getServer().getPluginManager().registerEvents(pinManager, this);
        getServer().getPluginManager().registerEvents(new PinMenu(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new LoginLockListener(), this);

        getCommand("pin").setExecutor((sender, command, label, args) -> {
            if (sender instanceof org.bukkit.entity.Player player) {
                PinMenu.openMenu(player);
            }
            return true;
        });
    }

    public static Main getInstance() {
        return instance;
    }

    public PinManager getPinManager() {
        return pinManager;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }
}
