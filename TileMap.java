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
    // private Pair offset;
    private  Scanner sc;

    private Set<Integer> forbiddenTiles;

    /** Load in all tiles. */
    public TileMap() {
        // this.offset = offset;
        this.mapFile = new File("Tileset/map_new.txt");

        try {
            this.sc = new Scanner(this.mapFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public Pair tryAndMove(Pair position, Pair input, Pair size) {
         if (input.x() != 0) {
            position = this.tryAndMoveX(position, input.x(), size);

        } if (input.y() != 0) {
            position = this.tryAndMoveY(position, input.y(), size);
        }
        return position;
    }
   

    /**@param deltaY NOT ZERO
     * @return max possible deltaY
    */
    private Pair tryAndMoveY(Pair position, int deltaY, Pair size) {
        int cordXLeft = (position.x()) / this.scaledTile;
        int cordXRight = (position.x() + size.x() - 1) / this.scaledTile;
        int cordYTop = (position.y() + deltaY) / this.scaledTile;
        int cordYBot = (position.y() + deltaY + size.y() - 1) / this.scaledTile;
        if (cordYTop < 0) {
            position.setY(0);
        }
         else if (cordYBot >= (this.mapHeight- 1) * this.scaledTile) {
            position.setY(this.mapHeight * (this.scaledTile - 1));
        }
        else if ((deltaY < 0) && // Go up - if edge snap to current square
                    (this.forbiddenTiles.contains(this.tileMapMatrix[cordYTop][cordXLeft]) 
                    || this.forbiddenTiles.contains(this.tileMapMatrix[cordYTop][cordXRight]))) {
            position.setY((cordYTop + size.y() / this.scaledTile) * this.scaledTile);
            
        } else if((deltaY > 0) && 
                (this.forbiddenTiles.contains(this.tileMapMatrix[cordYBot][cordXLeft])
                || this.forbiddenTiles.contains(this.tileMapMatrix[cordYBot][cordXRight]))) {
            position.setY((cordYBot - size.y() / this.scaledTile) * this.scaledTile);
        } else {
            position.incrementY(deltaY);
        }
        return position;
    }

    private Pair tryAndMoveX(Pair position, int deltaX, Pair size) {
        int cordXLeft = (position.x() + deltaX) / this.scaledTile;
        int cordXRight = (position.x() + deltaX + size.x() - 1) / this.scaledTile;
        int cordYTop = (position.y()) / this.scaledTile;
        int cordYBot = (position.y() + size.y() - 1) / this.scaledTile;
         if (position.x() + deltaX < 0) {
            position.setX(0);
        }
        else if (cordXRight >= (this.mapHeight - 1) * this.scaledTile) {
            position.setX(this.mapWidth * (this.scaledTile - 1));
        }
        else if ((deltaX < 0) && // Go up - if edge snap to current square
                (this.forbiddenTiles.contains(this.tileMapMatrix[cordYTop][cordXLeft])
                || this.forbiddenTiles.contains(this.tileMapMatrix[cordYBot][cordXLeft]))) {
            position.setX((cordXLeft + 1) * this.scaledTile);

        } else if((deltaX > 0) &&  // Go down, if edge snap to square bellow
                (this.forbiddenTiles.contains(this.tileMapMatrix[cordYTop][cordXRight])
                || this.forbiddenTiles.contains(this.tileMapMatrix[cordYBot][cordXRight]))) {
            position.setX((cordXRight - size.x() / this.scaledTile) * this.scaledTile);
        } else {
            position.incrementX(deltaX);
        }
        return position;
    }

    public void draw(Graphics g, Pair offset) {
        int yMatrixOff = offset.y() / this.scaledTile;
        int xMatrixOff = offset.x() / this.scaledTile;
        int xFracOff = offset.x() % this.scaledTile;
        int yFracOff = offset.y() % this.scaledTile;
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
