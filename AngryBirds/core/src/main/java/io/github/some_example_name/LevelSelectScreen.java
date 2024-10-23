package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LevelSelectScreen implements Screen {

    private MainGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Texture backgroundTexture;

    public LevelSelectScreen(MainGame game) {
        this.game = game;

        // Load the background image (level selection screen)
        backgroundTexture = new Texture(Gdx.files.internal("ui/levelpage.png"));  // Replace with actual file path

        // Create the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);

        // Initialize the stage for UI
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        // Add the level 1 button with adjusted size and position
        addLevelButton(216, 720, 300, 300, 1);  // Adjust the x, y, width, height values as per your layout
        // You can add other level buttons here similarly...

        addBackButton(-27, -22, 250, 250);
    }

    private void addLevelButton(float x, float y, float width, float height, final int level) {
        // Load the texture for the button (e.g., level 1 button)
        Texture buttonTexture = new Texture(Gdx.files.internal("ui/level1.png"));  // Replace with actual file path

        // Create an ImageButton with the actual texture
        ImageButton button = new ImageButton(new TextureRegionDrawable(new TextureRegion(buttonTexture)));

        // Adjust the size and position based on your background layout
        button.setBounds(x, y, width, height);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to PlayGameScreen when level is clicked
                game.setScreen(new PlayGameScreen(game, level));  // Pass the level number
            }
        });

        // Add the button to the stage
        stage.addActor(button);
    }

    private void addBackButton(float x, float y, float width, float height) {
        // Load the back button texture
        Texture backButtonTexture = new Texture(Gdx.files.internal("ui/backbutton.png"));  // Replace with actual file path

        // Create an ImageButton for the back button
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(backButtonTexture)));
        backButton.setBounds(x, y, width, height);

        // Add a ClickListener to go back to the MenuScreen
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to MenuScreen when back button is clicked
                game.setScreen(new MenuScreen(game));  // Replace MenuScreen with your actual menu screen class
            }
        });

        // Add the back button to the stage
        stage.addActor(backButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);  // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Draw background
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();

        // Draw UI elements (buttons)
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
}
