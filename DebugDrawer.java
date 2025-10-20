import java.awt.Color;
import java.awt.Graphics;

public class DebugDrawer {
    int sizeX;
    int sizeY;
    public DebugDrawer() {
        this(5, 5);
    }

    public DebugDrawer(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
    public void drawDebug(Graphics g, int x, int y) {
        g.setColor(Color.RED);
        g.fillRect(x, y, this.sizeX, this.sizeY);
    }
}
