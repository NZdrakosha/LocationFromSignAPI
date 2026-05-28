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

    public static File pluginConfig;

    public void init (JavaPlugin plugin) {
        pluginConfig = new File(plugin.getDataFolder().getPath() + "/LocationInSing", "mapsConfig.yml");
        createYMLFile(plugin);
        YmlUtil.loadConfig();
        Bukkit.getPluginManager().registerEvents(new SignListener(), plugin);
    }
    public void createYMLFile(Plugin plugin){
        if (!pluginConfig.exists()) {
            try {
                plugin.getConfig().save(pluginConfig);
            } catch (IOException e) {
                Bukkit.getLogger().info(e.getMessage());
            }
        }
    }
}