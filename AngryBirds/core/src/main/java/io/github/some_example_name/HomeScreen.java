package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HomeScreen implements Screen {

    private MainGame game;
    private Texture homeScreenImg;
    private OrthographicCamera cam;
    private Viewport viewport;

    public HomeScreen(MainGame game) {
        this.game = game;
        homeScreenImg = new Texture(Gdx.files.internal("angry-birds/home_page.png"));
        cam = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, cam);
        viewport.apply();
    }

    @Override
    public void show() {
        Gdx.app.postRunnable(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            game.setScreen(new MenuScreen(game));
        });
    }

    @Override
    public void render(float timeElapsed) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        game.batch.draw(homeScreenImg, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height);
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
        cam.update();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        homeScreenImg.dispose();
    }
}
