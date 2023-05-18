import java.awt.Graphics2D;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class TileManager {
    GamePanel gamePanel;
    ArrayList<Tile> tiles = new ArrayList<Tile>();

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        setTileSprites();
    }

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
        String[][] map = new String[gamePanel.maxScreenRow][gamePanel.maxScreenCol];

        Scanner scan = null;
        try {
            scan = new Scanner(new FileReader(filePath));

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ArrayList<String> lines = new ArrayList<String>();

        while (scan.hasNext()) {
            String line = scan.nextLine();
            lines.add(line);
        }
        scan.close();

        for (int i = 0; i < lines.size(); i++)
            for (int j = 0; j < lines.get(i).length(); j++)
                map[i][j] = lines.get(i).charAt(j) + "";

        return map;

    }

    public void draw(Graphics2D g2d, String pathToTextfileMap) {
        String[][] map = getTextMapContent(pathToTextfileMap);

        for (int row = 0; row < gamePanel.maxScreenRow; row++)
            for (int col = 0; col < gamePanel.maxScreenCol; col++)
                g2d.drawImage(tiles.get(Integer.parseInt(map[row][col])).sprite, col * gamePanel.tileSize,
                        row * gamePanel.tileSize,
                        gamePanel.tileSize, gamePanel.tileSize, null);

    }

}
