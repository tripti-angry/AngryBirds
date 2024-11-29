package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class SaveManager {
    private static final String SAVE_FILE = "save_data.json";

  
    public static void saveProgress(SaveData data) {
        Json json = new Json();
        FileHandle file = Gdx.files.local(SAVE_FILE);

      
        String jsonData = json.toJson(data);
        file.writeString(jsonData, false); 

        Gdx.app.log("SaveManager", "Progress saved: " + jsonData);
    }

   
    public static SaveData loadProgress() {
        Json json = new Json();
        FileHandle file = Gdx.files.local(SAVE_FILE);

        if (file.exists()) {
           
            String jsonData = file.readString();
            SaveData data = json.fromJson(SaveData.class, jsonData);
            Gdx.app.log("SaveManager", "Progress loaded: " + jsonData);
            return data;
        } else {
            Gdx.app.log("SaveManager", "No save file found. Starting fresh.");
            return null; 
        }
    }
}
