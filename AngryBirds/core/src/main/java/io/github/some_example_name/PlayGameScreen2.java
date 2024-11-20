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

public class PlayGameScreen2 implements Screen {

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

    public  PlayGameScreen2(MainGame game, int level) {
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
        pigs.add(new LargePig(1550, 195, world));
        pigs.add(new SmallPig(1710, 340, world));
        pigs.add(new SmallPig(1432, 340, world));


        // Initialize structures
        structures = new Array<>();
        structures.add(new SteelStructure(1500,170,100,"rectangle",world) );
        structures.add(new SteelStructure(1435,170,100,"rectangle",world) );

        structures.add(new SteelStructure(1700,170,100,"rectangle",world) );
        structures.add(new SteelStructure(1765,170,100,"rectangle",world) );

        structures.add(new SteelStructure(1455,290,100,"vertical-rectangle",world) );
        structures.add(new SteelStructure(1730,290,100,"vertical-rectangle",world) );


        structures.add(new GlassStructure(1455, 440, 100, "thick-rectangle-block", world));
        structures.add(new GlassStructure(1730, 440, 100, "thick-rectangle-block", world));

        structures.add(new GlassStructure(1455, 490, 100, "vertical-rectangle", world));
        structures.add(new GlassStructure(1730, 490, 100, "vertical-rectangle", world));

        structures.add(new WoodenStructure(1440, 207, 100, "rectangle", world) );
        structures.add(new WoodenStructure(1495, 207, 100, "rectangle-verysmall", world) );

        structures.add(new WoodenStructure(1710, 207, 100, "rectangle", world) );
        structures.add(new WoodenStructure(1765, 207, 100, "rectangle-verysmall", world) );

        structures.add(new WoodenStructure(1593, 195, 100, "rectangle-kindalong", world) );

        structures.add(new WoodenStructure(1477, 240, 100, "rectangle", world) );
        structures.add(new WoodenStructure(1425, 240, 100, "rectangle-verysmall", world) );

        structures.add(new WoodenStructure(1750, 240, 100, "rectangle", world) );
        structures.add(new WoodenStructure(1700, 240, 100, "rectangle-verysmall", world) );

        structures.add(new WoodenStructure(1700, 290, 100, "vertical-rectangle", world) );
        structures.add(new WoodenStructure(1765, 290, 100, "vertical-rectangle", world) );
        structures.add(new WoodenStructure(1490, 290, 100, "vertical-rectangle", world) );
        structures.add(new WoodenStructure(1425, 290, 100, "vertical-rectangle", world) );

        structures.add(new WoodenStructure(1455, 335, 100, "horizontal-rectangle-kindasmall", world) );
        structures.add(new WoodenStructure(1730, 335, 100, "horizontal-rectangle-kindasmall", world) );

        structures.add(new WoodenStructure(1685, 372, 100, "vertical-rectangle", world) );
        structures.add(new WoodenStructure(1410, 372, 100, "vertical-rectangle", world) );
        structures.add(new WoodenStructure(1780, 372, 100, "vertical-rectangle", world) );
        structures.add(new WoodenStructure(1495, 372, 100, "vertical-rectangle", world) );

        structures.add(new WoodenStructure(1455, 416, 100, "horizontal-rectangle-kindasmall", world) );
        structures.add(new WoodenStructure(1730, 416, 100, "horizontal-rectangle-kindasmall", world) );


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

        // Sort structures by their Y position (lowest Y will be drawn first)
        structures.sort((a, b) -> Float.compare(a.getY(), b.getY())); // Sort structures by Y position

        // Draw game entities
        game.batch.begin();
        drawGameElements();
        game.batch.end();

//         Optionally render Box2D debug information
//         debugRenderer.render(world, camera.combined);
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
    private void handleInput() {
        if (Gdx.input.justTouched()) {
            // Convert screen coordinates to world coordinates
            Vector2 touchPosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            for (Bird bird : birds) {
                // Check if the touch is within the bird's bounding box
                if (bird.isTouched(touchPosition)) {
                    // Place the bird on the catapult
                    currentBird = bird;
                    bird.setPosition(catapult.getX(), catapult.getY());
                    break; // Stop checking other birds
                }
            }
        }
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
