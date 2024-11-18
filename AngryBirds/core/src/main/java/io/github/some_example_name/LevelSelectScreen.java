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
    private OrthographicCamera cam;
    private Viewport viewport;
    private Stage stage;
    private Texture bgTexture;

    public LevelSelectScreen(MainGame game) {
        this.game = game;

        bgTexture = new Texture(Gdx.files.internal("ui/lf.png.png"));

        cam = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, cam);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        addLevelButton(110, 745, 300, 300, 1);
        addLevelButton(450, 745, 300, 300, 2);
        addLevelButton(820, 745, 300, 300, 3);
        addBackButton(-27, -22, 250, 250);
    }


    private void addLevelButton(float x, float y, float width, float height, final int level) {
        // Select appropriate texture based on the level
        String texturePath = "ui/level" + level + ".png";
        Texture btnTexture = new Texture(Gdx.files.internal(texturePath));
        ImageButton button = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnTexture)));

        button.setBounds(x, y, width, height);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (level == 1) {
                    game.setScreen(new PlayGameScreen1(game, level));
                } else if (level == 2) {
                    game.setScreen(new PlayGameScreen2(game, level));
                } else if (level == 3) {
                    game.setScreen(new PlayGameScreen3(game, level));
                }
            }
        });

        stage.addActor(button);
    }

    private void addBackButton(float x, float y, float width, float height) {
        Texture backBtnTexture = new Texture(Gdx.files.internal("ui/backbutton.png"));

        ImageButton backBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(backBtnTexture)));
        backBtn.setBounds(x, y, width, height);

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        stage.addActor(backBtn);
    }

    @Override
    public void render(float timeElapsed) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        game.batch.draw(bgTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();

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
        bgTexture.dispose();
    }
}
