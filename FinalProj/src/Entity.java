import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

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

    public Entity(int worldX, int worldY, int quickness, Rectangle hitbox, Sprite sprite) {
        this.worldX = worldY;
        this.worldY = worldY;
        this.hitbox = hitbox;
        this.quickness = quickness;
        this.sprite = sprite;
        setSprites(sprite);
    }

    private void setSprites(Sprite sprite) {

        // Intilize Sprite Sheet Image
        BufferedImage spriteSheetImage = initSpriteSheet(sprite.spritesheetFileName);

        // Get All Sprites and add them to a corrosponding HashMap(Row,
        // ArrayList<Sprites Associated w/ Animation> )
        sprite.spriteAnimationCycles = initAnimationCycles(spriteSheetImage, sprite);

    }

    private BufferedImage initSpriteSheet(String spriteSheetPath) {
        // ImageIO.read(PathFinder(sprites[i]).toFile());
        try {
            return ImageIO.read(
                    PathFinder.getFilePathForFile(spriteSheetPath).toFile());
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("SpriteSheet Not Found!");
        }

    }

    private HashMap<String, ArrayList<BufferedImage>> initAnimationCycles(BufferedImage spriteSheet,
            Sprite sprite) {

        HashMap<String, ArrayList<BufferedImage>> currentAnimationCycles = new HashMap<String, ArrayList<BufferedImage>>();

        int numRows = sprite.animationCycleRowNames.length;

        for (int row = 0; row < numRows; row++) {

            currentAnimationCycles.put(sprite.animationCycleRowNames[row], new ArrayList<BufferedImage>());

            for (int col = 0; col < sprite.spriteColumnSequence[row]; col++) {
                int spriteXPos = col * sprite.width;
                int spriteYPos = row * sprite.height;
                // 120,130
                //
                currentAnimationCycles.get(sprite.animationCycleRowNames[row]).add(spriteSheet.getSubimage((spriteXPos),
                        (spriteYPos), sprite.width, sprite.height));
                // Doesn't account for blanks
            }
        }

        return currentAnimationCycles;
    }

    /**
     * Returns the specific sprite needed for the current frame
     * 
     * @param animationCycleKey should be the left side of the - (Walking,
     *                          Standing,etc.)
     * @return
     */

    public BufferedImage getAnimationSprite(String animationCycleKey) {
        switch (direction) {
            case UP:
                sprite.updateAnimationIndex(animationCycleKey + "-Upward");
                return sprite.spriteAnimationCycles.get(animationCycleKey + "-Upward").get(sprite.animationIndex);
            case DOWN:
                sprite.updateAnimationIndex(animationCycleKey + "-Downward");
                return sprite.spriteAnimationCycles.get(animationCycleKey + "-Downward").get(sprite.animationIndex);
            case LEFT:
                sprite.updateAnimationIndex(animationCycleKey + "-Left");
                return sprite.spriteAnimationCycles.get(animationCycleKey + "-Left").get(sprite.animationIndex);
            case RIGHT:
                sprite.updateAnimationIndex(animationCycleKey + "-Right");
                return sprite.spriteAnimationCycles.get(animationCycleKey + "-Right").get(sprite.animationIndex);

            default:
                direction = -1;
                return sprite.spriteAnimationCycles.get(animationCycleKey + "-Downward").get(0);

        }

    }

}