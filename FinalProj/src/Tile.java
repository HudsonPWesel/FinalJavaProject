import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage sprite;
    public String name;

    public boolean isCollided = false;

    public Tile(BufferedImage sprite, String name) {
        this.sprite = sprite;
        this.name = name;

    }

    @Override
    public String toString() {
        return name;
    }

}
