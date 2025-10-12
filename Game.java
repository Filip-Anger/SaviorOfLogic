import java.awt.BorderLayout;
import javax.swing.JFrame;

/**PUPET MASTER.
 * Creates a MODEL - GamePanel
 * Calls 
 */
class Game {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 640;
    public static final int SCRREN_WIDTH_PIX = 50;
    public static final int SCRREN_HEIGHT_PIX = 40;
    

    GamePanel gamePanel;
    JFrame frame;

    void start() {
        this.frame = new JFrame("Savior of the Logic");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.frame.setLayout(new BorderLayout());

        this.gamePanel = new GamePanel(50, 50);
        this.frame.add(this.gamePanel, BorderLayout.CENTER);

        this.frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}