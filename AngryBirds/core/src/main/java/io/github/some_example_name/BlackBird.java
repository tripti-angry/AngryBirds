package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import java.util.ArrayList; // <-- Import ArrayList

public class BlackBird extends Bird {

    private boolean isExploding = false;
    private float explosionDelay = 2.0f; // Time before explosion in seconds
    private float timeSinceLaunch = 0.0f;
    private float explosionRadius = 5.0f; // Radius of the explosion's effect

    public BlackBird(float x, float y, World world) {
        super("angry-birds/blackbird.png", x, y, 50, 50, 20, world); // 20 is the damage value for explosion
    }

    @Override
    public void launch(Vector2 force) {
        // Apply the initial force to launch the BlackBird
        body.applyLinearImpulse(force, body.getWorldCenter(), true);
        System.out.println("BlackBird launched with a delayed explosion!");
        timeSinceLaunch = 0.0f; // Reset the launch timer
        isExploding = false; // Reset explosion state
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        // Check if the bird is in the air and the explosion delay has passed
        if (isExploding) {
            explode(); // Trigger explosion
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (!isExploding) {
            timeSinceLaunch += deltaTime;
            if (timeSinceLaunch >= explosionDelay) {
                isExploding = true;
            }
        }
    }

    // Handle explosion, applying force to nearby objects
    private void explode() {
        System.out.println("BlackBird explodes, causing area damage!");

        // Get the bird's current position from Box2D body
        Vector2 explosionCenter = body.getPosition();

        // Apply force to nearby bodies in the world (Area of Effect)
        for (Body otherBody : getNearbyBodies(explosionCenter, explosionRadius)) {
            // Calculate direction and apply force to nearby bodies
            Vector2 direction = otherBody.getPosition().sub(explosionCenter).nor();
            float forceMagnitude = 50f; // Force of explosion
            otherBody.applyLinearImpulse(direction.scl(forceMagnitude), otherBody.getWorldCenter(), true);
        }
    }

    // Method to get nearby bodies within an explosion radius (placeholder for simplicity)
    private Iterable<Body> getNearbyBodies(Vector2 center, float radius) {
        // In a real implementation, you'd use Box2D queries to get bodies within the radius
        // Here, we return an empty list as a placeholder
        return new ArrayList<>();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
