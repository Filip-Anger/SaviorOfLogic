import java.awt.Graphics;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


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
    private final Inventory inventory;
    private final ItemSpawner itemSpawner;
    private int offsetX;
    private int offsetY;

    public static final int TILE_SIZE = 16;

    //Game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int inventoryState = 2;
    public final int dialogueState = 3;

    public final int propItem = 0;

    public GamePanel(int startX, int startY) {
        // Player
        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                System.out.println(e.getX()+" "+ e.getY());
                inventory.hoverItem(e.getX(), e.getY());
            }

        });

        this.player = new Player(inputMap, actionMap);

        // TileMap
        this.tileMap = new TileMap();
        // World offset
        this.offsetX = startX;
        this.offsetY = startY;

        // ItemSpawner
        this.itemSpawner = new ItemSpawner();
        // Inventory
        this.inventory = new Inventory();
        // set the game state
        gameState = playState;
        // Update timer
        this.timer = new Timer(16, e -> {
            this.logicUpdate();  // Change the model
            this.repaint(); // Display the model
        });

        

        this.timer.start();
    }

    private void logicUpdate() {
        playerState();
        playerMovment();
        
        switch (gameState) {
            case playState:
                
                
                break;
            
            case inventoryState:

                break;

            case dialogueState:

                break;
        
            default:
                break;
        }
        

    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clean background
            switch (gameState) {
            
            
            case inventoryState:
                

                break;

            case dialogueState:

                break;
        
            default:
                break;
        }
        
        System.out.println("Xoffset: " + this.offsetX + "    Yoffset: " + this.offsetY);
        this.tileMap.draw(g, this.offsetX, this.offsetY); // HELPER class to make it organized

        this.player.draw(g);
        
        this.itemSpawner.drawItems(g, this.offsetX, this.offsetY);
        // this.inventory.draw(g);
        
        
    }

    private void playerMovment() {
        this.offsetX += this.player.xUpdate();
        this.offsetY += this.player.yUpdate();
    }

    private void playerState(){
        this.gameState = this.player.stateUpdate();
    }
    
    //private void inventoryMovement() 
}
