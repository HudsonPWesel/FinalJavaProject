import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;
    BufferedImage displaySprite = null;

    // Where to place character
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, String spriteSheetPath) {
        // Super must be the first line
        super(gamePanel.tileSize * 23,
                gamePanel.tileSize * 21, 6, new Sprite(spriteSheetPath,
                        new int[] { 120, 130 },
                        new String[] { "Standing-Downward", "Standing-Left", "Standing-Upward", "Standing-Right",
                                "Walking-Downward", "Walking-Left", "Walking-Upward", "Walking-Right" },
                        new int[] { 3, 3, 1, 3, 10, 10, 10, 10 }));

        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        super.direction = -1;
        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);
    }

    public void update() {

        if (keyHandler.upPressed) {
            worldY -= quickness;
            direction = this.UP;
        } else if (keyHandler.downPressed) {
            worldY += quickness;
            direction = this.DOWN;

        } else if (keyHandler.rightPressed) {
            worldX += quickness;
            direction = this.RIGHT;

        } else if (keyHandler.leftPressed) {
            worldX -= quickness;
            direction = this.LEFT;
        }
        gamePanel.tileManager.updateMap(new int[] { worldX, worldY });
    }

    /**
     * Draws The Proper Sprite for the current frame
     * 
     * @param g2d needed for drawing the sprite on screen
     * 
     */

    public void draw(Graphics2D g2d) {

        boolean isStandingStill = !keyHandler.upPressed && !keyHandler.downPressed && !keyHandler.leftPressed
                && !keyHandler.rightPressed;

        displaySprite = isStandingStill ? getAnimationSprite("Standing") : getAnimationSprite("Walking");

        g2d.drawImage(displaySprite, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

    }

}
