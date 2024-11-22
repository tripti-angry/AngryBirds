package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Pig {
    protected Texture texture;
    protected float x, y;
    protected float width, height;
    protected int health;

    // Constructor to initialize the pig with texture, position, size, and health
    public Pig(Texture texture, float x, float y, float width, float height, int health) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
    }

    // Method to apply damage and trigger destruction if health <= 0
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            onDestroyed();
        }
    }

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    // Abstract method for destruction behavior (to be implemented in subclasses)
    protected abstract void onDestroyed();

    // Check if the pig is destroyed (health <= 0)
    public boolean isDestroyed() {
        return health <= 0;
    }

    // Set a new position for the pig
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Getter for the texture of the pig
    public Texture getTexture() {
        return texture;
    }

    // Getter for the width of the pig
    public float getWidth() {
        return width;
    }

    // Getter for the height of the pig
    public float getHeight() {
        return height;
    }

    // Dispose of the texture to free up resources when no longer needed
    public void dispose() {
        texture.dispose();
    }

    // Render the pig (draw its texture on the screen using SpriteBatch)
    public void render(SpriteBatch batch) {
        if (!isDestroyed()) {
            batch.draw(texture, x, y, width, height);
        }
    }

    // You can override the update method if needed in subclasses
    public void update() {
        // Logic for updating the pig's state (e.g., movement, physics) can go here
    }
}
