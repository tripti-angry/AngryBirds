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
    private Texture bgTexture;
    private OrthographicCamera cam;
    private Viewport viewport;
    private Stage stage;
    private Texture settingsTexture;

    public MenuScreen(MainGame game) {
        this.game = game;
        bgTexture = new Texture(Gdx.files.internal("angry-birds/background.png"));
        settingsTexture = new Texture(Gdx.files.internal("ui/settings.png"));
        cam = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, cam);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);


        Texture playBtnTexture = new Texture(Gdx.files.internal("ui/play.png"));
        TextureRegionDrawable playButtonDrawable = new TextureRegionDrawable(new TextureRegion(playBtnTexture));

        ImageButton playBtn = new ImageButton(playButtonDrawable);
        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        Texture exitBtnTexture = new Texture(Gdx.files.internal("ui/exit.png"));
        TextureRegionDrawable exitButtonDrawable = new TextureRegionDrawable(new TextureRegion(exitBtnTexture));

        ImageButton exitBtn = new ImageButton(exitButtonDrawable);
        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Table table = new Table();
        table.setFillParent(true);

        float buttonWidth = viewport.getWorldWidth() * 0.6f;
        float buttonHeight = viewport.getWorldHeight() * 0.3f;

        table.add(playBtn).size(buttonWidth, buttonHeight).center().padBottom(20);

        stage.addActor(table);

        Table exitTable = new Table();
        exitTable.top().right().padTop(20).padRight(20);
        exitTable.setFillParent(true);

        exitTable.add(exitBtn).size(100, 100);

        stage.addActor(exitTable);

        ImageButton settingsBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(settingsTexture)));
        settingsBtn.setSize(100, 100);
        settingsBtn.setPosition(20, viewport.getWorldHeight() - 120);
        settingsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // when settings button will be clicked
            }
        });
        stage.addActor(settingsBtn);
    }

    @Override
    public void render(float delta) {
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
        settingsTexture.dispose();
    }
}
