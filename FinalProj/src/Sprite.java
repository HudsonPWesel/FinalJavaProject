import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Sprite {
    public String spritesheetFileName;
    public int width;
    public int height;
    public String[] animationCycleRowNames;
    public int[] spriteColumnSequence;
    HashMap<String, ArrayList<BufferedImage>> spriteAnimationCycles;
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
    }

    public BufferedImage getAnimationSprite(String animationCycleKey) {
        return spriteAnimationCycles.get(animationCycleKey).get(animationIndex);

    }

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
