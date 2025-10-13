import java.awt.BorderLayout;

import java.util.ArrayList;

import javax.swing.JFrame;

/**PUPET MASTER.
 * Creates a MODEL - GamePanel
 * Calls 
 */
class Game {

    public ArrayList<Item> allItems = new ArrayList<Item>();
    //hello still testing
    

    GamePanel gamePanel;
    JFrame frame;

    void start(int screenWidth, int screenHeight, int scale) {
        this.frame = new JFrame("Savior of the Logic");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(screenWidth, screenHeight);
        this.frame.setLayout(new BorderLayout());

        this.gamePanel = new GamePanel(screenWidth, screenHeight, scale, 0, 0);
        this.frame.add(this.gamePanel, BorderLayout.CENTER);

        this.frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        game.start(800, 640, 2);
    }
}