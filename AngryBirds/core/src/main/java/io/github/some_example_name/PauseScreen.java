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
    private OrthographicCamera cam;
    private Viewport viewport;
    private Texture lvlTexture;
    private Texture gameOverTexture;
    private Texture contTexture;
    private Stage stage;  //input handling
    private int level;
    public PauseScreen(MainGame game, int level) {
        this.game = game;
        this.level=level;

        lvlTexture = new Texture(Gdx.files.internal("ui/LEVEL 1.png"));
        gameOverTexture = new Texture(Gdx.files.internal("ui/game-over.png"));
        contTexture = new Texture(Gdx.files.internal("ui/continue.png"));

        cam = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, cam);
        cam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        ImageButton gameOverBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(gameOverTexture)));
        gameOverBtn.setSize(300, 150);
        gameOverBtn.setPosition(viewport.getWorldWidth() - 320, 20);

        gameOverBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        stage.addActor(gameOverBtn);

        ImageButton continueBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(contTexture)));
        continueBtn.setSize(300, 150);
        continueBtn.setPosition(20, 30); //

        continueBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayGameScreen(game,level));
            }
        });
        stage.addActor(continueBtn);
    }

    @Override
    public void render(float timeElapsed) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();

        game.batch.draw(lvlTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        game.batch.end();

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
        lvlTexture.dispose();
        gameOverTexture.dispose();
        contTexture.dispose();
        stage.dispose();
    }
}
