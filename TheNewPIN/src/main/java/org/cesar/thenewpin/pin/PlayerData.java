package org.cesar.thenewpin.pin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerData {

    private final JavaPlugin plugin;
    private final File file;
    private final FileConfiguration data;

    public PlayerData(JavaPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "players.yml");
        if (!file.exists()) {
            plugin.saveResource("players.yml", false);
        }
        this.data = YamlConfiguration.loadConfiguration(file);
    }

    public boolean hasPin(UUID uuid) {
        return data.contains(uuid.toString());
    }

    public void setPin(UUID uuid, String pin) {
        data.set(uuid.toString(), pin);
        save();
    }

    public String getPin(UUID uuid) {
        return data.getString(uuid.toString());
    }

    public void save() {
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
