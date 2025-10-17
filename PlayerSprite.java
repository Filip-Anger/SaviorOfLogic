
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.sql.rowset.spi.XmlReader;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;

public class PlayerSprite extends Entiti implements PlayerUpdate {
    BufferedImage[] up;
    BufferedImage[] left;
    BufferedImage[] down;
    BufferedImage[] right;
    int frames;
    private String lastDirection; // 0, 1, 2, 3 UP LEFT
    private int lastAnimationFrame;
    private  int fpsPerFrame = 5;

    ActionMap playerActionMap;
    private boolean pickUp;
    private boolean inventoryState;

    public PlayerSprite(ActionMap actionMap) {
        this.playerActionMap = actionMap;

        this.velocity = 3 * Game.SCALE;
        this.sizeX = 32;       
        this.sizeY = 64;
        this.x = Game.WIDTH / 2 - this.sizeX / 2;
        this.y = Game.HEIGHT / 2 - this.sizeY / 2;


        // Animation 
        this.lastAnimationFrame = 0;
        this.frames = 8;
        this.up = new BufferedImage[this.frames];
        this.left = new BufferedImage[this.frames];
        this.down = new BufferedImage[this.frames];
        this.right = new BufferedImage[this.frames];
        String pathStart = "Tileset/Player/";
        String pathEnd = ".png";
        for (int i = 0; i < frames * 4; i++) {
            if (i < this.frames) {
                try {
                    this.up[i % frames] = ImageIO.read(new File(pathStart + i + pathEnd));
                } catch (IOException e) {
                    e.getStackTrace();
                }
                
            } else if (i < this.frames * 2) {
                try {
                    this.left[i % frames] = ImageIO.read(new File(pathStart + i + pathEnd));
                } catch (IOException e) {
                    e.getStackTrace();
                }
            } else if (i < this.frames * 3) {
                try {
                    this.down[i % frames] = ImageIO.read(new File(pathStart + i + pathEnd));
                } catch (IOException e) {
                    e.getStackTrace();
                }
            } else {
                try {
                    this.right[i % frames] = ImageIO.read(new File(pathStart + i + pathEnd));
                } catch (IOException e) {
                    e.getStackTrace();
                }
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
        this.playerActionMap.put("PickUpReleased", new PickUpRelease());
        
    }

    public void drawAnimation(Graphics g, BufferedImage[] animationList) {
        g.drawImage(animationList[(this.lastAnimationFrame / this.fpsPerFrame) % this.frames], this.x, this.y, null);
        this.lastAnimationFrame += 1;
    }

    @Override
    public void draw(Graphics g, int xMovment, int yMovment) {
        if (xMovment != 0 ) {
            if (xMovment > 0) {
                if (this.lastDirection != "Right") { // Start going right
                    this.lastAnimationFrame = 0;
                }
                this.drawAnimation(g, this.right);
                this.lastDirection = "Right";
            } else {
                if (this.lastDirection != "Left") {
                    this.lastAnimationFrame = 0;
                }
                this.drawAnimation(g, this.left);
                this.lastDirection = "Left";
            }
        } else if (yMovment != 0) {
            if (yMovment > 0) {
                if (this.lastDirection != "Down") {
                    this.lastAnimationFrame = 0;
                }
                this.drawAnimation(g, this.down);
                this.lastDirection = "Down";
            } else {
                if (this.lastDirection != "Up") {
                    this.lastAnimationFrame = 0;
                }
                this.drawAnimation(g, this.up);
                this.lastDirection = "Up";
            }
        } else {
            g.drawImage(this.down[0], this.x, this.y, null);
            this.lastDirection = "Idle";
        }
    }
    @Override
    public boolean invStateUpdate(){
        return inventoryState;
    }
    @Override
    public boolean pickUpUpdate(){
        return pickUp;
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
            pickUp = true;
        }
    }

    public class PickUpRelease  extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            pickUp = false;
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
