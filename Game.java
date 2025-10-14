import java.awt.BorderLayout;

import java.util.ArrayList;

import javax.swing.JFrame;

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

    void start() {
        this.frame = new JFrame("Savior of the Logic");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(WIDTH, HEIGHT);
        this.frame.setLayout(new BorderLayout());

        this.gamePanel = new GamePanel(-50, -50);
        this.frame.add(this.gamePanel, BorderLayout.CENTER);

        this.frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}