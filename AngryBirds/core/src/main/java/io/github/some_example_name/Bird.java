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

    protected Body body;  // Box2D body for physics interactions
    protected float x, y; // Position of the bird
    protected String texturePath;
    protected int damage; // Damage value for the bird
    protected Texture texture; // Texture for rendering

    public Bird(String texturePath, float x, float y, int width, int height, int damage, World world) {
        this.texturePath = texturePath;
        this.x = x;
        this.y = y;
        this.damage = damage;

        // Load the texture
        this.texture = new Texture(texturePath);

        // Initialize the Box2D body and other components
        createBody(x, y, width, height, world);
    }

    private void createBody(float x, float y, int width, int height, World world) {
        // Body definition for physics interactions
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y); // Set position to the bird's starting position

        // Create the body in the Box2D world
        body = world.createBody(bodyDef);

        // Create the shape of the bird (using a circle for simplicity)
        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2); // Set radius to half of width

        // Define the fixture for collision detection
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Standard density for physics objects
        fixtureDef.friction = 0.5f; // Some friction
        fixtureDef.restitution = 0.5f; // Some restitution (bounciness)

        // Attach the shape (fixture) to the body
        body.createFixture(fixtureDef);

        // Dispose of shape after use (since it's no longer needed after being attached to the body)
        shape.dispose();
    }
    public Vector2 getPosition() {
        return body.getPosition(); // Return the position of the bird's physics body
    }

    // This method will be overridden in the subclasses
    public abstract void launch(Vector2 force);

    // The render method that can be overridden by subclasses
    public void render(SpriteBatch batch) {
        if (texture != null) {
            // Update position based on Box2D body, and render the bird at that position
            Vector2 bodyPosition = body.getPosition();
            batch.draw(texture, bodyPosition.x - texture.getWidth() / 2, bodyPosition.y - texture.getHeight() / 2);
        }
    }

    // The update method that can be overridden by subclasses
    public void update(float deltaTime) {
        // Logic to update bird (e.g., checking for conditions, animation)
        // This can be overridden by subclasses to add more specific behaviors
    }

    public void dispose() {
        // Clean up resources (dispose of textures, etc.)
        if (texture != null) {
            texture.dispose();
        }
    }
}
