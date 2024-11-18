
package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class PlayGameScreen1 implements Screen {

    private MainGame game;
    private OrthographicCamera cam;
    private Viewport viewport;

    private Texture bgTexture;
    private Texture pauseBtnTexture;
    private Texture replayBtnTexture;
    private Texture groundTexture;
    private Texture slingShotTexture;
    private Texture birdTexture;
    private Texture redbirdTexture;
    private Texture scoreTexture;
    private Texture greenBtnTexture;
    private Texture redBtnTexture;
    private Texture longStructTexture;
    private Texture smallStructTexture;
    private TextureRegion smallStructRegion;
    private TextureRegion longStructRegion;
    private Texture glassStructTexture;
    private Texture additionalLongStructTexture;
    private TextureRegion additionalLongStructRegion;
    private Texture pigTexture;


    private int level;

    private static final float BUTTON_SIZE = 64;
    private static final float PADDING = 10;
    private static final float SCORE_WIDTH = 300;
    private static final float SCORE_HEIGHT = 100;



    public PlayGameScreen1(MainGame game, int level) {
        Gdx.app.log("PlayGameScreen", "Initializing PlayGameScreen1 for level " + level);
        this.game = game;
        this.level = level;

        loadTextures();

        cam = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, cam);
        cam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }

    private void loadTextures() {
        bgTexture = new Texture(Gdx.files.internal("angry-birds/background.png"));
        pauseBtnTexture = new Texture(Gdx.files.internal("ui/pause-button.png"));
        replayBtnTexture = new Texture(Gdx.files.internal("ui/replay-button.png"));
        groundTexture = new Texture(Gdx.files.internal("angry-birds/ground.png"));
        slingShotTexture = new Texture(Gdx.files.internal("ui/slingshot2.png"));
        birdTexture = new Texture(Gdx.files.internal("angry-birds/angry.png"));
        redbirdTexture = new Texture(Gdx.files.internal("angry-birds/angry.png"));
        scoreTexture = new Texture(Gdx.files.internal("ui/score.png"));
        greenBtnTexture = new Texture(Gdx.files.internal("ui/green.png"));
        redBtnTexture = new Texture(Gdx.files.internal("ui/red.png"));
        longStructTexture = new Texture(Gdx.files.internal("ui/long-wooden-struct.png"));
        smallStructTexture = new Texture(Gdx.files.internal("ui/small-wooden-struct.png"));
        glassStructTexture = new Texture(Gdx.files.internal("ui/glass-struct.png"));
        additionalLongStructTexture = new Texture(Gdx.files.internal("ui/long-wooden-struct.png"));
        pigTexture = new Texture(Gdx.files.internal("ui/pig-struct.png"));

        additionalLongStructRegion = new TextureRegion(additionalLongStructTexture);
        smallStructRegion = new TextureRegion(smallStructTexture);
        longStructRegion = new TextureRegion(longStructTexture);

    }

    @Override
    public void render(float timeElapsed) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        handleInput();

        game.batch.begin();
        drawGameElements();
        game.batch.end();
    }

    private void drawGameElements() {
        game.batch.draw(bgTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        float pauseBtnX = 10;
        float pauseBtnY = viewport.getWorldHeight() - BUTTON_SIZE - PADDING;
        game.batch.draw(pauseBtnTexture, pauseBtnX, pauseBtnY, BUTTON_SIZE, BUTTON_SIZE);
        game.batch.draw(replayBtnTexture, pauseBtnX + BUTTON_SIZE + PADDING, pauseBtnY, BUTTON_SIZE, BUTTON_SIZE);

        float scoreX = viewport.getWorldWidth() - SCORE_WIDTH - PADDING;
        float scoreY = viewport.getWorldHeight() - SCORE_HEIGHT - PADDING;
        game.batch.draw(scoreTexture, scoreX, scoreY, SCORE_WIDTH, SCORE_HEIGHT);

        float groundHeight = viewport.getWorldHeight() * 0.3f;
        game.batch.draw(groundTexture, 0, 0, viewport.getWorldWidth(), groundHeight);

        float catapultX = 300;
        float catapultHeight = 150;
        float catapultY = groundHeight - 80;
        game.batch.draw(slingShotTexture, catapultX, catapultY, 150, catapultHeight);

        float birdWidth = 50;
        float birdHeight = 50;
        float birdX = catapultX + 50;
        float birdY = catapultY + catapultHeight - birdHeight / 2;
        game.batch.draw(birdTexture, birdX, birdY, birdWidth, birdHeight);

        drawAngryBirds(catapultX, catapultY);


        float longStructWidth = 500;
        float longStructHeight = 30;
        float longStructX = viewport.getWorldWidth() - longStructWidth - 200;
        float longStructY = catapultY;
        game.batch.draw(longStructRegion, longStructX, longStructY, longStructWidth, longStructHeight);

        float additionalLongStructWidth = 300;
        float additionalLongStructHeight = 30;

        float leftAdditionalLongStructX = longStructX - additionalLongStructWidth + 130;
        float leftAdditionalLongStructY = longStructY + (longStructHeight - additionalLongStructHeight) / 2 +288;
        game.batch.draw(additionalLongStructRegion, leftAdditionalLongStructX, leftAdditionalLongStructY, additionalLongStructWidth, additionalLongStructHeight, additionalLongStructWidth, additionalLongStructHeight, 1, 1, 90f);

        float rightAdditionalLongStructX = longStructX - additionalLongStructWidth + 326;
        float rightAdditionalLongStructY = longStructY + (longStructHeight - additionalLongStructHeight) / 2 + 288;
        game.batch.draw(additionalLongStructRegion, rightAdditionalLongStructX, rightAdditionalLongStructY, additionalLongStructWidth, additionalLongStructHeight, additionalLongStructWidth, additionalLongStructHeight, 1, 1, 90f);


        additionalLongStructWidth = 220;
        additionalLongStructHeight = 30;

        leftAdditionalLongStructX = longStructX - additionalLongStructWidth + 170;
        leftAdditionalLongStructY = longStructY + (longStructHeight - additionalLongStructHeight) / 2 +490;
        game.batch.draw(additionalLongStructRegion, leftAdditionalLongStructX, leftAdditionalLongStructY, additionalLongStructWidth, additionalLongStructHeight, additionalLongStructWidth, additionalLongStructHeight, 1, 1, 90f);


        rightAdditionalLongStructX = longStructX - additionalLongStructWidth + 290;
        rightAdditionalLongStructY = longStructY + (longStructHeight - additionalLongStructHeight) / 2 + 490;
        game.batch.draw(additionalLongStructRegion, rightAdditionalLongStructX, rightAdditionalLongStructY, additionalLongStructWidth, additionalLongStructHeight, additionalLongStructWidth, additionalLongStructHeight, 1, 1, 90f);


        float smallStructWidth = 35;
        float smallStructHeight = 120;

        float centerSmallStructX = longStructX + (longStructWidth - smallStructWidth) / 2;
        float centerSmallStructY = longStructY + longStructHeight + 131;

        float increasedHeight = smallStructHeight * 2.3f;
        float increasedWidth = smallStructWidth * 0.7f;

        game.batch.draw(smallStructRegion, centerSmallStructX, centerSmallStructY,
            increasedWidth/2, increasedHeight/ 2,
            increasedWidth, increasedHeight,
            1, 1, 90);

        smallStructWidth = 30;
        smallStructHeight = 55;
        centerSmallStructX = longStructX + (longStructWidth - smallStructWidth) / 2;
        centerSmallStructY = longStructY + longStructHeight + 221;

        increasedHeight = smallStructHeight * 2.3f;
        increasedWidth = smallStructWidth * 0.7f;

        game.batch.draw(smallStructRegion, centerSmallStructX, centerSmallStructY,
            increasedWidth/2, increasedHeight/ 2,
            increasedWidth, increasedHeight,
            1, 1, 90);


        smallStructWidth = 30;
        smallStructHeight = 65;
        centerSmallStructX = longStructX + (longStructWidth - smallStructWidth) / 2;
        centerSmallStructY = longStructY + longStructHeight + 400;

        increasedHeight = smallStructHeight * 2.3f;
        increasedWidth = smallStructWidth * 0.7f;

        game.batch.draw(smallStructRegion, centerSmallStructX, centerSmallStructY,
            increasedWidth/2, increasedHeight/ 2,
            increasedWidth, increasedHeight,
            1, 1, 90);


        smallStructWidth = 30;
        smallStructHeight = 25;
        centerSmallStructX = longStructX + (longStructWidth - smallStructWidth) / 2;
        centerSmallStructY = longStructY + longStructHeight + 475;

        increasedHeight = smallStructHeight * 2.3f;
        increasedWidth = smallStructWidth * 0.7f;

        game.batch.draw(smallStructRegion, centerSmallStructX, centerSmallStructY,
            increasedWidth/2, increasedHeight/ 2,
            increasedWidth, increasedHeight,
            1, 1, 0);


        smallStructWidth = 30;
        smallStructHeight = 70;

        float leftSmallStructX = longStructX + (longStructWidth - smallStructWidth) / 2 - smallStructWidth - 200;
        float leftSmallStructY = longStructY + longStructHeight- 12;

        game.batch.draw(smallStructRegion, leftSmallStructX, leftSmallStructY, smallStructWidth / 2, smallStructHeight / 2,
            smallStructWidth, smallStructHeight, 1, 1, 180);


        float rightSmallStructX = longStructX + (longStructWidth - smallStructWidth) / 2 + 190;
        float rightSmallStructY = leftSmallStructY;


        game.batch.draw(smallStructRegion, rightSmallStructX, rightSmallStructY, smallStructWidth / 2, smallStructHeight / 2,
            smallStructWidth, smallStructHeight, 1, 1, 180);

        centerSmallStructX = longStructX + (longStructWidth - smallStructWidth) / 2;
        centerSmallStructY = longStructY + longStructHeight - 74;

        increasedHeight = smallStructHeight * 2.3f;
        increasedWidth = smallStructWidth * 0.7f;

        game.batch.draw(smallStructRegion, centerSmallStructX, centerSmallStructY,
            increasedWidth/2, increasedHeight/ 2,
            increasedWidth, increasedHeight,
            1, 1, 90);


        float glassWidth = 25;
        float glassHeight = 120;

        float leftGlassX = centerSmallStructX - (glassWidth + 30);
        float leftGlassY = centerSmallStructY + increasedHeight / 2-10;
        game.batch.draw(glassStructTexture, leftGlassX, leftGlassY, glassWidth, glassHeight);

        float rightGlassX = centerSmallStructX + increasedWidth + 25;
        float rightGlassY = leftGlassY;
        game.batch.draw(glassStructTexture, rightGlassX, rightGlassY, glassWidth, glassHeight);


        float centerSmallStructXHigher = longStructX + (longStructWidth - smallStructWidth) / 2;
        float centerSmallStructYHigher= longStructY + longStructHeight - 74 + 50;

        float increasedHeight2 = smallStructHeight * 2.3f;
        float increasedWidth2 = smallStructWidth * 0.7f;

        game.batch.draw(smallStructRegion, centerSmallStructXHigher, centerSmallStructYHigher,
            increasedWidth2 / 2 - 20 , increasedHeight2 / 2 + 20,
            increasedWidth2, increasedHeight2,
            1, 1, 90);


        float pigWidth = 70;
        float pigHeight = 70;

        float pigX = centerSmallStructXHigher + (increasedWidth2 - pigWidth) / 2;
        float pigY = centerSmallStructYHigher + increasedHeight2 - pigHeight / 2 + 187;

        game.batch.draw(pigTexture, pigX, pigY, pigWidth, pigHeight);

        float greenBtnWidth = 64;
        float greenBtnHeight = 64;

        float redBtnWidth = 90;
        float redBtnHeight = 90;

        float greenBtnX = viewport.getWorldWidth() - greenBtnWidth - PADDING;
        float greenBtnY = 20;

        float redBtnX = viewport.getWorldWidth() - redBtnWidth - 2 * PADDING - greenBtnWidth;
        float redBtnY = 5;

        game.batch.draw(greenBtnTexture, greenBtnX, greenBtnY, greenBtnWidth, greenBtnHeight);
        game.batch.draw(redBtnTexture, redBtnX, redBtnY, redBtnWidth, redBtnHeight);
    }

    private void drawAngryBirds(float catapultX, float catapultY) {
        float redbirdSize = 64;
        float redbirdPadding = 20;
        for (int i = 0; i < 3; i++) {
            float redbirdX = catapultX - (redbirdSize + redbirdPadding) * (i + 1);
            float redbirdY = catapultY;
            game.batch.draw(redbirdTexture, redbirdX, redbirdY, redbirdSize, redbirdSize);
        }
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);

            float pauseBtnX = 10;
            float pauseBtnY = viewport.getWorldHeight() - BUTTON_SIZE - PADDING;
            float greenBtnX = viewport.getWorldWidth() - BUTTON_SIZE - PADDING;
            float greenBtnY = PADDING;
            float redBtnX = greenBtnX - BUTTON_SIZE - PADDING;
            float redBtnY = greenBtnY;

            if (isButtonTouched(touchPos, greenBtnX, greenBtnY, BUTTON_SIZE, BUTTON_SIZE)) {
                game.setScreen(new WinScreen(game));
            } else if (isButtonTouched(touchPos, redBtnX, redBtnY, BUTTON_SIZE, BUTTON_SIZE)) {
                game.setScreen(new LoseScreen(game));
            } else if (isButtonTouched(touchPos, pauseBtnX, pauseBtnY, BUTTON_SIZE, BUTTON_SIZE)) {
                game.setScreen(new PauseScreen(game));
            }
        }
    }

    private boolean isButtonTouched(Vector3 touchPos, float btnX, float btnY, float btnWidth, float btnHeight) {
        return touchPos.x > btnX && touchPos.x < btnX + btnWidth &&
            touchPos.y > btnY && touchPos.y < btnY + btnHeight;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        cam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
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
        bgTexture.dispose();
        pauseBtnTexture.dispose();
        replayBtnTexture.dispose();
        groundTexture.dispose();
        slingShotTexture.dispose();
        birdTexture.dispose();
        redbirdTexture.dispose();
        scoreTexture.dispose();
        greenBtnTexture.dispose();
        redBtnTexture.dispose();
        longStructTexture.dispose();
        smallStructTexture.dispose();
        glassStructTexture.dispose();
        additionalLongStructTexture.dispose();
        pigTexture.dispose();
    }
}






