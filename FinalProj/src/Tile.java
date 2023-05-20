import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage sprite;
    public String name;

    public boolean doesCollide;

    public Tile(BufferedImage sprite, String name, boolean doesCollide) {
        this.sprite = sprite;
        this.name = name;
        this.doesCollide = doesCollide;

    }

    @Override
    public String toString() {
        return name + " " + doesCollide;
    }

}
