import java.awt.BorderLayout;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**PUPET MASTER.
 * Creates a MODEL - GamePanel
 * Calls 
 */
class Game implements Runnable{
    public static final int SCALE = 2;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 640;
    public static final int MAP_RESOLTION = 16;
    public static final int SPRITE_RESOLUTION = 32;
    public static final int SUBWINDOW_BONUS_SIZE = 30;
    public static final int PLAYER_ACCELERATION = 50;
    public static final int PROXIMITY_DIS = 10;
    
    


    GamePanel gamePanel;
    JFrame frame;
    int fps;

    public Game(int fps) {
        this.fps = fps;
        SwingUtilities.invokeLater(this);
    }

    @Override
    public void run() {
        this.frame = new JFrame("Savior of the Logic");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(WIDTH, HEIGHT);
        this.frame.setLayout(new BorderLayout());

        this.gamePanel = new GamePanel(400  , 100, 60);
        this.frame.add(this.gamePanel, BorderLayout.CENTER);

        this.frame.setVisible(true);
    }

    
    public static void main(String[] args) {
        Game game = new Game(60);
    }
}