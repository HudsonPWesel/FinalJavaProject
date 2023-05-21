
public class CollisionChecker {
    public GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Main collision detection system
     * 
     * @param entity
     */
    public void checkTile(Entity entity) {
        int entityLeftX = entity.worldX + entity.hitbox.x;
        int entityRightX = entity.worldX + entity.hitbox.x + entity.hitbox.width;

        int entityTopY = entity.worldY + entity.hitbox.y;
        int entityBottomY = entity.worldY + entity.hitbox.y + entity.hitbox.height;

        int entityLeftCol = entityLeftX / gamePanel.tileSize;
        int entityRightCol = entityRightX / gamePanel.tileSize;

        int entityTopRow = entityTopY / gamePanel.tileSize;
        int entityBottomRow = entityBottomY / gamePanel.tileSize;

        // Only two points of contact

        int tileNum1, tileNum2;

        switch (entity.direction) {
            // going one frame into the future to determine row and col
            case Entity.UP:
                entityTopRow = (entityTopY - entity.quickness) / gamePanel.tileSize;

                tileNum1 = Integer.parseInt(gamePanel.tileManager.map[entityTopRow][entityLeftCol]);
                tileNum2 = Integer.parseInt(gamePanel.tileManager.map[entityTopRow][entityRightCol]);

                if (gamePanel.tileManager.tiles.get(tileNum1).doesCollide
                        || gamePanel.tileManager.tiles.get(tileNum2).doesCollide)
                    entity.isCollided = true;

                break;
            case Entity.DOWN:
                entityBottomRow = (entityBottomY - entity.quickness) / gamePanel.tileSize;
                tileNum1 = Integer.parseInt(gamePanel.tileManager.map[entityBottomRow][entityLeftCol]);
                tileNum2 = Integer.parseInt(gamePanel.tileManager.map[entityBottomRow][entityRightCol]);
                if (gamePanel.tileManager.tiles.get(tileNum1).doesCollide
                        || gamePanel.tileManager.tiles.get(tileNum2).doesCollide)
                    entity.isCollided = true;
                break;

            case Entity.LEFT:

                entityLeftCol = (entityLeftX - entity.quickness) / gamePanel.tileSize;
                tileNum1 = Integer.parseInt(gamePanel.tileManager.map[entityTopRow][entityLeftCol]);
                tileNum2 = Integer.parseInt(gamePanel.tileManager.map[entityBottomRow][entityLeftCol]);

                if (gamePanel.tileManager.tiles.get(tileNum1).doesCollide
                        || gamePanel.tileManager.tiles.get(tileNum2).doesCollide)
                    entity.isCollided = true;
                break;

            case Entity.RIGHT:

                entityLeftCol = (entityRightX - entity.quickness) / gamePanel.tileSize;
                tileNum1 = Integer.parseInt(gamePanel.tileManager.map[entityTopRow][entityRightCol]);
                tileNum2 = Integer.parseInt(gamePanel.tileManager.map[entityBottomRow][entityRightCol]);

                if (gamePanel.tileManager.tiles.get(tileNum1).doesCollide
                        || gamePanel.tileManager.tiles.get(tileNum2).doesCollide)
                    entity.isCollided = true;
                break;

            default:
                break;
        }

    }

}
