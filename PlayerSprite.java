
import com.sun.source.tree.YieldTree;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;

public class PlayerSprite extends Entiti implements PlayerUpdate {
    BufferedImage[][] animationFrames; // 0, 1, 2, 3 UP LEFT DOWN RIGHT
    int frames;
    private int lastDirection; // 0, 1, 2, 3 UP LEFT DOWN RIGHT
    private int lastAnimationFrame;
    private  int framesPerAnimation = 5;

    ActionMap playerActionMap;
    private boolean pickUp;
    private boolean inventoryState;
    private int speedingUp;
    private final Pair spriteSize;
    private final int spriteOffset;
    private int playerScale;

    public PlayerSprite(ActionMap actionMap, Pair offset) {
        this.playerActionMap = actionMap;
        this.velocity = 3 * Game.SCALE;
        this.playerScale = (Game.SCALE * Game.MAP_RESOLTION_16 / Game.SPRITE_RESOLUTION);
        this.spriteOffset = 20 * this.playerScale;

        this.size = new Pair(Game.SPRITE_RESOLUTION * this.playerScale, Game.SPRITE_RESOLUTION * this.playerScale);
        this.spriteSize = new Pair(this.size.x(), this.size.x() * 2);
        this.screenPosition = new Pair(Game.WIDTH / 2 - this.size.x() / 2, Game.HEIGHT / 2 - 3/2 * this.size.x());
        this.absolutePosition = new Pair(offset.x() + this.screenPosition.x(), offset.y() + this.screenPosition.y());

        // Animation 
        this.speedingUp = Game.PLAYER_ACCELERATION;
        this.lastAnimationFrame = 0;
        this.frames = 8;
        this.animationFrames = new BufferedImage[4][this.frames];
        String pathStart = "Tileset/Player/";
        String pathEnd = ".png";
        BufferedImage originalT;
        for (int i = 0; i < frames * 4; i++) {
            try {
                originalT = ImageIO.read(new File(pathStart + i + pathEnd));
                BufferedImage scaledTGraphics = new BufferedImage(originalT.getWidth() * this.playerScale, originalT.getHeight() * this.playerScale, originalT.getType());
                Graphics2D temp2d = scaledTGraphics.createGraphics();
                temp2d.drawImage(originalT, 0, 0, originalT.getWidth() * this.playerScale, originalT.getHeight() * this.playerScale, null);
                temp2d.dispose();

                this.animationFrames[i / this.frames][i % this.frames] = scaledTGraphics;
            } catch (IOException e) {
                System.out.println("ERROR");
                e.getStackTrace();
            }
        }
        // Movement
        this.playerActionMap.put("moveUpPressed", new UpPressAction());
        this.playerActionMap.put("moveUpReleased", new UpReleaseAction());
        this.playerActionMap.put("moveDownPressed", new DownPressAction()); 
        this.playerActionMap.put("moveDownReleased", new DownReleaseAction());
        this.playerActionMap.put("moveLeftPressed", new LeftPressAction());
        this.playerActionMap.put("moveLeftReleased", new LeftReleaseAction());
        this.playerActionMap.put("moveRightPressed", new RightPressAction());
        this.playerActionMap.put("moveRightReleased", new RightReleaseAction());
        this.playerActionMap.put("InventoryPressed", new InventoryPress());
        this.playerActionMap.put("PickUpPressed", new PickUpPress());

        
    }

    public Pair movement() {
        Pair input = this.inputUpdate();
        if (input.x() != 0 && input.y() != 0) {
            input.set((int) Math.round(input.x() / Math.sqrt(2)), (int) Math.round(input.y() / Math.sqrt(2)));
        }
        input.multyPercentage(this.speedingUp);
        return input;
    }
    public void drawDebug(Graphics g) {
        for (int i = 0; i < 4 * this.frames; i++) {
            g.drawImage(this.animationFrames[i / this.frames][i % this.frames], 
                50 + (this.spriteSize.x() + 5) * (i % this.frames), 50 + (this.spriteSize.y() + 5) * (i / this.frames), null);
        }
    }
    public void drawMovement(Graphics g, int checkedDir) {
        if (this.lastDirection != checkedDir) { // Start going right
                this.lastAnimationFrame = 0;
                if (this.lastDirection % 2 == checkedDir % 2) {
                    speedingUp = Game.PLAYER_ACCELERATION;
                }
            this.lastDirection = checkedDir;
            }
        g.drawImage(this.animationFrames[checkedDir][(this.lastAnimationFrame / this.framesPerAnimation) % this.frames], this.screenPosition.x(), this.screenPosition.y() - this.spriteOffset, null);
        this.lastAnimationFrame += 1;
        if (this.speedingUp < 100) {
            this.speedingUp += 1;
        }
    }
    public int getSpeed() {
        return this.speedingUp;
    }
    @Override
    public void draw(Graphics g, Pair newPosition) {
        int xMovment = newPosition.x() - this.absolutePosition.x();
        int yMovment = newPosition.y() - this.absolutePosition.y();
        if (xMovment != 0 ) {
            if (xMovment > 0) {
                this.drawMovement(g, 3);
            } else {
                this.drawMovement(g, 1);
            }
        } else if (yMovment != 0) {
            if (yMovment > 0) {
                this.drawMovement(g, 2);
            } else {
                this.drawMovement(g, 0);
            }
        } else {
            g.drawImage(this.animationFrames[2][0], this.screenPosition.x(), this.screenPosition.y() - this.spriteOffset, null);
            this.lastDirection = 4;
            speedingUp = Game.PLAYER_ACCELERATION;
        }
        this.absolutePosition = newPosition;
    }
    @Override
    public boolean invStateUpdate(){
        return inventoryState;
    }
    @Override
    public boolean pickUpUpdate(){
        return pickUp;
    }
    public void pickUpFalse(){
        this.pickUp = false;
    }

    public class UpPressAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingUp = true;
        }
    }


    public class DownPressAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingDown = true;
        }
    }

    
    public class LeftPressAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingLeft = true;
        }
    }


    public class RightPressAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingRight = true;
        }
    }


    public class UpReleaseAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingUp = false;
        }
    }

    public class DownReleaseAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingDown = false;
        }
    }

    public class LeftReleaseAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingLeft = false;
        }
    }

    public class RightReleaseAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            movingRight = false;
        }
    }

    public class PickUpPress  extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (pickUp){
                pickUp = false;
            }else {
                pickUp = true;
            }
        }
    }

    

    public class InventoryPress extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (inventoryState){
                inventoryState = false;
            }
            else{
                inventoryState = true;
            }
        }
    }
}
