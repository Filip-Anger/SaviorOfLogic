import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.imageio.ImageIO;

/** Tiles are loaded here, returned on request. */
public class TileMap {
    private BufferedImage[] tiles; // grass, path, tree, water, enemy
    private String[] filesNames;
    private int[][] tileMapMatrix;
    private final int mapHeight = 50;
    private final int mapWidth = 200;
    private final int scaledTile;
    private final int widthPixels;
    private final int heightPixels;
    private final File mapFile;
    private  Scanner sc;

    private Set<Integer> forbiddenTiles;
    private GamePanel gamePanel;

    /** Load in all tiles. */
    public TileMap(GamePanel gamePanel) {
        this.mapFile = new File("Tileset/map_new.txt");

        try {
            this.sc = new Scanner(this.mapFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.gamePanel = gamePanel;
        this.scaledTile = Game.MAP_RESOLTION * Game.SCALE;
        this.widthPixels = Game.WIDTH / this.scaledTile;
        this.heightPixels = Game.HEIGHT / this.scaledTile;

        this.tileMapMatrix = new int[this.mapHeight][this.mapWidth];
        for (int y = 0; y < this.mapHeight; y++) {
            for (int x = 0; x < this.mapWidth; x++) {
                if (sc.hasNext()) {
                    this.tileMapMatrix[y][x] = sc.nextInt(); // Random between 1 and 3
                }
            }
        }

        
        this.filesNames = new String[]{"water", "grass", "path", "tree", "enemy"};
        this.forbiddenTiles = new HashSet<>();
        this.forbiddenTiles.add(0);
        this.forbiddenTiles.add(3);
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

    private int collisionHelper(int deltaX, int deltaY, int x, int y) {
        int delta;
        int pos;
        if (deltaX != 0) {
            delta = deltaX;
            pos = x;
        } else {
            delta = deltaY;
            pos = y;
        }
        if (delta < 0) {
                return ((pos / this.scaledTile) * this.scaledTile - pos);
        } else {
            return ((pos / this.scaledTile) * this.scaledTile - pos); //(((pos / this.scaledTile) + 1) * this.scaledTile);
        }
    }

    /**@param deltaY NOT ZERO
     * @return max possible deltaY
    */
    public int tryAndMoveY(int x, int y, int deltaY, int sizeX, int sizeY) {
        int cordXLeft = (x) / this.scaledTile;
        int cordXRight = (x + sizeX - 1) / this.scaledTile;
        int cordYTop = (y + deltaY) / this.scaledTile;
        int cordYBot = (y + deltaY + sizeY - 1) / this.scaledTile;
        if (cordYTop < 0) {
            return (- y);
        }
        if (cordYBot >= (this.mapHeight- 1) * this.scaledTile) {
            return (this.mapHeight * (this.scaledTile - 1) - y);
        }
        if (deltaY < 0) { // Go up - if edge snap to current square
            if (this.forbiddenTiles.contains(this.tileMapMatrix[cordYTop][cordXLeft]) 
            || this.forbiddenTiles.contains(this.tileMapMatrix[cordYTop][cordXRight])) {
                return (cordYTop + sizeY / this.scaledTile) * this.scaledTile - y;
            }
        } else if(deltaY > 0) { // Go down, if edge snap to square bellow
            if (this.forbiddenTiles.contains(this.tileMapMatrix[cordYBot][cordXLeft])
            || this.forbiddenTiles.contains(this.tileMapMatrix[cordYBot][cordXRight])) {
                return (cordYBot - sizeY / this.scaledTile) * this.scaledTile - y;
            }
        }
        return deltaY;
    }

    public int tryAndMoveX(int x, int y, int deltaX, int sizeX, int sizeY) {
        int cordXLeft = (x + deltaX) / this.scaledTile;
        int cordXRight = (x + deltaX + sizeX - 1) / this.scaledTile;
        int cordYTop = (y) / this.scaledTile;
        int cordYBot = (y + sizeY - 1) / this.scaledTile;
         if (cordXLeft < 0) {
            return (- x);
        }
        if (cordXRight >= (this.mapHeight - 1) * this.scaledTile) {
            return (this.mapWidth * (this.scaledTile - 1) - x);
        }
        if (deltaX < 0) { // Go up - if edge snap to current square
            if (this.forbiddenTiles.contains(this.tileMapMatrix[cordYTop][cordXLeft])
            || this.forbiddenTiles.contains(this.tileMapMatrix[cordYBot][cordXLeft])) {
                if ((x + deltaX) == cordXLeft) {
                    return 0;
                }
                return (cordXLeft + 1) * this.scaledTile - x;
            }
        } else if(deltaX > 0) { // Go down, if edge snap to square bellow
            if (this.forbiddenTiles.contains(this.tileMapMatrix[cordYTop][cordXRight])
            || this.forbiddenTiles.contains(this.tileMapMatrix[cordYBot][cordXRight])) {
                if ((x + deltaX) == cordXRight) {
                    return 0;
                }
                return (cordXRight - sizeX / this.scaledTile) * this.scaledTile - x;
            }
        }
        return deltaX;
    }
    // public int canWalkOn(int x1, int y1, int deltaX, int deltaY, int size) {
    //     if (x1 < 0 || y1 < 0) {
    //         return 0;
    //     }
    //     if (x1 + deltaX + size > this.mapWidth * this.scaledTile
    //         || y1 + deltaY + size > this.mapHeight * this.scaledTile) {
    //         return this.mapWidth * this.scaledTile - size;
    //     }
        
    //     // Rounded down for the left top
    //     int xCord1 = (x1 + deltaX) / this.scaledTile;
    //     int yCord1 = (y1 + deltaY) / this.scaledTile;
    //     if (this.forbiddenTiles.contains(this.tileMapMatrix[yCord1][xCord1])) {
    //         return this.collisionHelper(deltaX, deltaY, x1, y1);
    //     }

    //     // Top right >> x round UP
    //     xCord1 = (x1 + deltaX + size) / this.scaledTile;
    //     if (this.forbiddenTiles.contains(this.tileMapMatrix[yCord1][xCord1])) {
    //         return this.collisionHelper(deltaX, deltaY, x1 , y1);

    //     }

    //     yCord1 = (y1 + + deltaY + size) / this.scaledTile;
    //     if (this.forbiddenTiles.contains(this.tileMapMatrix[yCord1][xCord1])) {
    //         return this.collisionHelper(deltaX, deltaY, x1, y1);
    //     }

    //     // Top right >> x round UP
    //     xCord1 = (x1 + deltaX) / this.scaledTile;
    //     if (this.forbiddenTiles.contains(this.tileMapMatrix[yCord1][xCord1])) {
    //         return this.collisionHelper(deltaX, deltaY, x1, y1);
    //     }
    //     return (deltaX + deltaY);
    //     // Bottom right >> 
    // }

    // public void snapToEdge(int velocityX, int velocityY, int x, int y) {
    //     if (x % this.scaledTile != 0) {
    //         if (velocityX > 0) {
    //             this.gamePanel.setPlayerX((x / this.scaledTile + 1) * this.scaledTile);
    //         } else if (velocityX < 0) {
    //             this.gamePanel.setPlayerX((x  - (x % this.scaledTile)));
    //         }
    //     }
    //     if (y % this.scaledTile != 0) {
    //         if (velocityY > 0) {
    //             this.gamePanel.setPlayerY((y / this.scaledTile + 1) * this.scaledTile);
    //         } else if (velocityY < 0) {
    //             this.gamePanel.setPlayerY((y  - (x % this.scaledTile)));
    //         }
    //     }
        
    // }

    public void draw(Graphics g, int xOffset, int yOffset) {
        int yMatrixOff = yOffset / this.scaledTile;
        int xMatrixOff = xOffset / this.scaledTile;
        int xFracOff = xOffset % this.scaledTile;
        int yFracOff = yOffset % this.scaledTile;
        for (int i = -1; i < this.heightPixels + 2; i++) {
            for (int j = -1; j < this.widthPixels + 2; j++) {
                if (yMatrixOff + i < 0 || yMatrixOff + i >= this.mapHeight
                    || xMatrixOff + j < 0 || xMatrixOff + j >= this.mapWidth) {
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
