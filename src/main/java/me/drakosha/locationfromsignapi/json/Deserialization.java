package me.drakosha.locationfromsignapi.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.drakosha.locationfromsignapi.location.CustomLocationFormat;
import org.bukkit.Location;
import org.bukkit.World;

public class Deserialization {

    public static Location getLocation(String path, World world){
        Gson gson = new Gson();
        JsonElement element = JsonUtil.getElementByPath(path);

        CustomLocationFormat customLocationFormat = null;
        if (element != null && element.isJsonObject()) {
            JsonObject jo = element.getAsJsonObject();
            customLocationFormat = gson.fromJson(jo, CustomLocationFormat.class);
        }
        assert customLocationFormat != null;
        double x = customLocationFormat.getX();
        double y = customLocationFormat.getY();
        double z = customLocationFormat.getZ();
        float yaw = customLocationFormat.getYaw();

        Location location = new Location(world, x, y, z);
        location.setYaw(yaw);

        return location;
    }
}
