package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HomeScreen implements Screen {

    private MainGame game;              // Reference to the main game
    private Texture homeScreenImage;     // Single image for the home screen
    private OrthographicCamera camera;   // Camera to control the view
    private Viewport viewport;           // Viewport to handle resizing

    public HomeScreen(MainGame game) {
        this.game = game;

        // Load the home screen image (replace with your actual image path)
        homeScreenImage = new Texture(Gdx.files.internal("angry-birds/home_page.png"));

        // Create the camera and viewport
        camera = new OrthographicCamera();
        // Create a FitViewport with base width and height for the viewport
        viewport = new FitViewport(1920, 1080, camera); // Set to your desired screen resolution

        // Apply the initial viewport configuration
        viewport.apply();
    }

    @Override
    public void show() {
        // Transition to MenuScreen after a short delay
        Gdx.app.postRunnable(() -> {
            try {
                Thread.sleep(2000); // Delay for 2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game.setScreen(new MenuScreen(game)); // Switch to MenuScreen
        });
    }

    @Override
    public void render(float delta) {
        // Clear the screen with a black background
        Gdx.gl.glClearColor(0, 0, 0, 1);  // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set the camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Start drawing the background
        game.batch.begin();
        // Fill the entire viewport with the background texture
        game.batch.draw(homeScreenImage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();


    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport to adjust to the new window size
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // Dispose of assets when done
        homeScreenImage.dispose();
    }
}
