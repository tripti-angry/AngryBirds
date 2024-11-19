package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;

public class SmallPig extends Pig {

    public SmallPig(float x, float y, World world) {
        super(new Texture(Gdx.files.internal("angry-birds/small-pig.png")),
            x,
            y,
            40,  // Width
            40,  // Height
            10); // Health
    }

    @Override
    protected void onDestroyed() {
        // Implement custom destruction logic here for SmallPig
        System.out.println("SmallPig destroyed!");
        // You can add more logic here, like playing an explosion sound or triggering an animation.
    }
}
