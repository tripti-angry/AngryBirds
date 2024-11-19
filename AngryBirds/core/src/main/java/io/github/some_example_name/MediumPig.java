package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MediumPig extends Pig {

    public MediumPig(float x, float y) {
        super(
            new Texture(Gdx.files.internal("angry-birds/medium_pig.png")), // Texture
            x,                                                            // X position
            y,                                                            // Y position
            50,                                                           // Width
            50,                                                           // Height
            20                                                            // Health
        );
    }

    @Override
    protected void onDestroyed() {
        System.out.println("MediumPig destroyed!");
        // Add custom destruction behavior (e.g., larger explosion or sound)
    }
}
