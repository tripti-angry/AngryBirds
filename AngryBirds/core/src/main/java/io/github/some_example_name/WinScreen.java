package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class WinScreen implements Screen {

    private MainGame game;
    private Stage stage;
    private Viewport viewport;
    private Texture loseTexture;

    // Button textures
    private Texture menuButtonTexture;
    private Texture replayButtonTexture;
    private Texture nextButtonTexture;
    private int level;

    public WinScreen(MainGame game) {
        this.game = game;
        viewport = new FitViewport(1920, 1080);
        stage = new Stage(viewport);

        // Load "Level Failed" screen texture
        loseTexture = new Texture(Gdx.files.internal("ui/passlevel.png"));

        Image loseImage = new Image(loseTexture);
        loseImage.setSize(viewport.getWorldWidth(), viewport.getWorldHeight()); // Scale to fit screen
        stage.addActor(loseImage);

        // Load button textures
        menuButtonTexture = new Texture(Gdx.files.internal("ui/menu.png"));
        replayButtonTexture = new Texture(Gdx.files.internal("ui/replay.png"));
        nextButtonTexture = new Texture(Gdx.files.internal("ui/next.png"));

        // Create buttons
        ImageButton menuButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(menuButtonTexture)));
        ImageButton replayButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(replayButtonTexture)));
        ImageButton nextButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(nextButtonTexture)));

        // Set button properties using the new helper method
        setButtonProperties(menuButton, 650, 150, 190, 180);
        setButtonProperties(replayButton, 860, 130, 200, 200);
        setButtonProperties(nextButton, 1085, 150, 190, 180);

        // Add listeners to buttons
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game)); // Navigate to MenuScreen
            }
        });

        replayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayGameScreen(game, level)); // Restart level (navigate to PlayGameScreen)
            }
        });

        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game)); // Navigate to SelectLevelScreen
            }
        });

        // Add buttons to stage
        stage.addActor(menuButton);
        stage.addActor(replayButton);
        stage.addActor(nextButton);

        // Set input processor
        Gdx.input.setInputProcessor(stage);
    }

    // Helper method to set button position and size
    private void setButtonProperties(ImageButton button, float x, float y, float width, float height) {
        button.setSize(width, height);
        button.setPosition(x, y);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render the lose screen
        stage.act(Gdx.graphics.getDeltaTime());
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
        loseTexture.dispose();
        menuButtonTexture.dispose();
        replayButtonTexture.dispose();
        nextButtonTexture.dispose();
    }
}
