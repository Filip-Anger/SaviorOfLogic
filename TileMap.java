import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.plaf.BorderUIResource;

/** Tiles are loaded here, returned on request. */
public class TileMap {
    private BufferedImage[] tiles; // grass, path, tree, water, enemy
    private String[] filesNames;
    private int[][] tileMapMatrix;
    private final int tileSize;

    private int matrixRow;
    private int matrixCol;
    private boolean mapEdgeY;
    private boolean mapEdgeX;

    /** Load in all tiles. */
    public TileMap(int tileSize) {
        this.tileMapMatrix = new int[][]{
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 3, 3, 1, 1, 1, 3, 3, 1, 1, 1, 3, 3, 1, 1, 1, 3, 3, 1, 1},
            {1, 3, 1, 1, 2, 2, 1, 3, 1, 1, 2, 2, 1, 3, 1, 1, 2, 2, 1, 1},
            {1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1},
            {1, 1, 3, 1, 2, 2, 1, 1, 3, 1, 2, 2, 1, 1, 3, 1, 2, 2, 1, 1},
            {1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1},
            {1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1},
            {1, 3, 1, 1, 2, 2, 1, 3, 1, 1, 2, 2, 1, 3, 1, 1, 2, 2, 1, 1},
            {1, 3, 3, 1, 1, 1, 3, 3, 1, 1, 1, 3, 3, 1, 1, 1, 3, 3, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2},
            {1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1},
            {1, 1, 2, 1, 3, 3, 1, 2, 1, 1, 2, 1, 3, 3, 1, 2, 1, 1, 2, 1},
            {1, 1, 2, 1, 3, 1, 1, 2, 1, 1, 2, 1, 3, 1, 1, 2, 1, 1, 2, 1},
            {1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1},
            {1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 3, 3, 1, 1, 1, 3, 3, 1, 1, 1, 3, 3, 1, 1, 1, 3, 3, 1, 1},
            {1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        };
        
        this.tileSize = tileSize;
        this.filesNames = new String[]{"water", "grass", "path", "tree", "enemy"};
        this.tiles = new BufferedImage[this.filesNames.length];
        try {
            for (int i = 0; i < this.filesNames.length; i++) {
                this.tiles[i] = ImageIO.read(new File("/Tileset/Tiles" + filesNames[i] + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, int xOffset, int yOffset) {
        int xOffsetPixels = xOffset / this.tileSize;
        int yOffsetPixels = yOffset / this.tileSize;
        for (int row = 0; row < Game.SCREEN_WIDTH / this.tileSize + 1; row++) {
            if (yOffsetPixels + row >= this.tileMapMatrix.length) {
                mapEdgeY = true;
            } else {
                mapEdgeX = false; // 
            }
            for (int col = 0; col < Game.SCREEN_HEIGHT / this.tileSize + 1; col++) {
                if (xOffsetPixels + col >= this.tileMapMatrix[0].length) {
                    mapEdgeX = true;
                } else {
                    mapEdgeX = false;
                }

                if (mapEdgeX || mapEdgeY) {
                    g.drawImage(this.tiles[0], 
                        xOffset + col * this.tileSize, yOffset + row * this.tileSize, null);
                } else {
                    g.drawImage(this.tiles[this.tileMapMatrix[row][col]], 
                        xOffset + col * this.tileSize, yOffset + row * this.tileSize, null);
                }
                
            }
        }
    }

}
