
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.sql.rowset.spi.XmlReader;
import javax.swing.ActionMap;
import javax.swing.InputMap;

public class PlayerSprite extends Player {
    BufferedImage[] up;
    BufferedImage[] left;
    BufferedImage[] down;
    BufferedImage[] right;
    int frames;
    private String lastDirection; // 0, 1, 2, 3 UP LEFT
    private int lastAnimationFrame;
    private  int fpsPerFrame = 5;

    public PlayerSprite(InputMap playerInputMap, ActionMap playerActionMap) {
        super(playerInputMap, playerActionMap);
        this.sizeX = 32;
        this.sizeY = 64;
        this.x = Game.WIDTH / 2 - this.sizeX / 2;
        this.y = Game.HEIGHT / 2 - this.sizeX / 2;

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
    
    
}
