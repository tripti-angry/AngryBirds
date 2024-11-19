package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.MathUtils;


public class GlassStructure extends Structure {
    protected Body body;
    private float width;  // Width of the glass structure
    private float height; // Height of the glass structure
    private float scale = -10f; // Scaling factor for the structure's size

    public GlassStructure(float x, float y, int health, String shapeType, World world) {
        // Initialize the glass structure with a texture based on the shape type
        super(new TextureRegion(new Texture(getTextureForShape(shapeType))), x, y, health);

        // Set initial size based on shape type or custom scaling
        this.width = 100;  // Default width
        this.height = 10;  // Default height

        // Adjust size based on shape type
        if (shapeType.equalsIgnoreCase("rectangle")) {
            this.width = 10;  // Reduce width
            this.height = 20; // Reduce height
        }

        // Create the Box2D body for the structure
        createPhysicsBody(x, y, world);
    }

    // A helper method to get the texture based on the shape type
    private static String getTextureForShape(String shapeType) {
        switch (shapeType.toLowerCase()) {
            case "circular":
                return "angry-birds/glass_circular.png";
            case "square":
                return "angry-birds/glass_square.png";
            case "triangle":
                return "angry-birds/glass_triangle.png";
            case "rectangle":
                return "angry-birds/glass_rectangle.png";
            default:
                return "angry-birds/glass_structure.png";
        }
    }

    public void setRotation(float degrees) {
        // Box2D rotation is in radians, so convert degrees to radians
        body.setTransform(body.getPosition(), MathUtils.degreesToRadians * degrees);
    }

    private void createPhysicsBody(float x, float y, World world) {
        // Box2D body definition for the structure
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody; // Static body for a structure that doesn't move
        bodyDef.position.set(x, y); // Position at the specified location

        // Create the Box2D body
        body = world.createBody(bodyDef);

        // Create the shape for the glass structure
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2); // Half of the width and height for Box2D

        // Define the fixture for the body (collision properties)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;  // Glass-like density
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.2f;  // Low restitution for glass-like behavior

        // Attach the fixture to the body
        body.createFixture(fixtureDef);

        // Dispose of the shape after use to free memory
        shape.dispose();
    }



    @Override
    public void update() {
        // Specific behavior for GlassStructure (e.g., respond to damage or physics)
        if (isDestroyed()) {
            System.out.println("Glass structure shattered!");
            // Additional destruction effects (e.g., particles, sound) can be added here
        }
    }

    @Override
    public void dispose() {
        super.dispose(); // Dispose of resources (texture, etc.)
    }

    @Override
    public void takeDamage(int damage) {
        // Glass structures are fragile, receiving double damage
        int fragileDamage = damage * 2;
        super.takeDamage(fragileDamage);
    }

    @Override
    public void render(SpriteBatch batch) {
        Vector2 position = body.getPosition();
        // Assuming you have logic to render the texture properly
        batch.draw(texture, position.x - width / 2, position.y - height / 2, width, height);
    }

}
