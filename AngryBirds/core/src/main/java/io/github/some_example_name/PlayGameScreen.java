package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayGameScreen implements Screen {

    private MainGame game;
    private OrthographicCamera camera;
    private Viewport viewport;

    // Textures
    private Texture backgroundTexture;
    private Texture pauseButtonTexture;
    private Texture replayButtonTexture;
    private Texture groundTexture;
    private Texture catapultTexture;
    private Texture birdTexture;
    private Texture angryTexture;
    private Texture structTexture;
    private Texture scoreTexture;
    private int level;

    // Constants for button size, padding, etc.
    private static final float BUTTON_SIZE = 64;
    private static final float PADDING = 10;
    private static final float SCORE_WIDTH = 300;
    private static final float SCORE_HEIGHT = 100;

    public PlayGameScreen(MainGame game, int level) {
        this.game = game;
        this.level=level;

        // Load textures
        loadTextures();

        // Set up the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }

    private void loadTextures() {
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        pauseButtonTexture = new Texture(Gdx.files.internal("ui/pause-button.png"));
        replayButtonTexture = new Texture(Gdx.files.internal("ui/replay-button.png"));
        groundTexture = new Texture(Gdx.files.internal("angry-birds/ground.png"));
        catapultTexture = new Texture(Gdx.files.internal("ui/slingshot2.png"));
        birdTexture = new Texture(Gdx.files.internal("angry-birds/angry.png"));
        angryTexture = new Texture(Gdx.files.internal("angry-birds/angry.png"));
        structTexture = new Texture(Gdx.files.internal("ui/hello.png"));
        scoreTexture = new Texture(Gdx.files.internal("ui/score.png"));
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Handle input for pause button
        handleInput();

        // Draw the background and game elements
        game.batch.begin();
        drawGameElements();
        game.batch.end();
    }

    private void drawGameElements() {
        game.batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Draw buttons and score
        float pauseButtonX = 10;
        float pauseButtonY = viewport.getWorldHeight() - BUTTON_SIZE - PADDING;
        game.batch.draw(pauseButtonTexture, pauseButtonX, pauseButtonY, BUTTON_SIZE, BUTTON_SIZE);
        game.batch.draw(replayButtonTexture, pauseButtonX + BUTTON_SIZE + PADDING, pauseButtonY, BUTTON_SIZE, BUTTON_SIZE);

        float scoreX = viewport.getWorldWidth() - SCORE_WIDTH - PADDING;
        float scoreY = viewport.getWorldHeight() - SCORE_HEIGHT - PADDING;
        game.batch.draw(scoreTexture, scoreX, scoreY, SCORE_WIDTH, SCORE_HEIGHT);

        // Draw ground, catapult, bird, and structures
        float groundHeight = viewport.getWorldHeight() * 0.3f;
        game.batch.draw(groundTexture, 0, 0, viewport.getWorldWidth(), groundHeight);

        float catapultX = 300;
        float catapultHeight = 150;
        float catapultY = groundHeight - 80;
        game.batch.draw(catapultTexture, catapultX, catapultY, 150, catapultHeight);

        float birdWidth = 50;
        float birdHeight = 50;
        float birdX = catapultX + 50;
        float birdY = catapultY + catapultHeight - birdHeight / 2;
        game.batch.draw(birdTexture, birdX, birdY, birdWidth, birdHeight);

        // Draw angry birds near catapult
        drawAngryBirds(catapultX, catapultY);

        // Draw large structure
        float structWidth = viewport.getWorldWidth() * 0.5f;
        float structHeight = viewport.getWorldHeight() * 0.6f;
        float structX = viewport.getWorldWidth() - structWidth;
        float structY = groundHeight - 80;
        game.batch.draw(structTexture, structX, structY, structWidth, structHeight);
    }

    private void drawAngryBirds(float catapultX, float catapultY) {
        float angrySize = 64;
        float angryPadding = 20;
        for (int i = 0; i < 3; i++) {
            float angryX = catapultX - (angrySize + angryPadding) * (i + 1);
            float angryY = catapultY;
            game.batch.draw(angryTexture, angryX, angryY, angrySize, angrySize);
        }
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);  // Convert touch position to game coordinates

            float pauseButtonX = 10;
            float pauseButtonY = viewport.getWorldHeight() - BUTTON_SIZE - PADDING;

            // Check if the pause button is clicked
            if (isButtonTouched(touchPos, pauseButtonX, pauseButtonY, BUTTON_SIZE, BUTTON_SIZE)) {
                game.setScreen(new PauseScreen(game));  // Switch to the PauseScreen
            }
        }
    }

    private boolean isButtonTouched(Vector3 touchPos, float buttonX, float buttonY, float buttonWidth, float buttonHeight) {
        return touchPos.x > buttonX && touchPos.x < buttonX + buttonWidth &&
            touchPos.y > buttonY && touchPos.y < buttonY + buttonHeight;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);  // Update camera position on resize
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
        backgroundTexture.dispose();
        pauseButtonTexture.dispose();
        replayButtonTexture.dispose();
        groundTexture.dispose();
        catapultTexture.dispose();
        birdTexture.dispose();
        angryTexture.dispose();
        structTexture.dispose();
        scoreTexture.dispose();
    }
}
