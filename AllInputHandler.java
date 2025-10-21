
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

public class AllInputHandler {
    InputMap inputMap;
    ActionMap actionMap;

    public AllInputHandler(InputMap globalInputMap, ActionMap globalActionMap) {
        this.inputMap = globalInputMap;
        this.inputMap.put(KeyStroke.getKeyStroke("pressed W"), "moveUpPressed");
        this.inputMap.put(KeyStroke.getKeyStroke("released W"), "moveUpReleased");
        this.inputMap.put(KeyStroke.getKeyStroke("pressed S"), "moveDownPressed");
        this.inputMap.put(KeyStroke.getKeyStroke("released S"), "moveDownReleased");
        this.inputMap.put(KeyStroke.getKeyStroke("pressed A"), "moveLeftPressed");
        this.inputMap.put(KeyStroke.getKeyStroke("released A"), "moveLeftReleased");
        this.inputMap.put(KeyStroke.getKeyStroke("pressed D"), "moveRightPressed");
        this.inputMap.put(KeyStroke.getKeyStroke("released D"), "moveRightReleased");
        this.inputMap.put(KeyStroke.getKeyStroke("pressed I"), "InventoryPressed");
        this.inputMap.put(KeyStroke.getKeyStroke("pressed E"), "PickUpPressed");

    
        this.actionMap = globalActionMap;
        
    }

    public ActionMap getActionMap() {
        return this.actionMap;
    }
}
