import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;
    String[] sprites;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, String[] sprites) {
        super(100, 100, 6, sprites);
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
    }

    public void draw(Graphics2D g2d) {

        BufferedImage displaySprite = null;

        switch (direction) {
            case UP:
                displaySprite = this.up1;
                break;
            case DOWN:
                displaySprite = this.up2;
                break;
            case LEFT:
                System.out.println(LEFT);
                break;
            case RIGHT:
                System.out.println(RIGHT);
                break;
            default:
                direction = -1;
        }

        g2d.drawImage(displaySprite, xPos, yPos, gamePanel.tileSize, gamePanel.tileSize, null);

    }

}
