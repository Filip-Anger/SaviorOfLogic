import java.awt.BorderLayout;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**PUPET MASTER.
 * Creates a MODEL - GamePanel
 * Calls 
 */
class Game {
    public static final int SCALE = 2;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 640;
    public static final int ORIGINAL_TILE = 16;

    public ArrayList<Item> allItems = new ArrayList<Item>();
    //hello still testing
    

    GamePanel gamePanel;
    JFrame frame;
    

    public Game(int fps) {
        Runnable start = new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("Savior of the Logic");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setSize(WIDTH, HEIGHT);
                f.setLayout(new BorderLayout());

                GamePanel gp = new GamePanel(-50, -50, fps);
                // f.add(gp, BorderLayout.CENTER);

                f.setVisible(true);
            }
            
        };
        SwingUtilities.invokeLater(start);
    }  


    
    public static void main(String[] args) {
        Game game = new Game(60);
    }
}