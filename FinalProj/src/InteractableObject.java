import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class InteractableObject {
    public Sprite sprite;
    public String name;
    public boolean isCollided = false;
    public int worldX, worldY;
    public BufferedImage singleSprite;
    public boolean isAlive;

    /**
     * 
     * @param singleSprite A sprite
     * @param name         Entity's name
     * @param worldX
     * @param worldY
     */
    public InteractableObject(Sprite sprite, String name, int worldX, int worldY) {
        this.sprite = sprite;
        this.name = name;
        this.worldX = worldX;
        this.worldY = worldY;

    }

    /**
     * 
     * @param singleSprite A sprite
     * @param name         Entity's name
     * @param worldX
     * @param worldY
     */
    public InteractableObject(BufferedImage singleSprite, String name, int worldX, int worldY) {
        this.singleSprite = singleSprite;
        this.name = name;
        this.worldX = worldX;
        this.worldY = worldY;

    }

    /**
     * Draws between animation sprites instead of one
     * 
     * @param g2d
     * @param gamePanel
     * @param animationCycleKey which animaiton cycle you would like to access
     */
    public void draw(Graphics2D g2d, GamePanel gamePanel, String animationCycleKey) {

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        boolean isInScreenBoundsX = worldX + gamePanel.tileSize > gamePanel.player.worldX
                - gamePanel.player.screenX
                && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX;

        boolean isInScreenBoundsY = worldY + gamePanel.tileSize > gamePanel.player.worldY
                - gamePanel.player.screenY
                && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY;

        if (isInScreenBoundsX && isInScreenBoundsY) {
            sprite.animationIndex++;
            g2d.drawImage(sprite.getAnimationSprite(animationCycleKey), screenX,
                    screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

    /**
     * Draws the image if it is the bounds of the screen
     * 
     * @param g2d       tool to draw
     * @param gamePanel game screen
     */

    public void draw(Graphics2D g2d, GamePanel gamePanel) {

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        boolean isInScreenBoundsX = worldX + gamePanel.tileSize > gamePanel.player.worldX
                - gamePanel.player.screenX
                && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX;

        boolean isInScreenBoundsY = worldY + gamePanel.tileSize > gamePanel.player.worldY
                - gamePanel.player.screenY
                && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY;

        if (isInScreenBoundsX && isInScreenBoundsY) {

            g2d.drawImage(singleSprite, screenX,
                    screenY, gamePanel.tileSize * 2, gamePanel.tileSize * 2, null);
        }
    }

}
