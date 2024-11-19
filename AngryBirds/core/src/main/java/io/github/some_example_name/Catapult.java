package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Catapult {
    private float x, y;
    private float angle;
    private float power;
    private Texture catapultTexture;
    private TextureRegion catapultTextureRegion; // TextureRegion for drawing
    private SpriteBatch batch;
    private World world;
    private Body armBody, baseBody;
    private Projectile currentProjectile;

    public Catapult(float x, float y, World world) {
        this.x = x;
        this.y = y;
        this.world = world;
        this.angle = 0f;
        this.power = 0f;

        catapultTexture = new Texture("ui/sshot.png"); // Placeholder texture
        catapultTextureRegion = new TextureRegion(catapultTexture); // Create TextureRegion
        createPhysicsBodies();
    }

    private void createPhysicsBodies() {
        // Define and create physics bodies for the catapult arm and base (e.g., static and dynamic bodies)
    }

    public void render(SpriteBatch batch) {
        // Define scaling factor
        float scale = 0.4f;  // Scale factor for the catapult texture

        // Get the scaled width and height
        float scaledWidth = catapultTextureRegion.getRegionWidth() * scale;
        float scaledHeight = catapultTextureRegion.getRegionHeight() * scale;

        // Draw the catapult texture at the appropriate position with scaling
        batch.draw(catapultTextureRegion, x, y, catapultTextureRegion.getRegionWidth() / 3f, catapultTextureRegion.getRegionHeight() / 3f,
            catapultTextureRegion.getRegionWidth(), catapultTextureRegion.getRegionHeight(), scale, scale, angle);

        // Optionally, draw the projectile (if there is one being launched)
        if (currentProjectile != null) {
            currentProjectile.render(batch);
        }
    }

    public void update() {
        // Update the position of the catapult, including power and angle
        // Update projectile position if being launched
        if (currentProjectile != null) {
            currentProjectile.update();
        }
    }

    public void aim(float newAngle) {
        this.angle = newAngle;
    }

    public void pullBack(float newPower) {
        this.power = newPower;
    }

    public void release() {
        // Launch the projectile with the current power and angle
        if (currentProjectile != null) {
            currentProjectile.launch(power, angle);
        }
    }

    public void resetProjectile() {
        // Reset the projectile to the catapult
        if (currentProjectile != null) {
            currentProjectile.resetPosition(x, y);
        }
    }

    public void setProjectile(Projectile projectile) {
        this.currentProjectile = projectile;
    }

    public void dispose() {
        // Dispose textures and other resources
        if (catapultTexture != null) {
            catapultTexture.dispose();
        }
    }
}
