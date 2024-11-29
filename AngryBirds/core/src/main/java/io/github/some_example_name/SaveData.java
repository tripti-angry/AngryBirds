package io.github.some_example_name;

public class SaveData {
    public int currentLevel;
    public int score;

    // No-argument constructor (required for serialization)
    public SaveData() {
    }

    // Constructor to set values
    public SaveData(int currentLevel, int score) {
        this.currentLevel = currentLevel;
        this.score = score;
    }
}
