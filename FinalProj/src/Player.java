import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;
    BufferedImage displaySprite = null;

    // Where to place character
    public final int screenX;
    public final int screenY;

    public ArrayList<BufferedImage> hearts = new ArrayList<BufferedImage>();
    public int health = 6;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, String spriteSheetPath) {
        // Super must be the first line
        super(gamePanel.tileSize * 21,
                gamePanel.tileSize * 23, 5, new Rectangle(15, 15, 14, 24),
                new Sprite(spriteSheetPath,
                        new int[] { 120, 130 },
                        new String[] { "Standing-Downward", "Standing-Left", "Standing-Upward", "Standing-Right",
                                "Walking-Downward", "Walking-Left", "Walking-Upward", "Walking-Right" },
                        new int[] { 3, 3, 1, 3, 10, 10, 10, 10 }));

        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        super.direction = -1;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        initHearts();

    }

    private void initHearts() {
        ArrayList<String> hearts_files = new ArrayList<String>(
                Arrays.asList(new String[] { "heart_blank", "heart_half", "heart_full" }));

        for (String heartPath : hearts_files) {
            try {
                hearts.add(ImageIO.read(
                        PathFinder.getFilePathForFile(heartPath).toFile()));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    public void update() {

        if (keyHandler.upPressed)
            direction = Entity.UP;

        else if (keyHandler.downPressed)
            direction = Entity.DOWN;

        else if (keyHandler.rightPressed)
            direction = Entity.RIGHT;

        else if (keyHandler.leftPressed)
            direction = Entity.LEFT;

        // Check for collideable
        isCollided = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.checkForDamage();

        updateCameraPos();
        gamePanel.tileManager.updateMap(new int[] { worldX, worldY });
    }

    private void updateCameraPos() {
        boolean isStandingStill = !keyHandler.upPressed && !keyHandler.downPressed && !keyHandler.leftPressed
                && !keyHandler.rightPressed;

        if (!isCollided && !isStandingStill) {
            switch (direction) {
                case Entity.UP:
                    worldY -= quickness;
                    break;
                case Entity.DOWN:
                    worldY += quickness;
                    break;
                case Entity.RIGHT:
                    worldX += quickness;
                    break;
                case Entity.LEFT:
                    worldX -= quickness;
                    break;

                default:
                    break;
            }
        }
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

        displaySprite = isStandingStill ? sprite.getAnimationSprite("Standing", direction)
                : sprite.getAnimationSprite("Walking", direction);

        g2d.drawImage(displaySprite, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

    }

}
