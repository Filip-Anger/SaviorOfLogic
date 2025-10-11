import java.awt.Graphics;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

/**MODEL. DATA without BEHAVIOUR should be here.
 * Collect data from Player and provides it for TileMap
 * Everything is drawn by paintComponent - using helper classes
*/
public class GamePanel extends JPanel {
    private final Timer timer;
    private final Player player;
    private final TileMap tileMap;
    private int offsetX;
    private int offsetY;

    public static final int TILE_SIZE = 16;


    public GamePanel(int startX, int startY) {
        // Player
        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();
        this.player = new Player(inputMap, actionMap);

        // TileMap
        this.tileMap = new TileMap(this.TILE_SIZE);
        // World offset
        this.offsetX = startX;
        this.offsetY = startY;

        // Update timer
        this.timer = new Timer(16, e -> {
            this.logicUpdate();  // Change the model

            this.repaint(); // Display the model
        });

        

        this.timer.start();
    }

    private void logicUpdate() {
        playerMovment();
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clean background

        this.tileMap.draw(g, this.offsetX, this.offsetY); // HELPER class to make it organized

        this.player.draw(g);
    }

    private void playerMovment() {
        this.offsetX += this.player.xUpdate();
        this.offsetY += this.player.yUpdate();
    }
}
