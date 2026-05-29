package me.drakosha.locationfromsignapi.ymlutil;

import me.drakosha.locationfromsignapi.LocationFromSignAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;


public final class YmlUtil {
    private static YamlConfiguration config = YamlConfiguration.loadConfiguration(LocationFromSignAPI.getPluginConfig());

    private YmlUtil(){
    }

    public static void writeDataConfig(String path, Location location){
        if (config.get(path) == null && path.length() > 6) {
            location.setWorld(Bukkit.getWorlds().get(0));
            config.set(path, location);
            saveConfig();
        }
    }
    public static void removeDataConfig(String path){
        if (config.get(path) != null) {
            config.set(path, null);

            String[] s = path.split("\\.");
            String currentPath = path;


            for (int i = s.length - 1; i > 0; i--) {
                int lastDot = currentPath.lastIndexOf(".");
                if (lastDot == -1) break;

                currentPath = currentPath.substring(0, lastDot);

                org.bukkit.configuration.ConfigurationSection section = config.getConfigurationSection(currentPath);
                if (section != null && section.getKeys(false).isEmpty()) {
                    config.set(currentPath, null);
                } else {
                    break;
                }
            }
            saveConfig();
        }
    }
    public static String getPathToArraySign(String[] lines){
        if (lines == null) return "";
        StringBuilder path = new StringBuilder();
        for (String s : lines){
            if (s.isEmpty()) break;
            if (s.equals("point")){
                path.append(s).append(".");
                continue;
            }
            path.append(s);
        }
        return path.toString();
    }
    public static Location getLocation(String pathFronSing, World world){
        String path = "point." + pathFronSing;
        if (pathFronSing != null){
            Location location = config.getSerializable(path, Location.class);
            if (location != null) {
                location.setWorld(world);
            }
            return location;
        }
        return null;
    }
    private static void saveConfig(){
        try {
            config.save(LocationFromSignAPI.getPluginConfig());
        }catch (IOException e){
            Bukkit.getLogger().info(e.getMessage());
        }
    }
}