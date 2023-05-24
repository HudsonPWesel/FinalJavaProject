import java.util.ArrayList;
import java.util.HashMap;

public class AssetManager {
    public HashMap<String, ArrayList<InteractableObject>> interactables = new HashMap<String, ArrayList<InteractableObject>>();

    /**
     * @param interactable Any asset the player is supposed to be able to interact
     *                     with
     */
    public void addInteractable(InteractableObject interactable) {
        if (!interactables.containsKey(interactable.name)) {
            ArrayList<InteractableObject> tempObj = new ArrayList<InteractableObject>();
            tempObj.add(interactable);

            interactables.put(interactable.name, tempObj);

        } else
            interactables.get(interactable.name).add(interactable);

    }

}
