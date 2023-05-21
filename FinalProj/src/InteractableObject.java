import java.awt.Graphics2D;

public class InteractableObject {
    public Sprite sprite;
    public String name;
    public boolean isCollided = false;
    public int worldX, worldY;

    public InteractableObject(Sprite sprite, String name, int worldX, int worldY) {
        this.sprite = sprite;
        this.name = name;
        this.worldX = worldX;
        this.worldY = worldY;

    }

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

}
