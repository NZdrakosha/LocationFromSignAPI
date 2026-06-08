package me.drakosha.locationfromsignapi.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.drakosha.locationfromsignapi.LocationFromSignAPI;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class JsonUtil {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static File file = LocationFromSignAPI.getJsonFile();


    public static JsonElement getElementByPath(String path){
        JsonObject jo = readJsonFile();
        if (path == null || path.isEmpty()){
            return jo;
        }
        String[] paths = path.split("\\.");
        JsonElement element = jo;

        for (String key : paths){
            if (element instanceof JsonObject && ((JsonObject) element).has(key)){
                element = ((JsonObject) element).get(key);
            }else {
                return null;
            }
        }
        return element;
    }
    public static void removeJsonObjectByPath(String path){
        JsonObject jo = readJsonFile();

        if (path == null || path.isEmpty()){
            return;
        }
        String[] paths = path.split("\\.");

        List<JsonObject> objectInPath = new ArrayList<>();
        JsonObject current = jo;
        objectInPath.add(current);

        for (String key : paths) {
            if (current.has(key) && current.get(key).isJsonObject()) {
                current = current.getAsJsonObject(key);
                objectInPath.add(current);
            } else {
                return;
            }
        }
        for (int i = paths.length - 1; i >= 0; i--){
            String key = paths[i];
            JsonObject parent = objectInPath.get(i);
            if (i == paths.length - 1){
                parent.remove(key);
            }else {
                if (parent.has(key) && parent.get(key).isJsonObject()){
                    JsonObject child = parent.getAsJsonObject(key);
                    if (child.size() == 0){
                        parent.remove(key);
                    }

                }
            }
        }
        writeJsonData(jo);
    }


    public static void setJsonObjectByPath(String path, Object object){
        JsonObject alreadyExistFile = readJsonFile();

        String[] paths = path.split("\\.");
        JsonObject current = alreadyExistFile;

        for (int i = 0; i < paths.length - 1; i++){
            String key = paths[i];
            if (current.has(key) && current.get(key).isJsonObject()){
                current = current.getAsJsonObject(key);
            }else {
                JsonObject next = new JsonObject();
                current.add(key, next);
                current = next;
            }
        }

        String lastKey = paths[paths.length - 1];
        current.add(lastKey, gson.toJsonTree(object));

        writeJsonData(alreadyExistFile);
    }

    private static JsonObject readJsonFile(){
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)){
                JsonObject object = gson.fromJson(reader, JsonObject.class);
                return object != null ? object : new JsonObject();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        return new JsonObject();
    }

    private static void writeJsonData(JsonObject jo) {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(jo, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
