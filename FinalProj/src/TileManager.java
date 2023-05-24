import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class TileManager {
    GamePanel gamePanel;
    HashMap<Integer, Tile> tiles = new HashMap<Integer, Tile>();

    public int currentRow = 0;
    public int currentCol = 0;

    public String[][] map;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        setTileSprites();
        map = new String[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
    }

    // Get the entire Map
    // Set the screen to specific part of map
    // AS the player moves the screenX & screenY changes.

    private void setTileSprites() {

        File directoryPath = new File("./FinalProj/Sprites/Background-Tiles");

        String contents[] = directoryPath.list();

        for (String fileName : contents) {
            try {
                String tileName = fileName.substring(0, fileName.indexOf("."));
                boolean isCollision = setCollisionTile((tileName));

                Tile currentTile = new Tile(ImageIO.read(PathFinder.getFilePathForFile(fileName).toFile()),
                        tileName, isCollision);

                tiles.put(getTileKey(tileName), currentTile);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("Failed to find FilePathForFile: " + fileName);
                e.printStackTrace();

            }
        }
    }

    private int getTileKey(String tileName) {

        switch (tileName) {
            case "grass":
                return 0;
            case "wall":
                return 1;
            case "water":
                return 2;
            case "earth":
                return 3;
            case "tree":
                return 4;
            case "road":
                return 5;
            case "hut":
                return 6;
            case "table":
                return 7;
            case "floor":
                return 8;
            default:
                return -1;
        }
    }

    /**
     * Sets if a specific tile (tree, hut, etc) is
     * 
     * @param fileName path to tile sprite.png
     * @return isCollidable
     */

    private boolean setCollisionTile(String fileName) {
        switch (fileName) {
            case "wall":
                return true;
            case "hut":
                return true;
            case "water":
                return true;
            case "tree":
                return true;

            default:
                return false;
        }
    }

    /**
     * 
     * @param filePath path to map.txt file
     * @return 2D array of map.txt content
     */
    private String[][] getTextMapContent(String filePath) {
        Scanner scan = null;
        try {
            scan = new Scanner(new FileReader(filePath));

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ArrayList<String> lines = new ArrayList<String>();

        while (scan.hasNext()) {
            String line = scan.nextLine().replaceAll("\\s", "");
            lines.add(line);
        }
        scan.close();

        for (int i = 0; i < lines.size(); i++)
            for (int j = 0; j < lines.get(i).length(); j++) {

                map[i][j] = lines.get(i).charAt(j) + "";
            }

        return map;

    }

    public void updateMap(int[] position) {
        currentRow = position[0] / gamePanel.tileSize;
        currentCol = position[1] / gamePanel.tileSize;

    }

    /**
     * Draws map based on map.txt file
     * 
     * @param g2d               tool to draw all tiles on map
     * @param pathToTextfileMap path to map file.txt
     */

    public void draw(Graphics2D g2d, String pathToTextfileMap) {
        String[][] map = getTextMapContent(pathToTextfileMap);

        for (int row = 0; row < gamePanel.maxWorldRow; row++) {
            for (int col = 0; col < gamePanel.maxWorldCol; col++) {

                int worldX = col * gamePanel.tileSize;
                int worldY = row * gamePanel.tileSize;

                int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

                boolean isInScreenBoundsX = worldX + gamePanel.tileSize > gamePanel.player.worldX
                        - gamePanel.player.screenX
                        && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX;

                boolean isInScreenBoundsY = worldY + gamePanel.tileSize > gamePanel.player.worldY
                        - gamePanel.player.screenY
                        && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY;

                if (isInScreenBoundsX && isInScreenBoundsY) {
                    int numTileInMap = Integer.parseInt(map[row][col]);

                    g2d.drawImage(tiles.get(numTileInMap).sprite, screenX,
                            screenY, gamePanel.tileSize, gamePanel.tileSize, null);
                }
            }
        }

    }
}
