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

    /**
     * Draws The Proper Sprite for the current frame
     * 
     * @param g2d needed for drawing the sprite on screen
     */

    public void draw(Graphics2D g2d) {

        boolean isStandingStill = !keyHandler.upPressed && !keyHandler.downPressed && !keyHandler.leftPressed
                && !keyHandler.rightPressed;

        displaySprite = isStandingStill ? getAnimationSprite("Standing") : getAnimationSprite("Walking");

        g2d.drawImage(displaySprite, xPos, yPos, gamePanel.tileSize, gamePanel.tileSize, null);

    }

}
