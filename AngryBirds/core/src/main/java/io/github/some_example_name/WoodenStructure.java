package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class WoodenStructure extends Structure {
    protected Body body; // Physics body for Box2D
    private float width;
    private float height;

    public WoodenStructure(float x, float y, int health, String shapeType, World world) {
        // Call super first, passing the textureRegion, x, y, and health
        super(new TextureRegion(new Texture(getTextureForShape(shapeType))), x, y, health);

        // Now that super is called, you can initialize the rest
        this.width = 50; // Default width
        this.height = 50; // Default height

        // Adjust dimensions for specific shape types
        if (shapeType.equalsIgnoreCase("rectangle")) {
            this.width = 100; // Example dimensions for a rectangle
            this.height = 20;
        }

        // Create the Box2D body for the structure
        createPhysicsBody(x, y, world);
    }



    private static String getTextureForShape(String shapeType) {
        switch (shapeType.toLowerCase()) {
            case "square":
                return "angry-birds/wooden_square.png";
            case "triangle":
                return "angry-birds/wooden_triangle.png";
            case "rectangle":
                return "angry-birds/wooden_rectangle.png";
            default:
                return "angry-birds/wooden_structure.png";
        }
    }

    private void createPhysicsBody(float x, float y, World world) {
        // Define the body type and position
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody; // Static body since it's a structure
        bodyDef.position.set(x, y);

        // Create the Box2D body
        body = world.createBody(bodyDef);

        // Define the shape of the structure
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        // Create a fixture for the body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;  // Wood density
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f; // Wood-like bounce

        body.createFixture(fixtureDef);

        // Dispose of the shape after it's no longer needed
        shape.dispose();
    }


    public void rotate(float angleDegrees) {
        float angleRadians = (float) Math.toRadians(angleDegrees);
        body.setTransform(body.getPosition(), angleRadians);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (batch != null && texture != null) {
            Vector2 position = body.getPosition();
            float angle = (float) Math.toDegrees(body.getAngle()); // Convert angle to degrees

            // Draw the texture using rotation
            batch.draw(
                texture,
                position.x - width / 2, position.y - height / 2, // Bottom-left corner
                width / 2, height / 2, // Origin for rotation
                width, height, // Dimensions of the texture
                1, 1 // Scale
                // Rotation angle
            );
        }
    }

    @Override
    public void takeDamage(int damage) {
        this.health -= damage;
        System.out.println("Wooden structure takes " + damage + " damage!");
        if (this.health <= 0) {
            destroy();
        }
    }

    @Override
    public void update() {
        if (this.health <= 0) {
            destroy();
        }
    }

    private void destroy() {
        System.out.println("Wooden structure is destroyed!");
        // Logic to remove this structure from the world, if necessary
    }
}
