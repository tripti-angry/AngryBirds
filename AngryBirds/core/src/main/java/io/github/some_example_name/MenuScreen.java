package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {

    private MainGame game;
    private Texture backgroundTexture;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Texture settingsTexture; // Texture for settings button

    public MenuScreen(MainGame game) {
        this.game = game;

        // Load the background image
        backgroundTexture = new Texture(Gdx.files.internal("angry-birds/background.png"));

        // Load the settings button image
        settingsTexture = new Texture(Gdx.files.internal("ui/settings.png"));

        // Create the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);

        // Initialize the stage for UI
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage); // Set the stage as the input processor

        // Load the play button image
        Texture playButtonTexture = new Texture(Gdx.files.internal("ui/play.png"));
        TextureRegionDrawable playButtonDrawable = new TextureRegionDrawable(new TextureRegion(playButtonTexture));

        // Create the ImageButton using the texture
        ImageButton playButton = new ImageButton(playButtonDrawable);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game)); // Switch to the GameScreen
            }
        });

        // Load the exit button image
        Texture exitButtonTexture = new Texture(Gdx.files.internal("ui/exit.png"));
        TextureRegionDrawable exitButtonDrawable = new TextureRegionDrawable(new TextureRegion(exitButtonTexture));

        // Create the ImageButton using the texture for exit
        ImageButton exitButton = new ImageButton(exitButtonDrawable);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Exit the application
            }
        });

        // Create a table to arrange the buttons and center them
        Table table = new Table();
        table.setFillParent(true); // Make the table fill the parent (stage)

        // Define the sizes of the buttons based on viewport
        float buttonWidth = viewport.getWorldWidth() * 0.6f;  // 60% of the viewport width
        float buttonHeight = viewport.getWorldHeight() * 0.3f; // 30% of the viewport height

        // Add the play button with the defined size to the table
        table.add(playButton).size(buttonWidth, buttonHeight).center().padBottom(20); // Center with bottom padding

        // Add the table to the stage
        stage.addActor(table);

        // Create a new table for the exit button
        Table exitTable = new Table();
        exitTable.top().right().padTop(20).padRight(20); // Position at top-right with padding
        exitTable.setFillParent(true); // Fill the parent (stage)

        // Add the exit button to the exit table
        exitTable.add(exitButton).size(100, 100); // Define size for exit button

        // Add the exit table to the stage
        stage.addActor(exitTable);

        // Add the settings button
        ImageButton settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(settingsTexture)));
        settingsButton.setSize(100, 100);
        settingsButton.setPosition(20, viewport.getWorldHeight() - 120); // Adjust position as needed
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Define what happens when the settings button is clicked
            }
        });
        stage.addActor(settingsButton); // Add the settings button to the stage
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);  // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set the camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Start drawing the background
        game.batch.begin();
        // Fill the entire viewport with the background texture
        game.batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();

        // Draw the UI (stage with buttons)
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
        settingsTexture.dispose(); // Dispose of settings texture
    }
}
