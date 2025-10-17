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
    final int velocity;
    
    /** Constructor.
     * 
     * @param frameInputMap map from the parent swing object
     * @param frameActionMap map from the parent swing object
    */
    public Entiti() {
        this.velocity = 3 * Game.SCALE;
        System.out.println(sizeX);
        System.out.println(sizeY);
        this.x = Game.WIDTH / 2 - this.sizeX / 2;
        this.y = Game.HEIGHT / 2 - this.sizeX / 2;

    }

    /**Called from GamePanel. */
    public void draw(Graphics g, int xMovment, int yMovment) {
        g.setColor(Color.RED);
        g.fillRect(x, y, this.sizeX, this.sizeY);
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

    public int getPlayerX() {
        return this.x;
    }

    public int getPlayerY() {
        return this.y;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY() {
        return this.sizeY;
    }


    // public boolean stateUpdate(){
    //     return inventoryState;
    // }


    // this.actionMap.put("InventoryPressed", new Inventory());
    
    // public class Inventory extends AbstractAction {
    //     @Override
    //     public void actionPerformed(ActionEvent e){
    //         if (inventoryState){
    //             inventoryState = false;
    //         } else{
    //             inventoryState = true;
    //         }
    //     }
    // }
}

    