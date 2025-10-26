package com.savioroflogic.ui;

import java.awt.Color;
import java.awt.Graphics;


/**TODO: Class description. */
public class DebugDrawer {
    com.savioroflogic.util.Pair size;
    public DebugDrawer() {
        this(new com.savioroflogic.util.Pair(5, 5));
    }

    public DebugDrawer(com.savioroflogic.util.Pair size) {
        this.size = size;
    }
    public void drawDebug(Graphics g, com.savioroflogic.util.Pair coords) {
        g.setColor(Color.RED);
        g.fillRect(coords.x(), coords.y(), this.size.x(), this.size.y());
    }
}
