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
    private int playerMovmentX = 0;
    private int playerMovmentY = 0;
    private AllInputHandler inputHandler;
    private long lastFrameTime;
    private ProofSubmitter proofSubmitter;
    private DebugDrawer debugDrawer;
    private Pair newPlayerPos;


    public static final int TILE_SIZE = 16;

    //Game state
    public boolean inventoryState = false;
    public boolean submitterState = false;

    public final int propItem = 0;

    public GamePanel(int startX, int startY, int fps) {
        // Player
        Pair offset = new Pair(startX, startY);
        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                System.out.println(e.getX()+" "+ e.getY());
                inventory.hoverItem(e.getX(), e.getY());
            }

        });

        this.debugDrawer = new DebugDrawer();

        inputHandler = new AllInputHandler(inputMap, actionMap);
        
        this.player = new PlayerSprite(actionMap, offset);
        this.newPlayerPos = player.getAbsotulePosition();
        // TileMap
        this.tileMap = new TileMap();
        // World offset

        // ItemSpawner
        this.itemSpawner = new ItemSpawner();
        // Inventory
        this.inventory = new Inventory();

        this.proofSubmitter = new ProofSubmitter();
        
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
        
        this.tileMap.draw(g, this.newPlayerPos.subtractAndGive(this.player.getScreenPosition())); // HELPER class to make it organized

        // this.player.drawHitbox(g);
        this.player.draw(g, this.newPlayerPos.giveNew());
        this.playerMovmentX = 0;
        this.playerMovmentY = 0;
        

        this.itemSpawner.drawItems(g, this.newPlayerPos.subtractAndGive(this.player.getScreenPosition()));

        if (submitterState){
            this.proofSubmitter.draw(g);
        }
        if (inventoryState){
            this.inventory.draw(g);
        }
        // this.debugDrawer.drawDebug(g, this.player.getScreenPosition());
        // this.player.drawDebug(g);
        // this.lastFrameTime = System.nanoTime();
    }

    private void playerMovment() {
        this.newPlayerPos = this.tileMap.tryAndMove(this.player.absolutePosition.giveNew(), this.player.movement(), this.player.getSize());
        
    }

    private void playerStateUpdate(){
        this.inventoryState = this.player.invStateUpdate();
        System.out.println(this.player.pickUpUpdate());
        
        if (this.player.pickUpUpdate()){
            PickUpItem();
            this.player.pickUpFalse();
        }

    }

    private void PickUpItem(){

        if (submitterState){
            submitterState = false;

        }
        else if (proofSubmitter.IsNear(this.player.getAbsotulePosition(), this.player.getSize())){
            submitterState = true;
        } else { 
        Item i = itemSpawner.getItem(this.player.getAbsotulePosition(), this.player.getSize());
        if (i != null){
            inventory.addItem(i);
        }
    }
    //private void inventoryMovement() 
    }
}