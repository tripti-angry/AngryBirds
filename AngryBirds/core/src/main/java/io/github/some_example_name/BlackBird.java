package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class BlackBird extends Bird {

    private final float scale = 0.14f; 
    private final float maxLaunchForce = 40f; 

    public BlackBird(World world, float x, float y) {
        super("angry-birds/blackbird.png", x, y, 50, 50, 10, world); 
    }

    @Override
    public void launch(Vector2 force) {
        if (body != null) {
            
            if (force.len() > maxLaunchForce) {
                force.nor().scl(maxLaunchForce); 
            }

           
            body.applyLinearImpulse(force, body.getWorldCenter(), true);

            isLaunched = true;
            System.out.println("BlackBird launched with force: " + force);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (batch != null && body != null && texture != null) {
            
            TextureRegion textureRegion = new TextureRegion(texture);

           
            Vector2 position = body.getPosition();
            float width = textureRegion.getRegionWidth() * scale;
            float height = textureRegion.getRegionHeight() * scale;

            batch.draw(textureRegion, position.x - width / 2, position.y - height / 2, width, height);
        }
    }


    public void update(float deltaTime) {
        
    }

    @Override
    public void dispose() {
        super.dispose(); 
    }
}
