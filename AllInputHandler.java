
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

public class AllInputHandler {
    InputMap inputMap;
    ActionMap actionMap;


    boolean dragging = false;
    int mouseClickX;
    int mouseClickY;
    int mouseDragX;
    int mouseDragY;

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
        this.inputMap.put(KeyStroke.getKeyStroke("pressed F"), "SubmitPressed");

    
        this.actionMap = globalActionMap;
        
    }

    public ActionMap getActionMap() {
        return this.actionMap;
    }

    public int getMouseClickX() {
        return mouseClickX;
    }
    public int getMouseClickY() {
        return mouseClickY;
    }
    public int getMouseDragX() {
        return mouseDragX;
    }
    public int getMouseDragY() {
        return mouseDragY;
    }
    public boolean isDragging() {
        return dragging;
    }


    public class ClickListener extends MouseAdapter {
        public void mousePressed (MouseEvent e){
            mouseClickX = e.getX();
            mouseClickY = e.getY();
            mouseDragX = mouseClickX;
            mouseDragY = mouseClickY;
            dragging = true;
            System.out.println("Pressed at " + mouseClickX + " " + mouseClickY);
        }
        
    }
    public class ReleaseListener extends MouseAdapter {
        public void mouseReleased (MouseEvent e){
            dragging = false;
            //System.out.println("Released");
        }
        
    }

    public class DragListener extends MouseMotionAdapter{
        public void mouseDragged(MouseEvent e){
            mouseDragX = e.getX();
            mouseDragY = e.getY();
            //System.out.println("Drag at " + mouseDragX + " " + mouseDragY);
        }
    }

}
