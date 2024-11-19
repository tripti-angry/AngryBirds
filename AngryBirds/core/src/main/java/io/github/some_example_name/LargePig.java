package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;


public class LargePig extends Pig {

    public LargePig(float x, float y, World world) {
        super(
            new Texture(Gdx.files.internal("angry-birds/large_pig.png")), // Texture
            x,                                                            // X position
            y,                                                            // Y position
            70,                                                           // Width
            70,                                                           // Height
            30                                                            // Health
        );
    }

    @Override
    protected void onDestroyed() {
        System.out.println("LargePig destroyed!");
        // Add custom destruction behavior (e.g., big explosion or sound effect)
    }
}
