import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

public class Entity {
    public int xPos, yPos;
    public int quickness;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public int direction;

    public final int LEFT = 0;
    public final int RIGHT = 1;
    public final int UP = 2;
    public final int DOWN = 3;

    public Entity(int xPos, int yPos, int quickness, String spriteSheetPath, int[] spriteDimensions) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.quickness = quickness;
        setSprites(spriteSheetPath, spriteDimensions);
    }

    private void setSprites(String spriteSheetPath, int[] spriteDimensions, String[] spriteRowNames) {

        BufferedImage spriteSheet = ImageIO.read(
                PathFinder.getFilePathForFile(spriteSheetPath).toFile());

        int imageWidth = spriteSheet.getWidth();
        int imageHeight = spriteSheet.getHeight();

        int numRows = imageHeight / spriteDimensions[0];
        int numCols = imageWidth / spriteDimensions[0];

        // ImageIO.read(PathFinder(sprites[i]).toFile());
        HashMap<String, ArrayList<BufferedImage>> animationCycles = new HashMap<String, ArrayList<BufferedImage>>();

        for (int row = 0; row < numRows; row++) {
            animationCycles.put(spriteRowNames[row], new ArrayList<BufferedImage>());
            for (int col = 0; col < numCols; col++) {
                animationCycles.get(spriteRowNames[row]).add(spriteSheet.getSubimage(((col * imageWidth) + imageWidth),
                        ((row * height) + height), imageWidth, imageHeight));
                // Doesn't account for blanks
            }
        }
    }

    class PathFinder {

        private static Path filepath;

        public static Path getFilePathForFile(String filename) {

            try {
                filepath = Files.walk(Paths.get("."))
                        .collect(Collectors.toList()).stream()
                        .filter(file -> !Files.isDirectory(file) &&
                                file.getFileName().toString().startsWith(filename))
                        .findFirst().get();
            } catch (IOException exception) {

            }
            return filepath;
        }
    }
}