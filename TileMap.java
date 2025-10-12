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
    private  final int mapSize = 20;

    int yCoord;
    int xCoord;

    /** Load in all tiles. */
    public TileMap() {
        Random random = new Random();
        this.tileMapMatrix = new int[500][500];
        for (int y = 0; y < 500; y++) {
            for (int x = 0; x < 500; x++) {
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
        int yMatrixOff = yOffset / 16;
        int xMatrixOff = xOffset / 16;
        int xFracOff = xOffset % 16;
        int yFracOff = yOffset % 16;
        for (int i = 0; i < Game.SCRREN_HEIGHT_PIX + 1; i++) {
            for (int j = 0; j < Game.SCRREN_WIDTH_PIX + 1; j++) {
                g.drawImage(this.tiles[this.tileMapMatrix[yMatrixOff + i][xMatrixOff + j]],
                     j * 16 - xFracOff, i * 16 - yFracOff, null);
                // System.out.println("Xcoord: " + xCoord + "    Ycoord: " + yCoord);
                // if (yCoord < 0 || yCoord >= 500 || xCoord < 0 || xCoord >= 500) {
                //     g.drawImage(this.tiles[0], 
                //         j * 16 - xOffset, i * 16 - yOffset, null);
                // } else {
                //     g.drawImage(this.tiles[this.tileMapMatrix[yCoord][xCoord]], 
                //         j * 16 - xOffset, i * 16 - yOffset, null);
                // }
            }
        }
    }
}
