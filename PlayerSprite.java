
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.ActionMap;
import javax.swing.InputMap;

public class PlayerSprite extends Player {
    BufferedImage[] up;
    BufferedImage[] left;
    BufferedImage[] down;
    BufferedImage[] right;
    int frames;

    public PlayerSprite(InputMap playerInputMap, ActionMap playerActionMap) {
        super(playerInputMap, playerActionMap);

        this.frames = 5;
        this.up = new BufferedImage[this.frames];
        this.left = new BufferedImage[this.frames];
        this.down = new BufferedImage[this.frames];
        this.right = new BufferedImage[this.frames];

        String pathStart = "Tileset/Player/";
        String pathEnd = ".png";
        for (int i = 0; i < frames * 4; i++) {
            if (i < 5) {
                try {
                    this.up[i % frames] = ImageIO.read(new File(pathStart + String.format("%03d", i) + pathEnd));
                } catch (IOException e) {
                    e.getStackTrace();
                }
                
            } else if (i < 10) {
                try {
                    this.left[i % frames] = ImageIO.read(new File(pathStart + String.format("%03d", i) + pathEnd));
                } catch (IOException e) {
                    e.getStackTrace();
                }
            } else if (i < 15) {
                try {
                    this.down[i % frames] = ImageIO.read(new File(pathStart + String.format("%03d", i) + pathEnd));
                } catch (IOException e) {
                    e.getStackTrace();
                }
            } else {
                try {
                    this.right[i % frames] = ImageIO.read(new File(pathStart + String.format("%03d", i) + pathEnd));
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }   
        }
    }
    
    
}
