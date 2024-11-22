package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;


public class SteelStructure extends Structure {

    public SteelStructure(float x, float y, int health, String shapeType, World world) {
        // Initialize the steel structure with a specific texture based on shape type
        super(new TextureRegion(new Texture(getTextureForShape(shapeType))), x, y, health,world);
        this.width = 60; // Default width for steel structures
        this.height = 60; // Default height for steel structures

        // Adjust size based on shape type
        if (shapeType.equalsIgnoreCase("rectangle")) {
            this.width = 70;  // Reduce width
            this.height = 70; // Reduce height
        }

        if (shapeType.equalsIgnoreCase("vertical-rectangle")) {
            this.width = 30;  // Reduce width
            this.height = 130; // Reduce height
        }

    }

    // A helper method to get the texture based on the shape type
    private static String getTextureForShape(String shapeType) {
        switch (shapeType.toLowerCase()) {

            case "square":
                return "angry-birds/steel-square.png";
            case "triangle":
                return "angry-birds/steel-triangle.png";
            case "rectangle":
                return "angry-birds/steel-rectangle.png";
            case "vertical-rectangle":
                return "angry-birds/steel-rectangle.png";
            default:
                return "angry-birds/steel_structure.png";
        }
    }

    @Override
    public void update() {
        // Specific behavior for SteelStructure (e.g., respond to damage or physics)
        if (isDestroyed()) {
            // Perform any actions when the steel structure is destroyed
            System.out.println("Steel structure destroyed!");
        }
    }

    // Override takeDamage to make SteelStructure more resistant to damage
    @Override
    public void takeDamage(int damage) {
        // SteelStructure could have resistance to damage, reducing incoming damage
        int reducedDamage = (int) (damage * 0.7); // Example: Steel takes only 70% of damage
        super.takeDamage(reducedDamage);
        System.out.println("Steel structure takes " + reducedDamage + " damage!");
    }
}
