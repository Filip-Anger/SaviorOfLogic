import com.sun.source.tree.YieldTree;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.logging.XMLFormatter;

import javax.imageio.ImageIO;
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
    private int gameState = 0;
    private final Timer timer;
    private final PlayerSprite player;
    private final TileMap tileMap;
    private final Inventory inventory;
    private final ItemSpawner itemSpawner;
    private AllInputHandler inputHandler;
    private ProofSubmitter proofSubmitter;
    private Intro intro;
    // private DebugDrawer debugDrawer;
    private Pair newPlayerPos;
    private boolean wasDragging = false;
    private boolean isNearSubmitter = false;
    private ArrayList<Skeleton> enemies;
    //Game state
    public boolean inventoryState = false;
    public boolean submitterState = false;

    public final int propItem = 0;

    public GamePanel(int startX, int startY, int fps) {

        setBackground(Color.BLACK);
        // Player
        this.enemies = new ArrayList<>();
        Pair offset = new Pair(startX, startY);
        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();
        inputHandler = new AllInputHandler(inputMap, actionMap);

        this.addMouseListener(inputHandler.new ClickListener());
        this.addMouseListener(inputHandler.new ReleaseListener());
        this.addMouseMotionListener(inputHandler.new DragListener());

        this.player = new PlayerSprite(actionMap, offset);
        this.newPlayerPos = player.getAbsotulePosition();
        // TileMap

        // ItemSpawners
        this.itemSpawner = new ItemSpawner();
        // Inventory
        this.inventory = new Inventory();

        this.tileMap = new TileMap(itemSpawner);
        // World offset

        

        this.proofSubmitter = new ProofSubmitter(this.inventory);
        this.intro = new Intro();
        
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

    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (gameState) {
            case 0:
                this.intro.drawStartScreen(g, this.player);
                break;
            case 1:
                this.intro.drawStoryScreen(g);
                break;
            case 2:
                drawPlayScreen(g);
                break;
            case 3:
                //drawEndScreen
                break;
            default:
                drawPlayScreen(g);
                break;
        }
    }


    private void drawPlayScreen(Graphics g){
        this.tileMap.draw(g, this.offset()); // HELPER class to make it organized

        // this.player.drawHitbox(g);
        this.player.draw(g, this.newPlayerPos.giveNew());
        this.drawEnemies(g);

        this.itemSpawner.drawItems(g, this.offset());

        if (inventoryState){
            this.inventory.draw(g);
        }
        if (submitterState){
            this.proofSubmitter.draw(g);
            this.Drag(g);
            
        }
    }

    private Pair offset() {
        return this.newPlayerPos.subtractAndGive(this.player.getScreenPosition());
    }

    private void spritesMovment() {
        // Player
        this.newPlayerPos = this.tileMap.tryAndMove(this.player.absolutePosition.giveNew(), this.player.movement(), this.player.getSize());
        // Other sprites
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
        this.isNearSubmitter = proofSubmitter.isNear(this.player.getAbsotulePosition(), this.player.getSize());
        this.inventoryState = this.player.invStateUpdate();

        if (!this.isNearSubmitter){
                this.submitterState = false;
            }
        
        if (this.player.pickUpUpdate()){
            if (this.gameState == 0){
                this.gameState += 1;
                this.player.pickUpFalse();
                return;
            }
            if (this.gameState == 1){
                //this.nextStoryText();
                return;
            }
            PickUpItem();
            this.player.pickUpFalse();
            if(this.isNearSubmitter){
                if (this.submitterState){
                    this.submitterState = false;
                }
                else{
                    this.submitterState = true;
                }
            }
        }
        if (this.player.submitUpdate() && this.isNearSubmitter){
            submit();
            this.player.submitFalse();
        }
        

    }

    private void submit(){
        if(proofSubmitter.submitProofs()){
            this.tileMap.openDoor();
            this.openDoor();
        }
    }

    private void PickUpItem(){
        Item i = itemSpawner.getItem(this.player.getAbsotulePosition(), this.player.getSize());
        if (i == null){
            return;
        }
        System.out.println(i.getType());
        if (i.getType().equalsIgnoreCase("Prop")) {
            this.inventory.addItem(i);
        } else if (i.getType().equalsIgnoreCase("ActionTile")) {
            if (i.getContent().equalsIgnoreCase("Mimic")) {
                this.enemies.add(new Skeleton(1, new Pair(i.getX(), i.getY())));
            } else {
                this.tileMap.actionUsed(i);
            }
        }
        
    }
    private void openDoor(){
        System.out.println("DOOR OPENED!");
    }
    private void Drag(Graphics g){
        if (this.inputHandler.isDragging()){
                this.wasDragging = true;

                if (this.inputHandler.getMouseClickX() > Game.WIDTH/2){
                    this.proofSubmitter.setDragItem(g, this.inputHandler.getMouseClickX(), this.inputHandler.getMouseClickY(), this.inputHandler.getMouseDragX(), this.inputHandler.getMouseDragY());
                }
                
                    this.inventory.dragItem(g, this.inputHandler.getMouseClickX(), this.inputHandler.getMouseClickY(), this.inputHandler.getMouseDragX(), this.inputHandler.getMouseDragY());
            }
            else if (this.wasDragging){
                this.wasDragging = false;
                this.proofSubmitter.dropItem(this.inputHandler.getMouseDragX(), this.inputHandler.getMouseDragY());
            }
    }    
}