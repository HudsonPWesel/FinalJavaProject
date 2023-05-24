import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int quickness;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public int direction;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    public Rectangle hitbox;
    public boolean isCollided = false;
    public Sprite sprite;

    /**
     * 
     * @param worldX    xPos
     * @param worldY    yPos
     * @param quickness speed
     * @param hitbox    collision box
     * @param sprite    sprite object that handles picture drawing
     */
    public Entity(int worldX, int worldY, int quickness, Rectangle hitbox, Sprite sprite) {
        this.worldX = worldY;
        this.worldY = worldY;
        this.hitbox = hitbox;
        this.quickness = quickness;
        this.sprite = sprite;

    }

}