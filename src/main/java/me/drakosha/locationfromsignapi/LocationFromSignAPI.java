package me.drakosha.locationfromsignapi;

import me.drakosha.locationfromsignapi.signlistener.SignListener;
import me.drakosha.locationfromsignapi.ymlutil.YmlUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class LocationFromSignAPI extends JavaPlugin {

    public static JavaPlugin instance;
    public static File pluginConfig;

    public static void init (JavaPlugin plugin) {
        instance = plugin;
        pluginConfig = new File(instance.getDataFolder().getPath() + "/LocationInSing", "mapsConfig.yml");
        createYMLFile();
        YmlUtil.loadConfig();
        Bukkit.getPluginManager().registerEvents(new SignListener(), instance);
    }
    public static void createYMLFile(){
        if (!pluginConfig.exists()) {
            try {
                instance.getConfig().save(pluginConfig);
            } catch (IOException e) {
                Bukkit.getLogger().info(e.getMessage());
            }
        }
    }
}