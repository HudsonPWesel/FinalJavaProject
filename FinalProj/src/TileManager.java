import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class TileManager {
    GamePanel gamePanel;
    ArrayList<Tile> tiles = new ArrayList<Tile>();

    public int currentRow = 0;
    public int currentCol = 0;
    public final int CAMERA_WIDTH = 5;
    public final int CAMERA_HEIGHT = 5;

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

                tiles.add(new Tile(ImageIO.read(PathFinder.getFilePathForFile(fileName).toFile()),
                        fileName.substring(0, fileName.indexOf("."))));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("Failed to find FilePathForFile: " + fileName);
                e.printStackTrace();

            }
        }

    }

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

                if (isInScreenBoundsX && isInScreenBoundsY)
                    g2d.drawImage(tiles.get(Integer.parseInt(map[row][col])).sprite, screenX,
                            screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }
        }

    }
}
