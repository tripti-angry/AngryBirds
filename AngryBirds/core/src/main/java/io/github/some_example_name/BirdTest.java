package io.github.some_example_name;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class BirdTest {

    private World world;
    private RedBird redBird;
    private SpriteBatch batch;

   
    public void setUp() {
        
        world = new World(new Vector2(0, -9.81f), true);
        batch = new SpriteBatch();
        
        redBird = new RedBird(100, 100, world); 
    }

    
    public void runTests() {
        setUp();
        testLaunch();
        testIsTouched();
        testGetImpactDamage();
        testIsOutOfBounds();
        testRender();
        testDispose();
    }

   
    public void testLaunch() {
        System.out.println("Running testLaunch...");

       
        Vector2 force = new Vector2(10, 10);
        redBird.launch(force);

        
        if (redBird.getBody() == null) {
            System.out.println("testLaunch failed: Bird's body is null.");
        } else if (redBird.getBody().getLinearVelocity().len() <= 0) {
            System.out.println("testLaunch failed: Bird's velocity is zero.");
        } else {
            System.out.println("testLaunch passed!");
        }
    }

    
    public void testIsTouched() {
        System.out.println("Running testIsTouched...");

       
        Vector2 touchPosition = new Vector2(redBird.getX(), redBird.getY());

       
        if (!redBird.isTouched(touchPosition)) {
            System.out.println("testIsTouched failed: Bird not touched at its position.");
        }

       
        touchPosition = new Vector2(redBird.getX() + 100, redBird.getY() + 100);
        if (redBird.isTouched(touchPosition)) {
            System.out.println("testIsTouched failed: Bird wrongly detected touch outside its bounds.");
        }

        System.out.println("testIsTouched passed!");
    }

  
    public void testGetImpactDamage() {
        System.out.println("Running testGetImpactDamage...");

        
        redBird.getBody().setLinearVelocity(new Vector2(10, 0));

      
        int expectedDamage = 10 * 10; 
        int actualDamage = redBird.getImpactDamage();

        
        if (expectedDamage != actualDamage) {
            System.out.println("testGetImpactDamage failed: Expected " + expectedDamage + " but got " + actualDamage);
        } else {
            System.out.println("testGetImpactDamage passed!");
        }
    }

   
    public void testIsOutOfBounds() {
        System.out.println("Running testIsOutOfBounds...");

       
        if (redBird.isOutOfBounds()) {
            System.out.println("testIsOutOfBounds failed: Bird should not be out of bounds at the start.");
        }

      
        redBird.setPosition(2000, 2000);

       
        if (!redBird.isOutOfBounds()) {
            System.out.println("testIsOutOfBounds failed: Bird should be out of bounds after moving outside the world.");
        }

        System.out.println("testIsOutOfBounds passed!");
    }

    
    public void testRender() {
        System.out.println("Running testRender...");

       
        try {
            redBird.render(batch);
            System.out.println("testRender passed!");
        } catch (Exception e) {
            System.out.println("testRender failed: Exception thrown during render.");
        }
    }

    
    public void testDispose() {
        System.out.println("Running testDispose...");

        
        if (redBird.texture == null) {
            System.out.println("testDispose failed: Texture is null before disposal.");
        }

        
        redBird.dispose();

        
        if (redBird.texture != null) {
            System.out.println("testDispose failed: Texture was not properly disposed.");
        } else {
            System.out.println("testDispose passed!");
        }
    }

    
    public static void main(String[] args) {
        BirdTest test = new BirdTest();
        test.runTests();
    }
}
