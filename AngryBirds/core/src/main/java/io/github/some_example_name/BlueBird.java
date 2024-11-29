package io.github.some_example_name;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class BlueBird extends Bird {

    public BlueBird(World world, float x, float y) {
      
        super("angry-birds/bluebird.png", x, y, 50, 50, 5, world); 
    }

    @Override
    public void launch(Vector2 force) {
       
        body.applyLinearImpulse(force, body.getWorldCenter(), true);
        System.out.println("BlueBird splits into three smaller birds!");

       
    }
}
