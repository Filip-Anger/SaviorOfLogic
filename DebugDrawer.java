import java.awt.Color;
import java.awt.Graphics;


/**TODO: Class description. */
public class DebugDrawer {
    Pair size;
    public DebugDrawer() {
        this(new Pair(5, 5));
    }

    public DebugDrawer(Pair size) {
        this.size = size;
    }
    public void drawDebug(Graphics g, Pair coords) {
        g.setColor(Color.RED);
        g.fillRect(coords.x(), coords.y(), this.size.x(), this.size.y());
    }
}
