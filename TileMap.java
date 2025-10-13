import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.plaf.BorderUIResource;

/** Tiles are loaded here, returned on request. */
public class TileMap {
    private BufferedImage[] tiles; // grass, path, tree, water, enemy
    private String[] filesNames;
    private int[][] tileMapMatrix;
    private final int mapSize = 500;
    private final int originalTileSize = 16;
    private final int tileSize;
    private final int widthPixels;
    private final int heightPixels;


    /** Load in all tiles. */
    public TileMap(int screenWidth, int screenSize, int scale) {
        this.tileSize = this.originalTileSize * scale;
        this.widthPixels = screenWidth / this.tileSize;
        this.heightPixels = screenWidth / this.tileSize;

        Random random = new Random();
        this.tileMapMatrix = new int[this.mapSize][this.mapSize];
        for (int y = 0; y < this.mapSize; y++) {
            for (int x = 0; x < this.mapSize; x++) {
                this.tileMapMatrix[y][x] = 1 + random.nextInt(3); // Random between 1 and 3
            }
        }

        
        this.filesNames = new String[]{"water", "grass", "path", "tree", "enemy"};
        this.tiles = new BufferedImage[this.filesNames.length];
        try {
            for (int i = 0; i < this.filesNames.length; i++) {
                this.tiles[i] = ImageIO.read(new File("Tileset/Tiles/" + filesNames[i] + ".png"));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, int xOffset, int yOffset) {
        int yMatrixOff = yOffset / this.tileSize;
        int xMatrixOff = xOffset / this.tileSize;
        int xFracOff = xOffset % this.tileSize;
        int yFracOff = yOffset % this.tileSize;
        for (int i = -1; i < this.heightPixels + 1; i++) {
            for (int j = -1; j < this.heightPixels + 1; j++) {
                if (yMatrixOff + i < 0 || yMatrixOff + i >= this.mapSize
                    || xMatrixOff + j < 0 || xMatrixOff + j >= this.mapSize) {
                    g.drawImage(this.tiles[0],
                        j * this.tileSize - xFracOff, i * this.tileSize - yFracOff,
                        this.tileSize, this.tileSize, null);
                } else {
                    g.drawImage(this.tiles[this.tileMapMatrix[yMatrixOff + i][xMatrixOff + j]],
                        j * this.tileSize - xFracOff, i * this.tileSize - yFracOff, 
                        this.tileSize, this.tileSize, null);
                }
            }
        }
    }
}
