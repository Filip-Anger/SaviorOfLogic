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

    final InputMap inputMap;
    final ActionMap actionMap;
    final int width;
    final int height;
    final int x;
    final int y;
    final int velocity;
    

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
        // Input map
        this.inputMap = frameInputMap;
        this.inputMap.put(KeyStroke.getKeyStroke("W"), "moveUp");
        this.inputMap.put(KeyStroke.getKeyStroke("S"), "moveDown");
        this.inputMap.put(KeyStroke.getKeyStroke("A"), "moveLeft");
        this.inputMap.put(KeyStroke.getKeyStroke("D"), "moveRight");

        // Input handle
        this.actionMap = frameActionMap;
        this.actionMap.put("moveUp", new UpAction());
        this.actionMap.put("moveDown", new DowAction());
        this.actionMap.put("moveLeft", new LeftAction());
        this.actionMap.put("moveRight", new RightAction());
    }

    /**Called from GamePanel. */
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    /**TODO COLISION.
    */
    public int yUpdate() {
        if (this.movingUp && ! this.movingDown) {
            this.movingUp = false;
            return -1 * this.velocity;
        } else if (this.movingDown && ! this.movingUp) {
            this.movingDown = false;
            return this.velocity;
        }
        else if (this.movingUp && this.movingDown) {
            this.movingUp = false;
            this.movingDown = false;
        }
        return 0;
    }

    /**TODO COLISION.
    */
    public int xUpdate() {
        if (this.movingLeft && ! this.movingRight) {
            this.movingLeft = false;
            return -1 * this.velocity;
        } else if (this.movingRight && ! this.movingLeft) {
            this.movingRight = false;
            return this.velocity;
        }
        else if (this.movingLeft && this.movingRight) {
            this.movingLeft = false;
            this.movingRight = false;
        }
        return 0;
    }


    /**Key press action.*/
    public class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingUp = true;
        }
    }

    /**Key press action.*/
    public class DowAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingDown = true;
        }
    }

    /**Key press action.*/
    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingLeft = true;
        }
    }

    /**Key press action.*/
    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingRight = true;
        }
    }
}


