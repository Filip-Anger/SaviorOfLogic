import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;

/** Tiles are loaded here, returned on request. */
public class TileMap {
    private BufferedImage[] tiles; // grass, path, tree, water, enemy
    private String[] filesNames;
    private int[][] tileMapMatrix;
    private final int mapSize = 500;
    private final int scaledTile;
    private final int widthPixels;
    private final int heightPixels;
    private Set<Integer> forbiddenTiles;
    private GamePanel gamePanel;

    /** Load in all tiles. */
    public TileMap(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.scaledTile = Game.ORIGINAL_TILE * Game.SCALE;
        this.widthPixels = Game.WIDTH / this.scaledTile;
        this.heightPixels = Game.HEIGHT / this.scaledTile;

        Random random = new Random();
        this.tileMapMatrix = new int[this.mapSize][this.mapSize];
        for (int y = 0; y < this.mapSize; y++) {
            for (int x = 0; x < this.mapSize; x++) {
                this.tileMapMatrix[y][x] = random.nextInt(4); // Random between 1 and 3
            }
        }

        
        this.filesNames = new String[]{"water", "grass", "path", "tree", "enemy"};
        this.forbiddenTiles = new HashSet<>();
        this.forbiddenTiles.add(0);
        this.tiles = new BufferedImage[this.filesNames.length];
        try {
            for (int i = 0; i < this.filesNames.length; i++) {
                BufferedImage originalT = 
                    ImageIO.read(new File("Tileset/Tiles/" + filesNames[i] + ".png"));
                BufferedImage scaledTGraphics = 
                    new BufferedImage(this.scaledTile, this.scaledTile, originalT.getType());
                Graphics2D temp2d = scaledTGraphics.createGraphics();
                temp2d.drawImage(originalT, 0, 0, this.scaledTile, this.scaledTile, null);
                temp2d.dispose();

                this.tiles[i] = scaledTGraphics;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean canWalkOn(int x1, int y1, int sizeX, int sizeY) {
        if (x1 < 0 || y1 < 0) {
            return false;
        }
        if (x1 + sizeX > this.mapSize * this.scaledTile 
            || y1 + sizeY > this.mapSize * this.scaledTile) {
            return false;
        }
        // Rounded down for the left top
        int xCord1 = x1 / this.scaledTile;
        int yCord1 = y1 / this.scaledTile;
        if (this.forbiddenTiles.contains(this.tileMapMatrix[yCord1][xCord1])) {
            return false;
        }

        // Top right >> x round UP
        int xCord2 = (x1 + sizeX) / this.scaledTile;
        int yCord2 = y1 / this.scaledTile;
        if (this.forbiddenTiles.contains(this.tileMapMatrix[yCord2][xCord2])) {
            return false;
        }

        int xCord3 = (x1 + sizeX) / this.scaledTile;
        int yCord3 = (y1 + sizeY) / this.scaledTile;
        if (this.forbiddenTiles.contains(this.tileMapMatrix[yCord3][xCord3])) {
            return false;
        }

        // Top right >> x round UP
        int xCord4 = x1 / this.scaledTile;
        int yCord4 = (y1 + sizeY) / this.scaledTile;
        if (this.forbiddenTiles.contains(this.tileMapMatrix[yCord4][xCord4])) {
            return false;
        }

        // Bottom right >> 
        return true;
    }

    public void snapToEdge(int velocityX, int velocityY, int x, int y) {
        if (x % this.scaledTile != 0) {
            if (velocityX > 0) {
                this.gamePanel.setPlayerX((x / this.scaledTile + 1) * this.scaledTile);
            } else if (velocityX < 0) {
                this.gamePanel.setPlayerX((x  - (x % this.scaledTile)));
            }
        }
        if (y % this.scaledTile != 0) {
            if (velocityY > 0) {
                this.gamePanel.setPlayerY((y / this.scaledTile + 1) * this.scaledTile);
            } else if (velocityY < 0) {
                this.gamePanel.setPlayerY((y  - (x % this.scaledTile)));
            }
        }
        
    }

    public void draw(Graphics g, int xOffset, int yOffset) {
        int yMatrixOff = yOffset / this.scaledTile;
        int xMatrixOff = xOffset / this.scaledTile;
        int xFracOff = xOffset % this.scaledTile;
        int yFracOff = yOffset % this.scaledTile;
        for (int i = -1; i < this.heightPixels + 1; i++) {
            for (int j = -1; j < this.widthPixels + 1; j++) {
                if (yMatrixOff + i < 0 || yMatrixOff + i >= this.mapSize
                    || xMatrixOff + j < 0 || xMatrixOff + j >= this.mapSize) {
                    g.drawImage(this.tiles[0],
                        j * this.scaledTile - xFracOff, i * this.scaledTile - yFracOff, null);
                } else {
                    g.drawImage(this.tiles[this.tileMapMatrix[yMatrixOff + i][xMatrixOff + j]],
                        j * this.scaledTile - xFracOff, i * this.scaledTile - yFracOff, null);
                }
            }
        }
    }
}
