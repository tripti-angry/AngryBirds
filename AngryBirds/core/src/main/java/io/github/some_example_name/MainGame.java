package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game {

    public SpriteBatch batch;
    private HomeScreen homeScreen;
    private int currentLevel;
    private int score;

    @Override
    public void create() {
        batch = new SpriteBatch();
        homeScreen = new HomeScreen(this);
        this.setScreen(homeScreen);

       
        SoundManager.playMusic();
        SaveData savedData = SaveManager.loadProgress();
        if (savedData != null) {
            currentLevel = savedData.currentLevel;
            score = savedData.score;
        } else {
            currentLevel = 1; 
            score = 0;      
        }
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        if (homeScreen != null) {
            homeScreen.dispose();
        }
        batch.dispose();

       
        SoundManager.stopMusic();
    }
}
