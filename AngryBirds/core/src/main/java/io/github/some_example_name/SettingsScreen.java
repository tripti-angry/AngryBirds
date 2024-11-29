
package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class SettingsScreen implements Screen {

    private final MainGame game;
    private final Stage stage;
    private final Skin skin;
    private final Texture bgTexture;

    public SettingsScreen(MainGame game) {
        this.game = game;

        OrthographicCamera camera = new OrthographicCamera();
        FitViewport viewport = new FitViewport(1920, 1080, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bgTexture = new Texture(Gdx.files.internal("angry-birds/background.png"));

        createSettingsUI();
    }

    private void createSettingsUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();

      
        Label titleLabel = new Label("SETTINGS", skin, "title");
        table.add(titleLabel).padBottom(50);
        table.row();


        final TextButton soundButton = new TextButton("Sound: ON", skin);
        soundButton.addListener(event -> {
            SoundManager.toggleSound();
            soundButton.setText(SoundManager.isSoundOn() ? "Sound: ON" : "Sound: OFF");
            return true;
        });
        table.add(soundButton).size(400, 100).padBottom(30);
        table.row();

        
        final TextButton notificationsButton = new TextButton("Notifications: ON", skin);
        notificationsButton.addListener(event -> {
            
            return true;
        });
        table.add(notificationsButton).size(400, 100).padBottom(30);
        table.row();

        
        TextButton saveLoadButton = new TextButton("Save/Load Progress", skin);
        saveLoadButton.addListener(event -> {
           
            SaveData currentProgress = new SaveData(game.getCurrentLevel(), game.getScore());
            SaveManager.saveProgress(currentProgress);
            Gdx.app.log("SettingsScreen", "Progress saved: Level " + currentProgress.currentLevel + ", Score " + currentProgress.score);

           
            SaveData loadedProgress = SaveManager.loadProgress();
            if (loadedProgress != null) {
                game.setCurrentLevel(loadedProgress.currentLevel);
                game.setScore(loadedProgress.score);
                Gdx.app.log("SettingsScreen", "Progress loaded: Level " + loadedProgress.currentLevel + ", Score " + loadedProgress.score);
            } else {
                Gdx.app.log("SettingsScreen", "No saved progress to load.");
            }

            return true;
        });
        table.add(saveLoadButton).size(400, 100).padBottom(30);
        table.row();

      
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(event -> {
            game.setScreen(new MenuScreen(game));
            return true;
        });
        table.add(backButton).size(400, 100);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(bgTexture, 0, 0, stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
        stage.dispose();
        bgTexture.dispose();
        skin.dispose();
    }
}
