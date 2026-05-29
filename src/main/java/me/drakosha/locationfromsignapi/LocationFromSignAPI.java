package me.drakosha.locationfromsignapi;

import me.drakosha.locationfromsignapi.signlistener.SignListener;
import me.drakosha.locationfromsignapi.ymlutil.YmlUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class LocationFromSignAPI extends JavaPlugin {
    public static JavaPlugin instance;

    public void init (JavaPlugin plugin) {
        instance = plugin;
        File pluginConfig = new File(instance.getDataFolder().getPath() + "/LocationInSing", "mapsConfig.yml");
        createYMLFile(pluginConfig);
        new YmlUtil(pluginConfig);
        Bukkit.getPluginManager().registerEvents(new SignListener(), instance);
    }
    public void createYMLFile(File file ){
        if (!file.exists()) {
            try {
                instance.getConfig().save(file);
            } catch (IOException e) {
                Bukkit.getLogger().info(e.getMessage());
            }
        }
    }
}