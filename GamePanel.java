import com.sun.source.tree.YieldTree;
import java.awt.Graphics;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
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
    private AllInputHandler inputHandler;
    private ProofSubmitter proofSubmitter;
    private DebugDrawer debugDrawer;
    private Pair newPlayerPos;
    private ArrayList<Skeleton> enemies;

    public static final int TILE_SIZE = 16;

    //Game state
    public boolean inventoryState = false;
    public boolean submitterState = false;

    public final int propItem = 0;

    public GamePanel(int startX, int startY, int fps) {
        // Player
        this.enemies = new ArrayList<>();
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
        this.enemies.add(new Skeleton(1));
        // TileMap
        this.tileMap = new TileMap(Game.MAP_RESOLUTION);
        // World offset

        // ItemSpawners
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
        spritesMovment();
        spritesColision();
    
    }

    private Pair offset() {
        return this.newPlayerPos.subtractAndGive(this.player.getScreenPosition());
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clean background
        // double betweenLast = (System.nanoTime() - this.lastFrameTime);
        // System.out.println("FPS: " + 1 / (betweenLast / Math.pow(10, 9)));
        
        this.tileMap.draw(g, this.offset()); // HELPER class to make it organized

        // this.player.drawHitbox(g);
        this.player.draw(g, this.newPlayerPos.giveNew());
        this.drawEnemies(g);

        this.itemSpawner.drawItems(g, this.offset());

        if (submitterState){
            this.proofSubmitter.draw(g);
        }
        if (inventoryState){
            this.inventory.draw(g);
        }
        this.debugDrawer.drawDebug(g, this.player.getMiddle().subtractAndGive(this.offset()));
        // this.player.drawDebug(g);
        // this.lastFrameTime = System.nanoTime();
    }

    private void spritesMovment() {
        this.newPlayerPos = this.tileMap.tryAndMove(this.player.absolutePosition.giveNew(), this.player.movement(), this.player.getSize());
        
        for (Skeleton enemie : this.enemies) {
            enemie.follow(this.player);
            enemie.setAbsotulePosition(this.tileMap.tryAndMove(enemie.getAbsotulePosition().giveNew(), 
            enemie.inputUpdate().giveNew(), enemie.getSize()));
        }
        
    }

    private void drawEnemies(Graphics g) {
        for (Skeleton enemie : this.enemies) {
            enemie.draw(g, this.offset());
        }
    }
    private void spritesColision() {
        for (Skeleton enemie : this.enemies) {
            if (this.player.inHitbox(enemie)) {
                System.out.println("HIT");
            }
        }
    }

    private void playerStateUpdate(){
        this.inventoryState = this.player.invStateUpdate();
        //System.out.println(this.player.pickUpUpdate());
        
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