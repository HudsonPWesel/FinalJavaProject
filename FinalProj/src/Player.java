import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;
    BufferedImage displaySprite = null;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, String spriteSheetPath) {
        // Super must be the first line
        super(100, 100, 6, new Sprite(spriteSheetPath,
                new int[] { 120, 130 },
                new String[] { "Standing-Downward", "Standing-Left", "Standing-Upward", "Standing-Right",
                        "Walking-Downward", "Walking-Left", "Walking-Upward", "Walking-Right" },
                new int[] { 3, 3, 1, 3, 10, 10, 10, 10 }));

        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        super.direction = -1;
    }

    public void update() {

        if (keyHandler.upPressed) {
            yPos -= quickness;
            direction = this.UP;
        } else if (keyHandler.downPressed) {
            yPos += quickness;
            direction = this.DOWN;

        } else if (keyHandler.rightPressed) {
            xPos += quickness;
            direction = this.RIGHT;

        } else if (keyHandler.leftPressed) {
            xPos -= quickness;
            direction = this.LEFT;
        }
        // todo FIX ANIMATION
    }

    public void draw(Graphics2D g2d) {

        switch (direction) {
            case UP:
                updateAnimationIndex("Walking-Upward");
                displaySprite = this.spriteAnimationCycles.get("Walking-Upward").get(animationIndex);
                break;
            case DOWN:
                updateAnimationIndex("Walking-Downward");
                displaySprite = this.spriteAnimationCycles.get("Walking-Downward").get(animationIndex);
                break;
            case LEFT:
                updateAnimationIndex("Walking-Left");
                displaySprite = this.spriteAnimationCycles.get("Walking-Left").get(animationIndex);
                break;
            case RIGHT:
                updateAnimationIndex("Walking-Right");
                displaySprite = this.spriteAnimationCycles.get("Walking-Right").get(animationIndex);
                break;
            default:
                direction = -1;
                displaySprite = this.spriteAnimationCycles.get("Standing-Downward").get(0);
        }

        boolean isStandingStill = !keyHandler.upPressed && !keyHandler.downPressed && !keyHandler.leftPressed
                && !keyHandler.rightPressed;

        if (isStandingStill) {
            switch (direction) {
                case UP:
                    updateAnimationIndex("Standing-Upward");
                    displaySprite = this.spriteAnimationCycles.get("Standing-Upward").get(animationIndex);
                    break;
                case DOWN:
                    updateAnimationIndex("Standing-Downward");
                    displaySprite = this.spriteAnimationCycles.get("Standing-Downward").get(animationIndex);
                    break;
                case LEFT:
                    updateAnimationIndex("Standing-Left");

                    displaySprite = this.spriteAnimationCycles.get("Standing-Left").get(animationIndex);
                    break;
                case RIGHT:
                    updateAnimationIndex("Standing-Right");
                    displaySprite = this.spriteAnimationCycles.get("Standing-Right").get(animationIndex);

                    break;
                default:
                    direction = -1;
                    displaySprite = this.spriteAnimationCycles.get("Standing-Downward").get(0);
            }

        }

        g2d.drawImage(displaySprite, xPos, yPos, gamePanel.tileSize, gamePanel.tileSize, null);

    }

    private void updateAnimationIndex(String animationCycleRowName) {
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
