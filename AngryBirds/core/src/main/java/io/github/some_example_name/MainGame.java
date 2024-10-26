package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game {

    public SpriteBatch batch;
    private HomeScreen homeScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        homeScreen = new HomeScreen(this);
        this.setScreen(homeScreen);
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
    }
}
