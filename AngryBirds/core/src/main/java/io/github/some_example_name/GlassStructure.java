package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.MathUtils;


public class GlassStructure extends Structure {
    protected Body body;
    private float width;  
    private float height;
    private float scale = -10f; 

    public GlassStructure(float x, float y, int health, String shapeType, World world) {
       
        super(new TextureRegion(new Texture(getTextureForShape(shapeType))), x, y, health, world);

     
        this.width = 100; 
        this.height = 10; 

        if (shapeType.equalsIgnoreCase("rectangle")) {
            this.width = 400; 
            this.height = 50; 
        }

        if (shapeType.equalsIgnoreCase("thick-rectangle-block")) {
            this.width = 80;  
            this.height = 70; 
        }

        if (shapeType.equalsIgnoreCase("square")) {
            this.width = 270;  
            this.height = 90; 
        }

        if (shapeType.equalsIgnoreCase("vertical-rectangle")) {
            this.width = 20;  
            this.height = 100; 
        }


        
        createPhysicsBody(x, y, world);
    }


    private static String getTextureForShape(String shapeType) {
        switch (shapeType.toLowerCase()) {
            case "circular":
                return "angry-birds/glass_circular.png";
            case "square":
                return "angry-birds/glass_square.png";
            case "triangle":
                return "angry-birds/glass_triangle.png";
            case "rectangle":
                return "angry-birds/glass_rectangle.png";
            case "thick-rectangle-block":
                return "angry-birds/glass_rectangle.png";
            case "vertical-rectangle":
                return "angry-birds/glass_rectangle.png";
            default:
                return "angry-birds/glass_structure.png";
        }
    }

    public void setRotation(float degrees) {
       
        body.setTransform(body.getPosition(), MathUtils.degreesToRadians * degrees);
    }

    private void createPhysicsBody(float x, float y, World world) {
       
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody; 
        bodyDef.position.set(x, y); 

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2); 

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;  
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.2f; 

        body.createFixture(fixtureDef);

  
        shape.dispose();
    }



    @Override
    public void update() {
    
        if (isDestroyed()) {
            System.out.println("Glass structure shattered!");
          
        }
    }

    @Override
    public void dispose() {
        super.dispose(); 
    }

    @Override
    public void takeDamage(int damage) {
      
        int fragileDamage = damage * 2;
        super.takeDamage(fragileDamage);
    }

    @Override
    public void render(SpriteBatch batch) {
        Vector2 position = body.getPosition();
       
        batch.draw(texture, position.x - width / 2, position.y - height / 2, width, height);
    }

}
