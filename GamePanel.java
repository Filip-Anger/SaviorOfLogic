import java.awt.Color;
import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**MODEL. DATA without BEHAVIOUR should be here.
 * Collect data from Player and provides it for TileMap 
*/
public class GamePanel extends JPanel {
    private Timer timer;
    private Player player;
    private int[][] tileMap;
    private InputMap inputMap;
    private ActionMap actionMap;
    public static final int TILE_SIZE = 16;


    public GamePanel() {
        // Player
        this.player = new Player();

        // Update timer
        this.timer = new Timer(16, e -> {
            this.update();
            this.repaint();
        });

        

        this.timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clean background

        TileMap.draw(g); // HELPER class to make it organized

        g.drawImage(this.tilePath, 100, 100, null);
        this.player.draw(g);
    }

    private void updateGameLogic() {

    }
}
