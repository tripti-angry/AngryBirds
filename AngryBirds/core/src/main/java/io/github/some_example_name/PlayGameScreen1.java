package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Screen;


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
    }

    private void initializeGameElements() {
        // Initialize birds
        birds = new Array<>();
        birds.add(new RedBird(400, 300, world));
        birds.add(new RedBird(300, 300, world));
        birds.add(new RedBird(200, 300, world));

        // Initialize pigs
        pigs = new Array<>();
        pigs.add(new LargePig(900, 300, world));
        pigs.add(new SmallPig(1000, 300, world));

        // Initialize structures
        structures = new Array<>();
        structures.add(new GlassStructure(1500, 165, 100, "rectangle", world));

        WoodenStructure woodenStructure = new WoodenStructure(1500, 200, 100, "rectangle", world);
        woodenStructure.rotate(45);  // Rotating the wooden block by 45 degrees
        structures.add(woodenStructure);

        // Initialize ground texture
        groundTexture = new Texture("angry-birds/ground.png");

        // Create the ground physical body
        createGround();
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

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Step the physics world
        world.step(1 / 60f, 6, 2);

        // Handle input (for launching birds)
        handleInput();

        // Draw game entities
        game.batch.begin();
        drawGameElements();
        game.batch.end();

        // Optionally render Box2D debug information
//        debugRenderer.render(world, camera.combined);
    }

    private void drawGameElements() {
        // Draw background
        game.batch.draw(new Texture("angry-birds/background.png"), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        game.batch.draw(groundTexture, 0, 0, viewport.getWorldWidth(), 200); // Draw at the bottom, height is 100 pixels

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

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            // Get the touch position
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            // Get the position of the bird (where the bird should be launched from)
            Vector2 birdPosition = currentBird.getPosition();

            // Calculate the distance between the bird's position and the touch position
            float deltaX = touchX - birdPosition.x ;
            float deltaY = touchY - birdPosition.y;

            // Scale the force based on the touch distance (control how strong the launch is)
            float forceX = deltaX * 0.5f;  // Horizontal force
            float forceY = deltaY * 0.5f;  // Vertical force, you can adjust this scale factor

            // Apply a maximum vertical force to prevent going too high
            forceY = Math.min(forceY, 1000f);  // Limit the vertical force

            // Apply the calculated force
            Vector2 force = new Vector2(forceX, forceY);
            currentBird.launch(force);

            // Optionally reset the bird after launch
            currentBird = null; // Reset after launch
        }
    }

    private Vector2 calculateLaunchForce() {
        // This method now calculates the launch force dynamically based on touch input
        return new Vector2(1000, 1000); // This method is redundant in this version
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
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
