package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StructureTest {

    private World world;
    private Structure structure;

    @Before
    public void setUp() {
        // Set up the world and the structure before each test
        world = new World(new Vector2(0, -9.8f), true);  // Create a gravity-affected world
        TextureRegion textureRegion = new TextureRegion(new Texture("default_texture.png"));
        structure = new Structure(textureRegion, 10, 10, 100, world) {
            @Override
            public void update() {
                // Dummy update method for abstract class
            }
        };
    }

    @Test
    public void testStructureInitialization() {
        assertNotNull("Structure should be initialized", structure);
        assertEquals("Health should be initialized to 100", 100, structure.getHealth());
        assertFalse("Structure should not be destroyed initially", structure.isDestroyed());
    }

    @Test
    public void testTakeDamage() {
        structure.applyDamage(50);
        assertEquals("Health should be reduced by 50", 50, structure.getHealth());

        structure.applyDamage(50);
        assertTrue("Structure should be destroyed when health reaches 0", structure.isDestroyed());
    }

    @Test
    public void testDestroyStructure() {
        structure.applyDamage(100); // Apply enough damage to destroy the structure
        assertTrue("Structure should be destroyed after taking 100 damage", structure.isDestroyed());

        // Check that the Box2D body is destroyed from the world
        assertNull("Box2D body should be destroyed", structure.getBody());
    }

    @Test
    public void testDispose() {
        structure.dispose(); // Call dispose to release resources
        assertTrue("Texture should be disposed", structure.isDestroyed());
    }

    @Test
    public void testHealthAfterDestruction() {
        structure.applyDamage(50); // Apply partial damage
        assertEquals("Health should be 50 after taking 50 damage", 50, structure.getHealth());

        structure.applyDamage(60); // Apply more damage than remaining health
        assertTrue("Structure should be destroyed after health reaches 0", structure.isDestroyed());
    }

    @Test
    public void testPosition() {
        // The structure is created at (10, 10) in the world, verify this
        assertEquals("Structure should be at X position 10", 10f, structure.getPosition().x, 0.01f);
        assertEquals("Structure should be at Y position 10", 10f, structure.getPosition().y, 0.01f);
    }

    @Test
    public void testReduceHealth() {
        structure.reduceHealth(20); // Reduce health by 20
        assertEquals("Health should be reduced by 20", 80, structure.getHealth());

        structure.reduceHealth(80); // Reduce health by 80, total damage of 100
        assertTrue("Structure should be destroyed after total damage of 100", structure.isDestroyed());
    }
}
