package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Structure {
    protected Texture texture;
    protected float x, y;
    protected float width, height;
    protected int health; // Health to determine how much damage it can take

    // Constructor that accepts texture region, position (x, y), and health
    public Structure(TextureRegion textureRegion, float x, float y, int health) {
        this.texture = textureRegion.getTexture(); // Get the texture from TextureRegion
        this.x = x;
        this.y = y;
        this.width = textureRegion.getRegionWidth(); // Set width based on texture size
        this.height = textureRegion.getRegionHeight(); // Set height based on texture size
        this.health = health;
    }

    // Constructor that only accepts position (x, y) and sets default values for texture and health
    public Structure(float x, float y) {
        this.x = x;
        this.y = y;
        this.texture = new Texture(Gdx.files.internal("default_texture.png"));  // Default texture
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.health = 100; // Default health
    }

    // Default constructor, should be used with caution as it's not fully initialized
    public Structure() {
        // No initialization, might be used for specific cases where a structure is not fully set yet
    }

    // Method to check if the structure is destroyed (health <= 0)
    public boolean isDestroyed() {
        return health <= 0;
    }

    // Method to apply damage to the structure
    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;  // Ensure health doesn't go below zero
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
}
