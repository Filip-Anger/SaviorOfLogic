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
    private final PlayerSprite player;
    private final TileMap tileMap;
    private final Inventory inventory;
    private final ItemSpawner itemSpawner;
    private int offsetX;
    private int offsetY;
    private int playerMovmentX = 0;
    private int playerMovmentY = 0;
    private final int playerX;
    private final int playerY;
    private final int playerSizeX;
    private final int playerSizeY;
    private AllInputHandler inputHandler;
    private long lastFrameTime;

    public static final int TILE_SIZE = 16;

    //Game state
    public boolean inventoryState = false;

    public final int propItem = 0;

    public GamePanel(int startX, int startY, int fps) {
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

        inputHandler = new AllInputHandler(inputMap, actionMap);
        PlayerSprite playerSprite = new PlayerSprite(actionMap);
        this.player = playerSprite;
        this.playerSizeX = this.player.getSizeX();
        this.playerSizeY = this.player.getSizeY();
        this.playerX = Game.WIDTH / 2 - playerSizeX / 2;
        this.playerY = Game.HEIGHT / 2 - playerSizeY / 3 * 2;


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
        this.timer = new Timer((int) Math.round(1000.0 / fps), e -> {
            this.logicUpdate();  // Change the model
            this.repaint(); // Display the model
        });

        
        this.timer.start();
    }

    private void logicUpdate() {
        
        
        playerStateUpdate();
        playerMovment();
    
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clean background
        // double betweenLast = (System.nanoTime() - this.lastFrameTime);
        // System.out.println("FPS: " + 1 / (betweenLast / Math.pow(10, 9)));
        
        this.tileMap.draw(g, this.offsetX, this.offsetY); // HELPER class to make it organized


        this.player.draw(g, this.playerMovmentX, this.playerMovmentY);
        this.playerMovmentX = 0;
        this.playerMovmentY = 0;
        

        this.itemSpawner.drawItems(g, this.offsetX, this.offsetY);
        if (inventoryState){
            this.inventory.draw(g);
        }
        
        this.lastFrameTime = System.nanoTime();
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

        if (xInput != 0) {
            this.playerMovmentX = this.tileMap.tryAndMoveX(posOnMapX, posOnMapY, xInput, this.playerSizeX, this.playerSizeY);
            this.offsetX += this.playerMovmentX;
            posOnMapX = this.offsetX + this.playerX;

        } if ( yInput != 0) {
            this.playerMovmentY += this.tileMap.tryAndMoveY(posOnMapX, posOnMapY, yInput, this.playerSizeX, this.playerSizeY);
        }
        this.offsetY += this.playerMovmentY;

    }

    public void setPlayerX(int playerX) {
        this.offsetX = playerX - Game.WIDTH / 2 + this.playerSizeX / 2;
    }

    public void setPlayerY(int playerY) {
        this.offsetY = playerY - Game.HEIGHT / 2 + this.playerSizeY / 2;
    }

    private void playerStateUpdate(){
        this.inventoryState = this.player.invStateUpdate();
        if (this.player.pickUpUpdate()){
            PickUpItem();
        }

    }

    private void PickUpItem(){
        Item i = itemSpawner.getItem(offsetX + Game.WIDTH / 2 - this.playerSizeX / 2, offsetY + Game.HEIGHT / 2 - this.playerSizeY / 2, this.playerSizeX / 2, this.playerSizeY / 2);
        if (i != null){
            inventory.addItem(i);
        }
    }
    
    //private void inventoryMovement() 
}
