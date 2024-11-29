package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class SaveManager {
    private static final String SAVE_FILE = "save_data.json"; // Use JSON file extension

    // Save game data to a file in JSON format
    public static void saveProgress(SaveData data) {
        Json json = new Json();
        FileHandle file = Gdx.files.local(SAVE_FILE);

        // Convert SaveData object to JSON string and write to file
        String jsonData = json.toJson(data);
        file.writeString(jsonData, false); // 'false' means overwrite if the file exists

        Gdx.app.log("SaveManager", "Progress saved: " + jsonData);
    }

    // Load game data from a JSON file
    public static SaveData loadProgress() {
        Json json = new Json();
        FileHandle file = Gdx.files.local(SAVE_FILE);

        if (file.exists()) {
            // Read the file content and convert JSON string back to SaveData object
            String jsonData = file.readString();
            SaveData data = json.fromJson(SaveData.class, jsonData);
            Gdx.app.log("SaveManager", "Progress loaded: " + jsonData);
            return data;
        } else {
            Gdx.app.log("SaveManager", "No save file found. Starting fresh.");
            return null; // Return null if no save file exists
        }
    }
}
