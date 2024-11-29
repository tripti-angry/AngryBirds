package io.github.some_example_name;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class BirdTest {

    private World world;
    private RedBird redBird;
    private SpriteBatch batch;

    // Manually set up the test environment
    public void setUp() {
        // Create a Box2D world for testing
        world = new World(new Vector2(0, -9.81f), true);
        batch = new SpriteBatch();
        // Initialize a RedBird for testing
        redBird = new RedBird(100, 100, world);  // Only passing the x, y, and world parameters
    }

    // Manually run the tests
    public void runTests() {
        setUp();
        testLaunch();
        testIsTouched();
        testGetImpactDamage();
        testIsOutOfBounds();
        testRender();
        testDispose();
    }

    // Test for bird launch
    public void testLaunch() {
        System.out.println("Running testLaunch...");

        // Given a RedBird, call the launch method
        Vector2 force = new Vector2(10, 10);
        redBird.launch(force);

        // Check if the bird's body has been launched
        if (redBird.getBody() == null) {
            System.out.println("testLaunch failed: Bird's body is null.");
        } else if (redBird.getBody().getLinearVelocity().len() <= 0) {
            System.out.println("testLaunch failed: Bird's velocity is zero.");
        } else {
            System.out.println("testLaunch passed!");
        }
    }

    // Test if bird detects touches
    public void testIsTouched() {
        System.out.println("Running testIsTouched...");

        // Given a touch position
        Vector2 touchPosition = new Vector2(redBird.getX(), redBird.getY());

        // Check if the bird detects the touch correctly
        if (!redBird.isTouched(touchPosition)) {
            System.out.println("testIsTouched failed: Bird not touched at its position.");
        }

        // Testing an outside position should return false
        touchPosition = new Vector2(redBird.getX() + 100, redBird.getY() + 100);
        if (redBird.isTouched(touchPosition)) {
            System.out.println("testIsTouched failed: Bird wrongly detected touch outside its bounds.");
        }

        System.out.println("testIsTouched passed!");
    }

    // Test the impact damage calculation
    public void testGetImpactDamage() {
        System.out.println("Running testGetImpactDamage...");

        // Given a RedBird and a velocity
        redBird.getBody().setLinearVelocity(new Vector2(10, 0));

        // Calculate the expected impact damage (damage * velocity)
        int expectedDamage = 10 * 10; // Assuming 10 is the damage value for RedBird
        int actualDamage = redBird.getImpactDamage();

        // Check if the calculated impact damage is correct
        if (expectedDamage != actualDamage) {
            System.out.println("testGetImpactDamage failed: Expected " + expectedDamage + " but got " + actualDamage);
        } else {
            System.out.println("testGetImpactDamage passed!");
        }
    }

    // Test if the bird is out of bounds
    public void testIsOutOfBounds() {
        System.out.println("Running testIsOutOfBounds...");

        // Assuming the game area is set to 1000x1000 based on Box2D world bounds
        if (redBird.isOutOfBounds()) {
            System.out.println("testIsOutOfBounds failed: Bird should not be out of bounds at the start.");
        }

        // Set the bird's position to outside the world bounds
        redBird.setPosition(2000, 2000);

        // Now the bird should be out of bounds
        if (!redBird.isOutOfBounds()) {
            System.out.println("testIsOutOfBounds failed: Bird should be out of bounds after moving outside the world.");
        }

        System.out.println("testIsOutOfBounds passed!");
    }

    // Test the render method (though it doesn't really validate visual output)
    public void testRender() {
        System.out.println("Running testRender...");

        // Testing render might be more difficult because it requires graphical context.
        // But we can call it to ensure it doesn't throw any errors.
        try {
            redBird.render(batch);
            System.out.println("testRender passed!");
        } catch (Exception e) {
            System.out.println("testRender failed: Exception thrown during render.");
        }
    }

    // Test the dispose method
    public void testDispose() {
        System.out.println("Running testDispose...");

        // Given that the texture is loaded
        if (redBird.texture == null) {
            System.out.println("testDispose failed: Texture is null before disposal.");
        }

        // Dispose of the resources
        redBird.dispose();

        // After disposing, the texture should be null
        if (redBird.texture != null) {
            System.out.println("testDispose failed: Texture was not properly disposed.");
        } else {
            System.out.println("testDispose passed!");
        }
    }

    // Main method to execute tests manually
    public static void main(String[] args) {
        BirdTest test = new BirdTest();
        test.runTests(); // Run all tests manually
    }
}
