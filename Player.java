import java.awt.Color;
import java.awt.Graphics;

public class Player {
    int width;
    int height;
    int x;
    int y;
    int xVelocity;
    int yVelocity;
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
        this.xVelocity = 5;
        this.yVelocity = 5;
    }

    /**Called from GamePanel. */
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    /**Update the... well map based on input*/
    public void update() {
        if (this.movingUp && ! this.movingDown) {
            this.y -= this.yVelocity;
            this.movingUp = false;
        }
        System.out.println(this.x);
    }

    void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }
}
