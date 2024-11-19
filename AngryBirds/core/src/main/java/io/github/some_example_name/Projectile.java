package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    private Vector2 position;
    private Texture texture;
    private TextureRegion textureRegion; // TextureRegion for drawing
    private float angle;
    private boolean isLaunched;

    public Projectile(float startX, float startY) {
        this.position = new Vector2(startX, startY);
        this.texture = new Texture("bird.png"); // Use your own texture
        this.textureRegion = new TextureRegion(texture); // Create a TextureRegion from the Texture
        this.isLaunched = false;
    }

    public void launch(float power, float angle) {
        // Apply force to the projectile based on the power and angle
        this.angle = angle;
        this.isLaunched = true;
    }

    public void update() {
        if (isLaunched) {
            // Update position or physics here if needed
            // E.g., move the projectile with velocity, apply gravity, etc.
        }
    }

    public void render(SpriteBatch batch) {
        // Calculate the center of the projectile texture for rotation
        float originX = textureRegion.getRegionWidth() / 2f;
        float originY = textureRegion.getRegionHeight() / 2f;

        // Draw the projectile texture with rotation using TextureRegion
        batch.draw(textureRegion, position.x, position.y, originX, originY, textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), 1f, 1f, angle);
    }

    public void resetPosition(float x, float y) {
        this.position.set(x, y);
        this.isLaunched = false;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
