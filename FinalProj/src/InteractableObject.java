import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class InteractableObject {
    public BufferedImage image;
    public String name;
    public boolean isCollided = false;
    public int worldX, worldY;

    public InteractableObject(BufferedImage image, String name, int worldX, int worldY) {
        this.image = image;
        this.name = name;
        this.worldX = worldX;
        this.worldY = worldY;

    }

    public void draw(Graphics2D g2d, GamePanel gamePanel, InteractableObject chest) {

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        boolean isInScreenBoundsX = worldX + gamePanel.tileSize > gamePanel.player.worldX
                - gamePanel.player.screenX
                && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX;

        boolean isInScreenBoundsY = worldY + gamePanel.tileSize > gamePanel.player.worldY
                - gamePanel.player.screenY
                && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY;

        if (isInScreenBoundsX && isInScreenBoundsY) {

            g2d.drawImage(image, screenX,
                    screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

}
