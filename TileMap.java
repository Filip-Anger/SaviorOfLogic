import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/** Tiles are loaded here, returned on request. */
public class TileMap {
    private BufferedImage[] tiles; // grass, path, tree, water, enemy
    private String[] filesNames;

    /** Load in all tiles. */
    public TileMap() {
        this.filesNames = new String[]{"grass", "path", "tree", "water", "enemy"};
        this.tiles = new BufferedImage[this.filesNames.length];
        try {
            for (int i = 0; i < this.filesNames.length; i++) {
                this.tiles[i] = ImageIO.read(new File("Tileset/Tiles" + filesNames[i] + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, int xOffset, int yOffset) {
                for (int row = 0; row < Game.SCREEN_WIDTH / this.TILE_SIZE; row++) {
            for (int col = 0; col < Game.SCREEN_HEIGHT / this.TILE_SIZE; col++) {
                int topLeftX = this.playerPosition[0]
            }
        }
    }

}
