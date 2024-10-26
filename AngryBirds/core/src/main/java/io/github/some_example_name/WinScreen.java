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
    private Texture menuBtnTexture;
    private Texture replayBtnTexture;
    private Texture nextBtnTexture;
    private int level;

    public WinScreen(MainGame game) {
        this.game = game;
        viewport = new FitViewport(1920, 1080);
        stage = new Stage(viewport);

        loseTexture = new Texture(Gdx.files.internal("ui/passlevel.png"));

        Image loseImg = new Image(loseTexture);
        loseImg.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        stage.addActor(loseImg);

        menuBtnTexture = new Texture(Gdx.files.internal("ui/menu.png"));
        replayBtnTexture = new Texture(Gdx.files.internal("ui/replay.png"));
        nextBtnTexture = new Texture(Gdx.files.internal("ui/next.png"));

        ImageButton menuBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(menuBtnTexture)));
        ImageButton replayBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(replayBtnTexture)));
        ImageButton nextBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(nextBtnTexture)));

        setButtonProperties(menuBtn, 650, 150, 190, 180);
        setButtonProperties(replayBtn, 860, 130, 200, 200);
        setButtonProperties(nextBtn, 1085, 150, 190, 180);

        menuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        replayBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayGameScreen(game, level));
            }
        });

        nextBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });


        stage.addActor(menuBtn);
        stage.addActor(replayBtn);
        stage.addActor(nextBtn);

        Gdx.input.setInputProcessor(stage);
    }


    private void setButtonProperties(ImageButton button, float x, float y, float width, float height) {
        button.setSize(width, height);
        button.setPosition(x, y);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        menuBtnTexture.dispose();
        replayBtnTexture.dispose();
        nextBtnTexture.dispose();
    }
}
