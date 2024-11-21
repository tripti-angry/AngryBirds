package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.CircleShape;

public abstract class Bird {

    protected Body body;            // Box2D body for physics interactions
    protected String texturePath;   // Path to the bird's texture file
    protected int damage;           // Damage value for the bird
    protected Texture texture;      // Texture for rendering the bird
    private Vector2 position;       // Logical position of the bird
    private World world;            // The Box2D world the bird belongs to

    /**
     * Constructor to initialize the bird.
     *
     * @param texturePath Path to the bird's texture file.
     * @param x Initial x-position.
     * @param y Initial y-position.
     * @param width Bird's width (used for physics shape).
     * @param height Bird's height (used for physics shape).
     * @param damage Damage value of the bird.
     * @param world The Box2D world where the bird exists.
     */
    public Bird(String texturePath, float x, float y, int width, int height, int damage, World world) {
        this.texturePath = texturePath;
        this.damage = damage;
        this.position = new Vector2(x, y);
        this.world = world;

        // Load the texture
        this.texture = new Texture(texturePath);

        // Initialize the Box2D body
        createBody(x, y, width, height);
    }

    /**
     * Creates the Box2D body for the bird.
     *
     * @param x Initial x-position.
     * @param y Initial y-position.
     * @param width Bird's width (used for physics shape).
     * @param height Bird's height (used for physics shape).
     */
    private void createBody(float x, float y, int width, int height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;  // Dynamic body for physics
        bodyDef.position.set(x, y);                  // Set initial position

        body = world.createBody(bodyDef);            // Create the body in the world

        // Use a circle shape for simplicity
        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2f); // Use half the width as the radius

        // Define the fixture for the body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;      // Standard density for the bird
        fixtureDef.friction = 0.5f;     // Some friction for realistic interactions
        fixtureDef.restitution = 0.5f;  // Some restitution (bounciness)

        // Attach the shape to the body
        body.createFixture(fixtureDef);

        // Dispose of the shape to free memory
        shape.dispose();
    }

    /**
     * Abstract method for launching the bird.
     * Subclasses should define their specific launch behavior.
     *
     * @param force Vector representing the launch force.
     */
    public abstract void launch(Vector2 force);

    /**
     * Updates the bird's logic. Can be overridden by subclasses.
     *
     * @param deltaTime Time since the last update.
     */
    public void update(float deltaTime) {
        // Example: Update logic like animations or status
    }

    /**
     * Renders the bird on the screen.
     *
     * @param batch SpriteBatch used for drawing.
     */
    public void render(SpriteBatch batch) {
        if (texture != null) {
            Vector2 bodyPosition = body.getPosition(); // Get position from Box2D body
            batch.draw(texture,
                bodyPosition.x - texture.getWidth() / 2f,
                bodyPosition.y - texture.getHeight() / 2f);
        }
    }

    public Body getBody() {
        return body;
    }



    /**
     * Returns the bird's current position.
     *
     * @return A Vector2 containing the bird's position.
     */
    public Vector2 getPosition() {
        return body.getPosition();
    }

    /**
     * Returns the bird's X-coordinate.
     *
     * @return X-coordinate of the bird.
     */
    public float getX() {
        return body.getPosition().x;
    }

    /**
     * Returns the bird's Y-coordinate.
     *
     * @return Y-coordinate of the bird.
     */
    public float getY() {
        return body.getPosition().y;
    }

    /**
     * Sets the bird's position.
     *
     * @param x New X-coordinate.
     * @param y New Y-coordinate.
     */
    public void setPosition(float x, float y) {
        position.set(x, y); // Update logical position
        if (body != null) {
            body.setTransform(x, y, body.getAngle()); // Update Box2D body position
        }
    }

    /**
     * Checks if the bird is touched at a given position.
     *
     * @param touchPosition Position of the touch input.
     * @return True if the bird is touched, false otherwise.
     */
    public boolean isTouched(Vector2 touchPosition) {
        float birdWidth = texture.getWidth();
        float birdHeight = texture.getHeight();
        return touchPosition.x >= getX() - birdWidth / 2 &&
            touchPosition.x <= getX() + birdWidth / 2 &&
            touchPosition.y >= getY() - birdHeight / 2 &&
            touchPosition.y <= getY() + birdHeight / 2;
    }

    /**
     * Disposes of resources used by the bird.
     */
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }


}
