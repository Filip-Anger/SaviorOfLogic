import com.sun.source.tree.YieldTree;
import java.awt.Graphics;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.XMLFormatter;


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
    private final int playerX;
    private final int playerY;
    private final int playerSize;


    public static final int TILE_SIZE = 16;

    //Game state
    public boolean inventoryState = false;

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
        this.playerX = this.player.getPlayerX();
        this.playerY = this.player.getPlayerY();
        this.playerSize = this.player.getSize();

        // TileMap
        this.tileMap = new TileMap(this);
        // World offset
        this.offsetX = startX;
        this.offsetY = startY;

        // ItemSpawner
        this.itemSpawner = new ItemSpawner();
        // Inventory
        this.inventory = new Inventory();
        
        // Update timer
        this.timer = new Timer(8, e -> {
            this.logicUpdate();  // Change the model
            this.repaint(); // Display the model
        });

        

        this.timer.start();
    }

    private void logicUpdate() {
        playerState();
        playerMovment();
        
        
        

    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clean background
        
        
        //System.out.println("Xoffset: " + this.offsetX + "    Yoffset: " + this.offsetY);
        this.tileMap.draw(g, this.offsetX, this.offsetY); // HELPER class to make it organized

        this.player.draw(g);
        
        this.itemSpawner.drawItems(g, this.offsetX, this.offsetY);
        if (inventoryState){
            this.inventory.draw(g);
        }
        
        
    }

    private void playerMovment() {
        int xInput = this.player.xUpdate();
        int yInput = this.player.yUpdate();
        if (xInput != 0 && yInput != 0) {
            xInput = Math.round((float) (xInput / Math.sqrt(2)));
            yInput = Math.round((float) (yInput / Math.sqrt(2)));
            if (xInput > 0) {
                xInput += 1;
            } else {
                xInput -= 1;    
            }
            if (yInput > 0) {
                yInput += 1;
            } else {
                yInput -= 1;
            }
        }
        //TODO: COLLISION
        // Left top corner
        int posOnMapX = this.offsetX + this.playerX;
        int posOnMapY = this.offsetY + this.playerY;
        if (this.tileMap.canWalkOn(posOnMapX + xInput, posOnMapY + yInput, 
            this.playerSize, this.playerSize)) {
            this.offsetX += xInput;
            this.offsetY += yInput;
        } else {
            // this.tileMap.snapToEdge(xInput, yInput, posOnMapX, posOnMapY);
        }

    }

    public void setPlayerX(int playerX) {
        this.offsetX = playerX - Game.WIDTH / 2 + this.playerSize / 2;
    }

    public void setPlayerY(int playerY) {
        this.offsetY = playerY - Game.HEIGHT / 2 + this.playerSize / 2;
    }

    private void playerState(){
        this.inventoryState = this.player.stateUpdate();
    }
    
    //private void inventoryMovement() 
}
