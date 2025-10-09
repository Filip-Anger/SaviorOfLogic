import java.awt.Color;
import java.awt.Graphics;

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

    // Sprite??

    public Player() {
        this.width = 8;
        this.height = 16;
        this.x = Game.SCREEN_WIDTH / 2 - this.width / 2;
        this.y = Game.SCREEN_HEIGHT / 2 - this.height / 2;
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

    void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }
    void setMovingDown(boolean movingDown);

    void setMovingLeft(boolean movingLeft);

    void setMovingRight(boolean movingRight);

    int[] getPosition() {
        return new int[]{this.x, this.y}
    }
}
