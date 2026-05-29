package me.drakosha.locationfromsignapi;

import lombok.Getter;
import me.drakosha.locationfromsignapi.signlistener.SignListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class LocationFromSignAPI extends JavaPlugin {
    @Getter
    private static Plugin instance;
    @Getter
    private static File pluginConfig;

    public static void init (Plugin plugin) {
        instance = plugin;
        pluginConfig = new File(instance.getDataFolder().getPath() + "/LocationInSing", "mapsConfig.yml");
        createYMLFile(pluginConfig);

        Bukkit.getPluginManager().registerEvents(new SignListener(), instance);
    }
    public static void createYMLFile(File file){
        if (!file.exists()) {
            try {
                instance.getConfig().save(file);
            } catch (IOException e) {
                Bukkit.getLogger().info(e.getMessage());
            }
        }
    }
}