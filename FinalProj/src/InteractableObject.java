
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

}
