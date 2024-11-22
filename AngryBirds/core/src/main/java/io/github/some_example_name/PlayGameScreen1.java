package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input;

public class PlayGameScreen1 implements Screen {

    private final MainGame game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private World world; // Box2D world for physics
    private Box2DDebugRenderer debugRenderer; // For visualizing physics (optional)

    private Array<Bird> birds; // Birds to launch
    private Array<Pig> pigs; // Pigs to destroy
    private Array<Structure> structures; // Game structures
    private int level; // Current level

    private Bird currentBird; // The bird being launched

    private Texture groundTexture; // Texture for the ground
    private Body groundBody; // Box2D body for the ground

    // Add the Catapult instance
    private Catapult catapult;

    // Buttons
    private TextButton replayButton;
    private TextButton pauseButton;
    private Stage stage; // For UI buttons

    private Skin skin; // UI Skin
    private Label scoreLabel;
    private int score;

    public PlayGameScreen1(MainGame game, int level) {
        this.game = game;
        this.level = level;
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(1920, 1080, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // Initialize Box2D world (gravity)
        world = new World(new Vector2(0, -100f), true);

        // Debug renderer to visualize physics (optional)
        debugRenderer = new Box2DDebugRenderer();

        // Initialize game elements
        initializeGameElements();

        score=0;

        // Initialize stage for UI elements
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        // Initialize the skin (UI assets)
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Initialize buttons
        initializeButtons();

        // Create score label
        scoreLabel = new Label("Score: 0", skin);
        scoreLabel.setPosition(viewport.getWorldWidth() - 200, viewport.getWorldHeight() - 50);

        // Create score button
        TextButton scoreButton = new TextButton("Score: " + score, skin);
        scoreButton.setPosition(viewport.getWorldWidth() - 200, viewport.getWorldHeight() - 100);
        scoreButton.setSize(100, 50);

        // Add listener to the button (optional action)
        scoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Optional: any specific action on button press
            }
        });

        // Add button and label to the stage
        stage.addActor(scoreLabel);
        stage.addActor(scoreButton);
    }

    private void initializeGameElements() {
        // Initialize birds
        birds = new Array<>();
        birds.add(new RedBird(400, 300, world));
        birds.add(new RedBird(300, 300, world));
        birds.add(new RedBird(200, 300, world));

        // Initialize pigs
        pigs = new Array<>();

        // Initialize structures
        structures = new Array<>();

        // Base Layer - Ground structures
        structures.add(new WoodenStructure(1400, 150, 100, "rectangle-kindalong", world));
        structures.add(new WoodenStructure(1560, 150, 100, "rectangle-kindalong", world));
        structures.add(new WoodenStructure(1330, 190, 100, "vertical-rectangle", world));
        structures.add(new WoodenStructure(1635, 190, 100, "vertical-rectangle", world));
        structures.add(new WoodenStructure(1400, 190, 100, "vertical-rectangle", world));
        structures.add(new WoodenStructure(1550, 190, 100, "vertical-rectangle", world));
        structures.add(new WoodenStructure(1400, 250, 100, "vertical-rectangle", world));
        structures.add(new WoodenStructure(1550, 250, 100, "vertical-rectangle", world));
        structures.add(new WoodenStructure(1400, 310, 100, "vertical-rectangle", world));
        structures.add(new WoodenStructure(1550, 310, 100, "vertical-rectangle", world));
        structures.add(new WoodenStructure(1472, 350, 100, "rectangle-kindalong", world));
        pigs.add(new SmallPig(1450, 360, world)); // Pig sitting on top
        structures.add(new WoodenStructure(1440, 390, 100, "vertical-rectangle", world));
        structures.add(new WoodenStructure(1500, 390, 100, "vertical-rectangle", world));

        structures.add(new WoodenStructure(1470, 450, 100, "rectangle", world));
        pigs.add(new SmallPig(1455, 465, world)); // Pig sitting on top
        structures.add(new WoodenStructure(1475, 180, 100, "rectangle", world));
        structures.add(new WoodenStructure(1475, 220, 100, "rectangle", world));

        // Initialize ground texture
        groundTexture = new Texture("angry-birds/ground.png");

        // Create the ground physical body
        createGround();

        // Initialize the catapult
        catapult = new Catapult(350, 42, world); // Set the catapult position and the Box2D world
    }

    private void createGround() {
        // Create the ground body as a static Box2D body at the bottom of the screen
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody; // Static body for the ground
        bodyDef.position.set(viewport.getWorldWidth() / 2, 110); // Position it at the bottom of the screen

        groundBody = world.createBody(bodyDef);

        // Create the ground shape (a rectangle)
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(viewport.getWorldWidth() / 2, 50); // Width is half of the viewport width, height is 50 pixels

        // Create the fixture for the ground body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        fixtureDef.friction = 0.5f; // Add friction to the ground

        // Attach the fixture to the body
        groundBody.createFixture(fixtureDef);

        // Dispose of shape after use
        groundShape.dispose();
    }

    private void initializeButtons() {
        // Replay button
        replayButton = new TextButton("Replay", skin);
        replayButton.setPosition(50, viewport.getWorldHeight() - 100); // Set position in the top-left corner
        replayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restartLevel();
            }
        });

        // Pause button
        pauseButton = new TextButton("Pause", skin);
        pauseButton.setPosition(100, viewport.getWorldHeight() - 150); // Set position below the replay button in the top-left corner
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseGame();
            }
        });

        // Add buttons to the stage
        stage.addActor(replayButton);
        stage.addActor(pauseButton);
    }


    private void restartLevel() {
        // Restart the level by reinitializing the game elements
        initializeGameElements();
    }

    private void pauseGame() {
        // Switch to the Pause Screen (assuming you have a PauseScreen implemented)
        game.setScreen(new PauseScreen(game));
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Step the physics world
        world.step(1 / 60f, 6, 2);

        checkBirdCollisions(currentBird);

        // Draw game entities
        game.batch.begin();
        drawGameElements();
        game.batch.end();

        scoreLabel.setText("Score: " + score);

        // Draw buttons
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        // Optionally render Box2D debug information
//        debugRenderer.render(world, camera.combined);
    }

    private void drawGameElements() {
        // Draw background
        game.batch.draw(new Texture("angry-birds/background.png"), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        game.batch.draw(groundTexture, 0, 0, viewport.getWorldWidth(), 200); // Draw at the bottom, height is 100 pixels

        // Draw catapult
        catapult.render(game.batch);

        // Draw birds
        for (Bird bird : birds) {
            bird.render(game.batch);
        }

        // Draw pigs
        for (Pig pig : pigs) {
            pig.render(game.batch);
        }

        // Draw structures
        for (Structure structure : structures) {
            structure.render(game.batch);
        }
    }

    private void checkBirdCollisions(Bird bird) {
        // Check for collisions between the bird and structures
        for (Structure structure : structures) {
            if (bird.getBody().getFixtureList().get(0).testPoint(structure.getPosition())) {
                // Increment the score when a bird hits a structure
                score += 10; // Or modify the points as needed
            }
        }

        // Check for collisions with pigs
        for (Pig pig : pigs) {
            if (bird.getBody().getFixtureList().get(0).testPoint(pig.getPosition())) {
                // Increment score when a bird hits a pig
                score += 50; // Or modify the points as needed
            }
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    @Override
    public void show() {
        // Choose the first bird as the current bird to be launched
        currentBird = birds.first();
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        // Dispose resources
        world.dispose();
        debugRenderer.dispose();

        for (Bird bird : birds) {
            bird.dispose();
        }
        for (Pig pig : pigs) {
            pig.dispose();
        }
        for (Structure structure : structures) {
            structure.dispose();
        }

        // Dispose the ground texture
        groundTexture.dispose();
    }
}


