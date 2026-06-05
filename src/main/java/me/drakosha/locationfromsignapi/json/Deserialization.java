package me.drakosha.locationfromsignapi.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.drakosha.locationfromsignapi.LocationFromSignAPI;
import me.drakosha.locationfromsignapi.location.CustomLocationFormat;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Deserialization {
    public void writeJsonData(String path, CustomLocationFormat locationFormat){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(LocationFromSignAPI.getInstance().getDataFolder(), "LocationFromSign.json");
        JsonObject alreadyExistFile = null;

        if (!file.getParentFile().exists()){
            System.out.println(file.getParentFile().mkdirs());
        }
        if (file.exists()){
            try(FileReader reader = new FileReader(file)){
                alreadyExistFile = gson.fromJson(reader, JsonObject.class);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (alreadyExistFile == null){
            alreadyExistFile = new JsonObject();
        }

        String[] paths = path.split("\\.");
        JsonObject current = alreadyExistFile;

        for (int i = 0; i < paths.length; i++){
            String s = paths[i];
            if (current.has(s) && current.get(s).isJsonObject()){
                current = current.getAsJsonObject(s);
            }else {
                JsonObject next = new JsonObject();
                current.add(s, next);
                current = next;
            }
        }

        String lastKey = paths[paths.length- 1];
        current.add(lastKey, gson.toJsonTree(locationFormat));


        try (FileWriter writer = new FileWriter(file)){
            gson.toJson(alreadyExistFile, writer);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
