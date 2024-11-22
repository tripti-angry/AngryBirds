//package io.github.some_example_name;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.physics.box2d.Body;
//
//public abstract class Structure {
//    protected Texture texture;
//    protected float x, y;
//    protected float width, height;
//    protected int health; // Health to determine how much damage it can take
//    private Rectangle boundingBox;
//    private Body body;
//
//
//
//    // Constructor that accepts texture region, position (x, y), and health
//    public Structure(TextureRegion textureRegion, float x, float y, int health) {
//        this.texture = textureRegion.getTexture(); // Get the texture from TextureRegion
//        this.x = x;
//        this.y = y;
//        this.width = textureRegion.getRegionWidth(); // Set width based on texture size
//        this.height = textureRegion.getRegionHeight(); // Set height based on texture size
//        this.health = health;
//    }
//
//    // Constructor that only accepts position (x, y) and sets default values for texture and health
//    public Structure(float x, float y) {
//        this.x = x;
//        this.y = y;
//        this.texture = new Texture(Gdx.files.internal("default_texture.png"));  // Default texture
//        this.width = texture.getWidth();
//        this.height = texture.getHeight();
//        this.health = 100; // Default health
//    }
//
//
//    // Method to render the structure
//    public void render(SpriteBatch batch) {
//        if (texture != null) {
//            batch.draw(texture, x - width / 2, y - height / 2, width, height); // Adjust position for center origin
//        }
//    }
//
//    public float getY() {
//        return y;  // Simply return the Y position for structures
//    }
//
//
//    // Abstract method for update, to be implemented by subclasses with specific behavior
//    public abstract void update();
//
//    // Dispose of the texture to free memory when the structure is no longer needed
//    public void dispose() {
//        if (texture != null) {
//            texture.dispose();
//        }
//    }
//
//    public Rectangle getBoundingBox() {
//        return boundingBox;
//    }
//
//    public void takeDamage(int damage) {
//        health -= damage;
//        if (health <= 0) {
//            onDestroyed();
//        }
//    }
//
//    public boolean isDestroyed() {
//        return health <= 0;
//    }
//
//    protected void onDestroyed() {
//        // Custom destruction behavior
//    }
//}
package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Structure {
    protected Texture texture;
    protected float x, y;
    protected float width, height;
    protected int health; // Health to determine how much damage it can take
    private Rectangle boundingBox;
    private Body body;

    // Constructor that accepts texture region, position (x, y), health, and Box2D world
    public Structure(TextureRegion textureRegion, float x, float y, int health, World world) {
        this.texture = textureRegion.getTexture(); // Get the texture from TextureRegion
        this.x = x;
        this.y = y;
        this.width = textureRegion.getRegionWidth(); // Set width based on texture size
        this.height = textureRegion.getRegionHeight(); // Set height based on texture size
        this.health = health;

        // Initialize Box2D body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody; // Set as static body for immovable structures
        bodyDef.position.set(x, y); // Set position
        body = world.createBody(bodyDef); // Create body in the given world

        // Create and set the shape for the structure (e.g., rectangle shape)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2); // Define the shape (half width and height)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef); // Create fixture with shape
        shape.dispose(); // Dispose of shape after fixture is created
    }

    // Constructor that only accepts position (x, y) and sets default values for texture and health
    public Structure(float x, float y, World world) {
        this.x = x;
        this.y = y;
        this.texture = new Texture(Gdx.files.internal("default_texture.png")); // Default texture
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.health = 100; // Default health

        // Initialize Box2D body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        // Create and set the shape for the structure (rectangle shape)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    // Method to render the structure
    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, x - width / 2, y - height / 2, width, height); // Adjust position for center origin
        }
    }

    public float getY() {
        return y;  // Simply return the Y position for structures
    }

    // Abstract method for update, to be implemented by subclasses with specific behavior
    public abstract void update();

    // Dispose of the texture to free memory when the structure is no longer needed
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }

    // Get the bounding box for collision detection (if needed)
    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    // Method to take damage and reduce health
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            onDestroyed();
        }
    }

    // Check if the structure is destroyed (health <= 0)
    public boolean isDestroyed() {
        return health <= 0;
    }

    // Protected method that can be overridden for custom destruction behavior
    protected void onDestroyed() {
        // Custom destruction behavior
    }

    // Method to get the position of the Box2D body (for collision detection or other use)
    public Vector2 getPosition() {
        return body.getPosition();  // Return the position of the Box2D body
    }

    // Optional: Getter for the Box2D body if needed in other parts of the game
    public Body getBody() {
        return body;
    }
}
