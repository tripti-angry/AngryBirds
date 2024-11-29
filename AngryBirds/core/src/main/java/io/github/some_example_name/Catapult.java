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
    private TextureRegion catapultTextureRegion; 
    private SpriteBatch batch;
    private World world;
    private Body armBody, baseBody;
    private Projectile currentProjectile;
    private float height;

    public Catapult(float x, float y, World world) {
        this.x = x;
        this.y = y;
        this.world = world;
        this.angle = 0f;
        this.power = 0f;

        catapultTexture = new Texture("ui/sshot.png"); 
        catapultTextureRegion = new TextureRegion(catapultTexture); 
        createPhysicsBodies();
    }

    private void createPhysicsBodies() {
       
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public float getHeight() {
        return height;
    }

    public void render(SpriteBatch batch) {
       
        float scale = 0.4f;  

        float scaledWidth = catapultTextureRegion.getRegionWidth() * scale;
        float scaledHeight = catapultTextureRegion.getRegionHeight() * scale;

     
        batch.draw(catapultTextureRegion, x, y, catapultTextureRegion.getRegionWidth() / 3f, catapultTextureRegion.getRegionHeight() / 3f,
            catapultTextureRegion.getRegionWidth(), catapultTextureRegion.getRegionHeight(), scale, scale, angle);

    
        if (currentProjectile != null) {
            currentProjectile.render(batch);
        }
    }

    public void update() {
       
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
        
        if (currentProjectile != null) {
            currentProjectile.launch(power, angle);
        }
    }

    public void resetProjectile() {

        if (currentProjectile != null) {
            currentProjectile.resetPosition(x, y);
        }
    }

    public void setProjectile(Projectile projectile) {
        this.currentProjectile = projectile;
    }

    public void dispose() {

        if (catapultTexture != null) {
            catapultTexture.dispose();
        }
    }
}
