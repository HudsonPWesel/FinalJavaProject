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

    public final int LEFT = 0;
    public final int RIGHT = 1;
    public final int UP = 2;
    public final int DOWN = 3;
    public int animationIndex = -1;

    HashMap<String, ArrayList<BufferedImage>> spriteAnimationCycles;

    public Entity(int worldX, int worldY, int quickness, Sprite sprite) {
        this.worldX = worldY;
        this.worldY = worldY;
        this.quickness = quickness;

        setSprites(sprite);
    }

    private void setSprites(Sprite sprite) {

        // Intilize Sprite Sheet Image
        BufferedImage spriteSheetImage = initSpriteSheet(sprite.spritesheetFileName);

        // Get All Sprites and add them to a corrosponding HashMap(Row,
        // ArrayList<Sprites Associated w/ Animation> )
        this.spriteAnimationCycles = initAnimationCycles(spriteSheetImage, sprite);

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
                updateAnimationIndex(animationCycleKey + "-Upward");
                return this.spriteAnimationCycles.get(animationCycleKey + "-Upward").get(animationIndex);
            case DOWN:
                updateAnimationIndex(animationCycleKey + "-Downward");
                return this.spriteAnimationCycles.get(animationCycleKey + "-Downward").get(animationIndex);
            case LEFT:
                updateAnimationIndex(animationCycleKey + "-Left");
                return this.spriteAnimationCycles.get(animationCycleKey + "-Left").get(animationIndex);
            case RIGHT:
                updateAnimationIndex(animationCycleKey + "-Right");
                return this.spriteAnimationCycles.get(animationCycleKey + "-Right").get(animationIndex);

            default:
                direction = -1;
                return this.spriteAnimationCycles.get(animationCycleKey + "-Downward").get(0);

        }

    }

    public void updateAnimationIndex(String animationCycleRowName) {
        boolean isNewDirection = animationIndex != 0;
        boolean shouldRepeatAnimation = animationIndex == spriteAnimationCycles.get(animationCycleRowName).size() - 1;
        if (isNewDirection)
            animationIndex = 0;
        else if (!isNewDirection && shouldRepeatAnimation)
            animationIndex = 0;
        else
            animationIndex++;

    }

}