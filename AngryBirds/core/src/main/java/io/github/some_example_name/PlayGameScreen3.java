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


public class PlayGameScreen3 implements Screen {

    private final MainGame game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private World world; // Box2D world for physics
    private Box2DDebugRenderer debugRenderer; // For visualizing physics (optional)

    private Array<Bird> birds; // Birds to launch
    private Array<Pig> pigs; // Pigs to destroy
    private Array<Structure> structures; // Game structures
    private CatapultController catapultController;

    private int level; // Current level

    private Bird currentBird; // The bird being launched

    private Texture groundTexture; // Texture for the ground
    private Body groundBody; // Box2D body for the ground
    private Texture angryTexture;

    // Add the Catapult instance
    private Catapult catapult;

    public  PlayGameScreen3(MainGame game, int level) {
        this.game = game;
        this.level = level;
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(1920, 1080, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // Initialize Box2D world (gravity)
        world = new World(new Vector2(0, -100f), true);
        catapultController = new CatapultController(catapult);


        // Debug renderer to visualize physics (optional)
        debugRenderer = new Box2DDebugRenderer();

        // Initialize game elements
        initializeGameElements();
    }

    private void initializeGameElements() {

        angryTexture = new Texture("angry-birds/angry.png");
        // Initialize birds
        birds = new Array<>();
        birds.add(new RedBird(400, 300, world));
        birds.add(new RedBird(300, 300, world));
        birds.add(new RedBird(200, 300, world));

        // Initialize pigs
        pigs = new Array<>();
        pigs.add(new MediumPig(1580, 455, world));
        pigs.add(new MediumPig(1482, 153, world));
        pigs.add(new MediumPig(1670, 153, world));
        pigs.add(new SmallPig(1520, 345, world));
        pigs.add(new SmallPig(1620, 345, world));


        // Initialize structures
        structures = new Array<>();
        structures.add(new SteelStructure(1390,205,100,"rectangle",world) );
        structures.add(new SteelStructure(1390,170,100,"rectangle",world) );
        structures.add(new SteelStructure(1390,240,100,"rectangle",world) );
        structures.add(new SteelStructure(1390,275,100,"rectangle",world) );

        structures.add(new SteelStructure(1800,170,100,"rectangle",world) );
        structures.add(new SteelStructure(1800,205,100,"rectangle",world) );
        structures.add(new SteelStructure(1800,240,100,"rectangle",world) );
        structures.add(new SteelStructure(1800,275,100,"rectangle",world) );

        structures.add(new SteelStructure(1555,225,100,"vertical-rectangle-long",world) );
        structures.add(new SteelStructure(1645,225,100,"vertical-rectangle-long",world) );

        structures.add(new SteelStructure(1460,340,100,"rectangle",world) );
        structures.add(new SteelStructure(1750,340,100,"rectangle",world) );

        structures.add(new SteelStructure(1597,373,100,"triangle",world) );




        structures.add(new GlassStructure(1538, 430, 100, "small-square-block", world));
        structures.add(new GlassStructure(1566, 430, 100, "small-square-block", world));
        structures.add(new GlassStructure(1595, 430, 100, "small-square-block", world));
        structures.add(new GlassStructure(1622, 430, 100, "small-square-block", world));
        structures.add(new GlassStructure(1649, 430, 100, "small-square-block", world));
        structures.add(new GlassStructure(1675, 430, 100, "small-square-block", world));

        structures.add(new GlassStructure(1538, 447, 100, "small-square-block", world));
        structures.add(new GlassStructure(1566, 447, 100, "small-square-block", world));
        structures.add(new GlassStructure(1595, 447, 100, "small-square-block", world));
        structures.add(new GlassStructure(1622, 447, 100, "small-square-block", world));
        structures.add(new GlassStructure(1649, 447, 100, "small-square-block", world));
        structures.add(new GlassStructure(1675, 447, 100, "small-square-block", world));




        structures.add(new WoodenStructure(1597, 300, 100, "rectangle-kindalong", world) );
        structures.add(new WoodenStructure(1430, 300, 100, "rectangle-kindalong", world) );
        structures.add(new WoodenStructure(1485, 320, 100, "rectangle-kindalong", world) );
        structures.add(new WoodenStructure(1600, 340, 100, "rectangle-kindalong", world) );

        structures.add(new WoodenStructure(1770, 300, 100, "rectangle-kindalong", world) );
        structures.add(new WoodenStructure(1690, 320, 100, "rectangle-kindalong", world) );

        structures.add(new WoodenStructure(1690, 360, 100, "vertical-rectangle", world) );
        structures.add(new WoodenStructure(1515, 360, 100, "vertical-rectangle", world) );

        structures.add(new WoodenStructure(1600, 415, 100, "rectangle-kindalong", world) );





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

        if (currentBird != null) {
            // If the current bird is selected, position it correctly on the catapult
            game.batch.draw(angryTexture,
                currentBird.getX() - angryTexture.getWidth() / 2,
                currentBird.getY() - angryTexture.getHeight() / 2);
        }

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
            Vector2 touchPos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            if (currentBird != null) {
                currentBird.setPosition(touchPos.x, touchPos.y);
            } else {
                for (Bird bird : birds) {
                    if (bird.isTouched(touchPos)) {
                        currentBird = bird;
                        birds.removeValue(bird, true);
                        currentBird.getBody().setType(BodyDef.BodyType.KinematicBody);
                        break;
                    }
                }
            }
        } else if (currentBird != null) {
            Vector2 launchForce = new Vector2(catapult.getX(), catapult.getY()).sub(currentBird.getPosition()).scl(10);
            currentBird.getBody().setType(BodyDef.BodyType.DynamicBody);
            currentBird.launch(launchForce);
            currentBird = null;
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
        world.dispose();
        debugRenderer.dispose();
        groundTexture.dispose();
        for (Bird bird : birds) bird.dispose();
        for (Pig pig : pigs) pig.dispose();
        for (Structure structure : structures) structure.dispose();
    }
}
