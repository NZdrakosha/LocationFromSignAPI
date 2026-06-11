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
    public static Plugin instance;
    @Getter
    private static File jsonFile;

    public static void init (Plugin plugin) {
        instance = plugin;
        jsonFile = new File(instance.getDataFolder() + "/locations", "LocationFromSign.json");

        try {
            File parent = jsonFile.getParentFile();
            if (parent != null && !parent.exists()){
                parent.mkdirs();
            }
            if (!jsonFile.exists()){
                jsonFile.createNewFile();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        Bukkit.getPluginManager().registerEvents(new SignListener(), instance);
    }
}