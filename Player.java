import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.*;



/**TODO: Class description. */
public class Player {
    boolean movingUp;
    boolean movingDown;
    boolean movingLeft;
    boolean movingRight;

    int gameState;

    final InputMap inputMap;
    final ActionMap actionMap;
    final int width;
    final int height;
    final int x;
    final int y;
    final int velocity;
    
    final int titleState = 0;
    final int playState = 1;
    final int inventoryState = 2;
    final int dialogueState = 3;
    

    // Sprite??
    /** Constructor.
     * 
     * @param frameInputMap map from the parent swing object
     * @param frameActionMap map from the parent swing object
    */
    public Player(InputMap frameInputMap, ActionMap frameActionMap) {
        this.width = 8;
        this.height = 16;
        this.velocity = 5;
        this.x = Game.SCREEN_WIDTH / 2 - this.width / 2;
        this.y = Game.SCREEN_HEIGHT / 2 - this.height / 2;
        // Input map: use pressed/released so movement is continuous while holding keys
        this.inputMap = frameInputMap;
        this.inputMap.put(KeyStroke.getKeyStroke("pressed W"), "moveUpPressed");
        this.inputMap.put(KeyStroke.getKeyStroke("released W"), "moveUpReleased");
        this.inputMap.put(KeyStroke.getKeyStroke("pressed S"), "moveDownPressed");
        this.inputMap.put(KeyStroke.getKeyStroke("released S"), "moveDownReleased");
        this.inputMap.put(KeyStroke.getKeyStroke("pressed A"), "moveLeftPressed");
        this.inputMap.put(KeyStroke.getKeyStroke("released A"), "moveLeftReleased");
        this.inputMap.put(KeyStroke.getKeyStroke("pressed D"), "moveRightPressed");
        this.inputMap.put(KeyStroke.getKeyStroke("released D"), "moveRightReleased");
        this.inputMap.put(KeyStroke.getKeyStroke("pressed I"), "InventoryPressed");


        // Input handlers
        this.actionMap = frameActionMap;
        this.actionMap.put("moveUpPressed", new UpPressAction());
        this.actionMap.put("moveUpReleased", new UpReleaseAction());
        this.actionMap.put("moveDownPressed", new DownPressAction()); // keep existing Down press class (typo kept)
        this.actionMap.put("moveDownReleased", new DownReleaseAction());
        this.actionMap.put("moveLeftPressed", new LeftPressAction());
        this.actionMap.put("moveLeftReleased", new LeftReleaseAction());
        this.actionMap.put("moveRightPressed", new RightPressAction());
        this.actionMap.put("moveRightReleased", new RightReleaseAction());
        this.actionMap.put("InventoryPressed", new Inventory());
    }

    /**Called from GamePanel. */
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    /**TODO COLISION.
    */
    public int yUpdate() {
        if (this.movingUp && !this.movingDown) {
            return -this.velocity;
        } else if (this.movingDown && !this.movingUp) {
            return this.velocity;
        }
        // both pressed or neither pressed -> no vertical movement
        return 0;
    }

    /**TODO COLISION.
    */
    public int xUpdate() {
        if (this.movingLeft && !this.movingRight) {
            return -this.velocity;
        } else if (this.movingRight && !this.movingLeft) {
            return this.velocity;
        }
        // both pressed or neither pressed -> no horizontal movement
        return 0;
    }

    public int stateUpdate(){
        return gameState;
    }


    public class UpPressAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingUp = true;
        }
    }


    public class DownPressAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingDown = true;
        }
    }

    
    public class LeftPressAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingLeft = true;
        }
    }


    public class RightPressAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingRight = true;
        }
    }


    public class UpReleaseAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingUp = false;
        }
    }

    public class DownReleaseAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingDown = false;
        }
    }

    public class LeftReleaseAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingLeft = false;
        }
    }

    public class RightReleaseAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingRight = false;
        }
    }
    public class Inventory extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e){
            if (gameState == inventoryState){
                gameState = playState;
            } else{
                gameState = inventoryState;
            }
        }
    }
}

    