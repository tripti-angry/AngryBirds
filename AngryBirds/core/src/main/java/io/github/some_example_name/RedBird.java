package io.github.some_example_name;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class RedBird extends Bird {

    private float scale = 2.0f; // Factor by which to scale the bird size

    public RedBird(float x, float y, World world) {
        super("angry-birds/angry.png", x, y, 50, 50, 10, world); // 10 is the damage value
    }

    @Override
    public void launch(Vector2 force) {
        if (body != null) {
            // Limit the launch force to avoid excessive height
            float maxLaunchForce = 40f; // Set a maximum value for the launch force
            force.x = Math.min(force.x, maxLaunchForce); // Limit horizontal force
            force.y = Math.min(force.y, maxLaunchForce); // Limit vertical force to prevent high launches

            body.applyLinearImpulse(force, body.getWorldCenter(), true);
            System.out.println("RedBird launched with force: " + force);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // Ensure that the batch is not null
        if (batch != null && body != null) {
            Vector2 position = body.getPosition();
            // Scale the texture size
            float width = texture.getWidth() * scale;  // Apply scale factor to width
            float height = texture.getHeight() * scale;  // Apply scale factor to height

            // Draw the bird centered at the body position with new scaled size
            batch.draw(texture, position.x - width / 2, position.y - height / 2, width, height);
        }
    }

    @Override
    public void update(float deltaTime) {
        // Update the bird's physics if necessary (e.g., checking for conditions)
        super.update(deltaTime); // Call parent update if needed for common functionality
    }

    @Override
    public void dispose() {
        super.dispose(); // Dispose of resources (texture, etc.)
    }
}
