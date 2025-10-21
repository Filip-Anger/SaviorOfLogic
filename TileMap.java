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
    private final int widthPixels;
    private final int heightPixels;
    private final File mapFile;
    // private Pair offset;
    private  Scanner sc;

    private Set<Integer> forbiddenTiles;

    /** Load in all tiles. */
    private void loadTiles() {
        this.filesNames = new String[]{"water", "grass", "path", "tree", "enemy"};
        this.forbiddenTiles = new HashSet<>();
        this.forbiddenTiles.add(0);
        this.forbiddenTiles.add(3);
        this.tiles = new BufferedImage[this.filesNames.length];
        try {
            for (int i = 0; i < this.filesNames.length; i++) {        
                this.tiles[i] = ImageIO.read(new File("Tileset/Tiles/" + filesNames[i] + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void scale(int scale) {
        BufferedImage originalT;
        BufferedImage scaledTGraphics;
        for (int i = 0; i < this.tiles.length; i++) {
            originalT = this.tiles[i];
            scaledTGraphics = new BufferedImage(originalT.getWidth() * scale, originalT.getHeight() * scale, originalT.getType());
            Graphics2D temp2d = scaledTGraphics.createGraphics();
            temp2d.drawImage(originalT, 0, 0, originalT.getWidth() * scale, originalT.getHeight() * scale, null);
            temp2d.dispose();

            this.tiles[i] = scaledTGraphics;
        }       
    }

    public void loadMap() {
        try {
            this.sc = new Scanner(this.mapFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tileMapMatrix = new int[this.mapHeight][this.mapWidth];
        for (int y = 0; y < this.mapHeight; y++) {
            for (int x = 0; x < this.mapWidth; x++) {
                if (sc.hasNext()) {
                    this.tileMapMatrix[y][x] = sc.nextInt();
                }
            }
        }
    }
    public TileMap(int originalTileSize) {
        // this.offset = offset;
        this.mapFile = new File("Tileset/map_new.txt");
        this.widthPixels = Game.WIDTH / Game.TILE_SIZE;
        this.heightPixels = Game.HEIGHT / Game.TILE_SIZE;
        this.loadMap();

        this.loadTiles();
        this.scale(Game.TILE_SIZE / originalTileSize);
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
        int cordXLeft = (position.x()) / Game.TILE_SIZE;
        int cordXRight = (position.x() + size.x() - 1) / Game.TILE_SIZE;
        int cordYTop = (position.y() + deltaY) / Game.TILE_SIZE;
        int cordYBot = (position.y() + deltaY + size.y() - 1) / Game.TILE_SIZE;
        if (cordYTop < 0) {
            position.setY(0);
        }
         else if (cordYBot >= (this.mapHeight- 1) * Game.TILE_SIZE) {
            position.setY(this.mapHeight * (Game.TILE_SIZE - 1));
        }
        else if ((deltaY < 0) && // Go up - if edge snap to current square
                    (this.forbiddenTiles.contains(this.tileMapMatrix[cordYTop][cordXLeft]) 
                    || this.forbiddenTiles.contains(this.tileMapMatrix[cordYTop][cordXRight]))) {
            position.setY((cordYTop + size.y() / Game.TILE_SIZE) * Game.TILE_SIZE);
            
        } else if((deltaY > 0) && 
                (this.forbiddenTiles.contains(this.tileMapMatrix[cordYBot][cordXLeft])
                || this.forbiddenTiles.contains(this.tileMapMatrix[cordYBot][cordXRight]))) {
            position.setY((cordYBot - size.y() / Game.TILE_SIZE) * Game.TILE_SIZE);
        } else {
            position.incrementY(deltaY);
        }
        return position;
    }

    private Pair tryAndMoveX(Pair position, int deltaX, Pair size) {
        int cordXLeft = (position.x() + deltaX) / Game.TILE_SIZE;
        int cordXRight = (position.x() + deltaX + size.x() - 1) / Game.TILE_SIZE;
        int cordYTop = (position.y()) / Game.TILE_SIZE;
        int cordYBot = (position.y() + size.y() - 1) / Game.TILE_SIZE;
         if (position.x() + deltaX < 0) {
            position.setX(0);
        }
        else if (cordXRight >= (this.mapHeight - 1) * Game.TILE_SIZE) {
            position.setX(this.mapWidth * (Game.TILE_SIZE - 1));
        }
        else if ((deltaX < 0) && // Go up - if edge snap to current square
                (this.forbiddenTiles.contains(this.tileMapMatrix[cordYTop][cordXLeft])
                || this.forbiddenTiles.contains(this.tileMapMatrix[cordYBot][cordXLeft]))) {
            position.setX((cordXLeft + 1) * Game.TILE_SIZE);

        } else if((deltaX > 0) &&  // Go down, if edge snap to square bellow
                (this.forbiddenTiles.contains(this.tileMapMatrix[cordYTop][cordXRight])
                || this.forbiddenTiles.contains(this.tileMapMatrix[cordYBot][cordXRight]))) {
            position.setX((cordXRight - size.x() / Game.TILE_SIZE) * Game.TILE_SIZE);
        } else {
            position.incrementX(deltaX);
        }
        return position;
    }

    public void draw(Graphics g, Pair offset) {
        int yMatrixOff = offset.y() / Game.TILE_SIZE;
        int xMatrixOff = offset.x() / Game.TILE_SIZE;
        int xFracOff = offset.x() % Game.TILE_SIZE;
        int yFracOff = offset.y() % Game.TILE_SIZE;
        for (int i = -1; i < this.heightPixels + 2; i++) {
            for (int j = -1; j < this.widthPixels + 2; j++) {
                if (yMatrixOff + i < 0 || yMatrixOff + i >= this.mapHeight
                    || xMatrixOff + j < 0 || xMatrixOff + j >= this.mapWidth) {
                    g.drawImage(this.tiles[0],
                        j * Game.TILE_SIZE - xFracOff, i * Game.TILE_SIZE - yFracOff, null);
                } else {
                    g.drawImage(this.tiles[this.tileMapMatrix[yMatrixOff + i][xMatrixOff + j]],
                        j * Game.TILE_SIZE - xFracOff, i * Game.TILE_SIZE - yFracOff, null);
                }
            }
        }
    }
}
