package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.CircleShape;

public abstract class Bird {

    protected Body body;           
    protected String texturePath;   
    protected int damage;          
    protected Texture texture;      
    private Vector2 position;       
    private World world;            

    public Bird(String texturePath, float x, float y, int width, int height, int damage, World world) {
        this.texturePath = texturePath;
        this.damage = damage;
        this.position = new Vector2(x, y);
        this.world = world;

        this.texture = new Texture(texturePath);

        createBody(x, y, width, height);
    }

    private void createBody(float x, float y, int width, int height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;  
        bodyDef.position.set(x, y);                 

        body = world.createBody(bodyDef);           

     
        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2f); 

  
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;      
        fixtureDef.friction = 0.5f;    
        fixtureDef.restitution = 0.5f;  

        body.createFixture(fixtureDef);

      
        shape.dispose();
    }

    public abstract void launch(Vector2 force);


    public void update(float deltaTime) {
       
    }


    public void render(SpriteBatch batch) {
        if (texture != null) {
            Vector2 bodyPosition = body.getPosition(); 
            batch.draw(texture,
                bodyPosition.x - texture.getWidth() / 2f,
                bodyPosition.y - texture.getHeight() / 2f);
        }
    }

    public Body getBody() {
        return body;
    }


    public Vector2 getPosition() {
        return body.getPosition();
    }

    public float getX() {
        return body.getPosition().x;
    }

    public float getY() {
        return body.getPosition().y;
    }

    public void setPosition(float x, float y) {
        position.set(x, y); 
        if (body != null) {
            body.setTransform(x, y, body.getAngle()); 
        }
    }

    public boolean isTouched(Vector2 touchPosition) {
        float birdWidth = texture.getWidth();
        float birdHeight = texture.getHeight();
        return touchPosition.x >= getX() - birdWidth / 2 &&
            touchPosition.x <= getX() + birdWidth / 2 &&
            touchPosition.y >= getY() - birdHeight / 2 &&
            touchPosition.y <= getY() + birdHeight / 2;
    }


    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }


}
