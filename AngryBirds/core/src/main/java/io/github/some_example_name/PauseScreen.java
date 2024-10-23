package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseScreen implements Screen {

    private MainGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Texture levelTexture;
    private Texture gameOverTexture;
    private Texture continueTexture;
    private Stage stage;
    // Stage for input handling
    private int level;
    public PauseScreen(MainGame game) {
        this.game = game;
        this.level=level;

        // Load the textures
        levelTexture = new Texture(Gdx.files.internal("ui/LEVEL 1.png"));
        gameOverTexture = new Texture(Gdx.files.internal("ui/game-over.png"));
        continueTexture = new Texture(Gdx.files.internal("ui/continue.png"));

        // Set up the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // Initialize stage for input handling
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage); // Set stage as the input processor

        // Create ImageButton for game-over.png
        ImageButton gameOverButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(gameOverTexture)));
        gameOverButton.setSize(300, 150); // Same size as you defined earlier
        gameOverButton.setPosition(viewport.getWorldWidth() - 320, 20); // Position in bottom-right corner

        // Add a click listener to handle click event and switch to MenuScreen
        gameOverButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game)); // Switch to MenuScreen
            }
        });

        // Add gameOverButton to the stage
        stage.addActor(gameOverButton);

        // Create ImageButton for continue.png
        ImageButton continueButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(continueTexture)));
        continueButton.setSize(300, 150); // Same size as you defined for gameOverButton
        continueButton.setPosition(20, 30); // Position in bottom-left corner

        // Add a click listener to handle click event and switch to PlayGameScreen
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayGameScreen(game,level)); // Switch to PlayGameScreen
            }
        });

        // Add continueButton to the stage
        stage.addActor(continueButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Start drawing
        game.batch.begin();

        // Draw the LEVEL 1.png to fill the entire screen (1920x1080)
        game.batch.draw(levelTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        game.batch.end();

        // Draw the UI (stage with continue and game-over buttons)
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        // Dispose of all textures and stage
        levelTexture.dispose();
        gameOverTexture.dispose();
        continueTexture.dispose();
        stage.dispose();
    }
}
