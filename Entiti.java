import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.*;



/**TODO: Class description. */
public abstract class Entiti {
    boolean movingUp;
    boolean movingDown;
    boolean movingLeft;
    boolean movingRight;

    int sizeX;
    int sizeY;
    int x;
    int y;
    int velocity;
    
    /** Constructor.
     * 
     * @param frameInputMap map from the parent swing object
     * @param frameActionMap map from the parent swing object
    */

    /**Called from GamePanel. */
    public void draw(Graphics g, int xMovment, int yMovment) {
        g.setColor(Color.RED);
        g.fillRect(this.x, this.y, this.sizeX, this.sizeY);
    }

    public void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(this.x, this.y, this.sizeX, this.sizeY);
    }

    public int yUpdate() {
        if (this.movingUp && !this.movingDown) {
            return -this.velocity;
        } else if (this.movingDown && !this.movingUp) {
            return this.velocity;
        }
        // both pressed or neither pressed -> no vertical movement
        return 0;
    }


    public int xUpdate() {
        if (this.movingLeft && !this.movingRight) {
            return -this.velocity;
        } else if (this.movingRight && !this.movingLeft) {
            return this.velocity;
        }
        // both pressed or neither pressed -> no horizontal movement
        return 0;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY() {
        return this.sizeY;
    }
}

    