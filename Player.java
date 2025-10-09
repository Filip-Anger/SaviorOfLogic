import java.awt.Color;
import java.awt.Graphics;
import java.awt.Desktop.Action;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class Player {
    int width;
    int height;
    int x;
    int y;
    int hp;
    boolean movingUp;
    boolean movingDown;
    boolean movingLeft;
    boolean movingRight;
    InputMap inputMap;
    ActionMap actionMap;

    // Sprite??

    public Player(InputMap frameInputMap, ActionMap frameActionMap) {
        this.width = 8;
        this.height = 16;
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
        this.actionMap.put("moveUp", setMovingUp(movingDown));
    }

    /**Called from GamePanel. */
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    /**Update the... well map based on input*/
    public void update() {
        if (this.movingUp && ! this.movingDown) {
            this.movingUp = false;
        } else if (this.movingDown && ! this.movingUp) {
            
        }
    }

    Action setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }
    void setMovingDown(boolean movingDown);

    void setMovingLeft(boolean movingLeft);

    void setMovingRight(boolean movingRight);

    int[] getPosition() {
        return new int[]{this.x, this.y}
    }
}
