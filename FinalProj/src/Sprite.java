import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Sprite {
    public String spritesheetFileName;
    public int width;
    public int height;
    public String[] animationCycleRowNames;
    public int[] spriteColumnSequence;
    HashMap<String, ArrayList<BufferedImage>> spriteAnimationCycles;
    public ArrayList<String> imageNames = new ArrayList<>();
    public int animationIndex = -1;

    /**
     * 
     * @param spritesheetFileName    fileName
     * @param dimensions             widthxheight of sprite
     * @param animationCycleRowNames name
     * @param spriteColumnSequence
     */
    public Sprite(String spritesheetFileName, int[] dimensions, String[] animationCycleRowNames,
            int[] spriteColumnSequence) {
        this.spritesheetFileName = spritesheetFileName;
        this.width = dimensions[0];
        this.height = dimensions[1];
        this.animationCycleRowNames = animationCycleRowNames;
        this.spriteColumnSequence = spriteColumnSequence;
        setSprites();

    }

    public Sprite(ArrayList<String> imageNames, int[] dimensions) {
        this.imageNames = imageNames;
        this.width = dimensions[0];
        this.height = dimensions[1];
        setSprites();

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

    private void initSpriteSheet() {
        // ImageIO.read(PathFinder(sprites[i]).toFile());
        ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
        try {
            for (String string : imageNames) {

                sprites.add(ImageIO.read(
                        PathFinder.getFilePathForFile(string).toFile()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("SpriteSheet Not Found!");
        }

    }

    private HashMap<String, ArrayList<BufferedImage>> initAnimationCycles(BufferedImage spriteSheet) {

        HashMap<String, ArrayList<BufferedImage>> currentAnimationCycles = new HashMap<String, ArrayList<BufferedImage>>();

        int numRows = animationCycleRowNames.length;

        for (int row = 0; row < numRows; row++) {

            currentAnimationCycles.put(animationCycleRowNames[row], new ArrayList<BufferedImage>());

            for (int col = 0; col < spriteColumnSequence[row]; col++) {
                int spriteXPos = col * width;
                int spriteYPos = row * height;
                // 120,130
                //
                currentAnimationCycles.get(animationCycleRowNames[row]).add(spriteSheet.getSubimage((spriteXPos),
                        (spriteYPos), width, height));
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

    private void setSprites() {

        // Intilize Sprite Sheet Image
        BufferedImage spriteSheetImage = initSpriteSheet(spritesheetFileName);

        // Get All Sprites and add them to a corrosponding HashMap(Row,
        // ArrayList<Sprites Associated w/ Animation> )
        spriteAnimationCycles = initAnimationCycles(spriteSheetImage);

    }

    /**
     * Gets sprite for animation, but only in one direction, used for interacables
     * 
     * @param animationCycleKey
     * @return
     */
    public BufferedImage getAnimationSprite(String animationCycleKey) {
        return spriteAnimationCycles.get(animationCycleKey).get(animationIndex);

    }

    /**
     * Used for getting the player animation sprite as it can face any direction
     * 
     * @param animationCycleKey
     * @param direction
     * @return
     */
    public BufferedImage getAnimationSprite(String animationCycleKey, int direction) {
        switch (direction) {
            case Entity.UP:
                updateAnimationIndex(animationCycleKey + "-Upward");
                return spriteAnimationCycles.get(animationCycleKey + "-Upward").get(animationIndex);
            case Entity.DOWN:
                updateAnimationIndex(animationCycleKey + "-Downward");
                return spriteAnimationCycles.get(animationCycleKey + "-Downward").get(animationIndex);
            case Entity.LEFT:
                updateAnimationIndex(animationCycleKey + "-Left");
                return spriteAnimationCycles.get(animationCycleKey + "-Left").get(animationIndex);
            case Entity.RIGHT:
                updateAnimationIndex(animationCycleKey + "-Right");
                return spriteAnimationCycles.get(animationCycleKey + "-Right").get(animationIndex);

            default:
                direction = -1;
                return spriteAnimationCycles.get(animationCycleKey + "-Downward").get(0);

        }
    }

    public void updateAnimationIndex(String animationCycleRowName) {
        boolean isNewDirection = animationIndex != 0;
        boolean shouldRepeatAnimation = animationIndex == spriteAnimationCycles.get(animationCycleRowName).size()
                - 1;

        if (isNewDirection)
            animationIndex = 0;
        else if (!isNewDirection && shouldRepeatAnimation)
            animationIndex = 0;
        else
            animationIndex++;

    }

}
