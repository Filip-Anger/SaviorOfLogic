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

    protected  Pair size;
    protected  Pair screenPosition;
    protected  Pair absolutePosition;
    int velocity;
    
    public Entiti() {
        this.size = new Pair(Game.SPRITE_RESOLUTION, Game.SPRITE_RESOLUTION);
        this.absolutePosition = new Pair(20, 200);
    }
    /** Constructor.
     * 
     * @param frameInputMap map from the parent swing object
     * @param frameActionMap map from the parent swing object
    */

    /**Called from GamePanel. */
    public void draw(Graphics g, Pair newPosition) {
        this.absolutePosition = newPosition;
        g.setColor(Color.RED);
        g.fillRect(this.screenPosition.x(), this.screenPosition.y(), this.size.x(), this.size.x());
    }

    public void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(this.screenPosition.x(), this.screenPosition.y(), this.size.x(), this.size.y());
    }

    public Pair inputUpdate() {
        int x = 0;
        int y = 0;
        if (this.movingUp && !this.movingDown) {
            y = -this.velocity;
        } else if (this.movingDown && !this.movingUp) {
            y = this.velocity;
        }
        if (this.movingLeft && !this.movingRight) {
            x = -this.velocity;
        } else if (this.movingRight && !this.movingLeft) {
            x = this.velocity;
        }
        // both pressed or neither pressed -> no vertical movement
        return new Pair(x, y);
    }

    public Pair getScreenPosition() {
        return this.screenPosition;
    }

    public Pair getSize() {
        return this.size;
    }

    public Pair getAbsotulePosition() {
        return this.absolutePosition;
    }
}

    