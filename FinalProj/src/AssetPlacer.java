import java.util.ArrayList;

public class AssetPlacer {
    public ArrayList<InteractableObject> interactables;
    public GamePanel gamePanel;

    public AssetPlacer(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.interactables = new ArrayList<>();
        // Just want to draw it on the screen and use collisoin to interact
    }
}
