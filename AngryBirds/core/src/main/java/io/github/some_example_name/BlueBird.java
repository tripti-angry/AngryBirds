package io.github.some_example_name;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class BlueBird extends Bird {

    public BlueBird(World world, float x, float y) {
        // Call the parent constructor, passing the world and the other parameters
        super("angry-birds/bluebird.png", x, y, 50, 50, 5, world); // 5 is the damage value
    }

    @Override
    public void launch(Vector2 force) {
        // Apply the initial force to launch the BlueBird
        body.applyLinearImpulse(force, body.getWorldCenter(), true);
        System.out.println("BlueBird splits into three smaller birds!");

        // Logic for splitting the BlueBird into three smaller birds
        // You would create three new instances of smaller birds with slightly different trajectories
        // For example, applying forces at different angles:
        // For now, let's just print a message
    }
}
