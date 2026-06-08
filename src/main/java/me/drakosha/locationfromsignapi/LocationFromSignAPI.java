package me.drakosha.locationfromsignapi;

import lombok.Getter;
import me.drakosha.locationfromsignapi.signlistener.SignListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class LocationFromSignAPI extends JavaPlugin {
    @Getter
    public static Plugin instance;
    @Getter
    private static File jsonFile;

    public static void init (Plugin plugin) {
        instance = plugin;
        jsonFile = new File(instance.getDataFolder() + "/locations", "LocationFromSign.json");

        if (!jsonFile.exists()) {
            jsonFile.mkdir();
        }
        Bukkit.getPluginManager().registerEvents(new SignListener(), instance);
    }
}