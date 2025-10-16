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
    public static final int ORIGINAL_TILE = 16;

    public ArrayList<Item> allItems = new ArrayList<Item>();
    //hello still testing
    

    GamePanel gamePanel;
    JFrame frame;
    int fps;
    
    @Override
    public void run() {
        JFrame f = new JFrame("Savior of the Logic");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(WIDTH, HEIGHT);
        f.setLayout(new BorderLayout());
        GamePanel gp = new GamePanel(0, 0, this.fps);
        f.add(gp, BorderLayout.CENTER);

        this.frame = f;
        this.gamePanel = gp;
        f.setVisible(true);
        
        };

    public Game(int fps) {
        this.fps = fps;
        SwingUtilities.invokeLater(this);
    }  

    
    public static void main(String[] args) {
        Game game = new Game(60);
    }
}