package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class MainGame extends Game {

    public SpriteBatch batch;
    private HomeScreen homeScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Create the HomeScreen instance and set it as the initial screen
        homeScreen = new HomeScreen(this);
        this.setScreen(homeScreen);
    }

    @Override
    public void render() {
        super.render();  // Delegates to the active screen's render method
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height); // This will call resize on the current screen
    }

    @Override
    public void dispose() {
        // Dispose of the SpriteBatch and the HomeScreen
        if (homeScreen != null) {
            homeScreen.dispose();
        }
        batch.dispose();
    }
}
