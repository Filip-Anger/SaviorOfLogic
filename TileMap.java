
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import javax.imageio.ImageIO;

/** Tiles are loaded here, returned on request. */
public class TileMap {
    private Image[] tiles; // grass, path, tree, water, enemy
    private int[][] tileMapMatrix;
    private ArrayList<MutliTile> mutliTiles;
    private int mapHeight = 0;
    private int mapWidth = 0;
    private final int widthPixels;
    private final int heightPixels;
    private final File mapFile;
    // private Pair offset;
    private  Scanner sc;
    private Set<Integer> forbiddenTiles;
    private int basicFloor = 43;
    private int[] brokenFloors = new int[]{17, 18, 19, 20, 21};;
    private Random random = new Random();
    private ArrayList<Pair> spikes = new ArrayList<>();
    private Set<Integer> actionTiles;
    private ArrayList<Pair> door = new ArrayList<>();

    // private Set<Integer>  = new HashMap<>();
    // Ked si v tejto lokacii a stalcis E tak posli niekam info ze sa pouzil ten item

    public TileMap(ItemSpawner itemSpawner) {
        // this.offset = offset;
        this.mapFile = new File("Tileset/DungeonMap.txt");
                try {
            this.sc = new Scanner(this.mapFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.widthPixels = Game.WIDTH / Game.TILE_SIZE;
        this.heightPixels = Game.HEIGHT / Game.TILE_SIZE;
        this.actionTiles = new HashSet<>();
        this.actionTiles.add(105);
        this.actionTiles.add(106);
        this.actionTiles.add(22);
        this.actionTiles.add(118);
        this.setMapSize();
        this.loadMap(itemSpawner);
        this.loadTiles();
        this.scale(Game.TILE_SIZE);
    }
    /**
     * 
     * @param start Including
     * @param end Excluding
     * @param forbidden Collision on tiles
     */
    private void loadStructure(MutliTile tileStructure) {
        int end = tileStructure.getStrat() + tileStructure.getLength();
        Set<Integer> nums = new HashSet<>();
        // System.out.print(tileStructure.getName()+ ": ");
        try {
            for (int i = tileStructure.getStrat(); i < end; i++) {
                this.tiles[i] = ImageIO.read(new File("Tileset/Dungeon/" + String.format("%03d", i) + ".png"));
                nums.add(i);
                // System.out.print(i + " ");
            }
            // System.out.println();
            if (tileStructure.getCollision()) {
                this.forbiddenTiles.addAll(nums);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
    /** Load in all tiles. */
    private void loadTiles() {
        this.forbiddenTiles = new HashSet<>();
        this.tiles = new Image[144];
        this.mutliTiles = new ArrayList<>();
        // this.mutliTiles.add(new MutliTile("gateClosed", 0, 8, true));
        this.mutliTiles.add(new MutliTile("gateEdge1", 0, 1, false));
        this.mutliTiles.add(new MutliTile("gateEdge1", 3, 1, false));
        this.mutliTiles.add(new MutliTile("gateClosed1", 1, 2, true));
        this.mutliTiles.add(new MutliTile("gateClosed1", 4, 4, true));
        this.mutliTiles.add(new MutliTile("gateOpened", 140, 4, false));
        this.mutliTiles.add(new MutliTile("floor", 43, 1, false));
        this.mutliTiles.add(new MutliTile("floorsBroken", 16, 6, false));
        this.mutliTiles.add(new MutliTile("chestClosed", 22, 1, false));
        this.mutliTiles.add(new MutliTile("chestEmptyClosedOpened", 100, 2, false));
        this.mutliTiles.add(new MutliTile("chestFullClosedOpened", 109, 2, false));
        this.mutliTiles.add(new MutliTile("mimicClosedOpened", 118, 2, false));
        this.mutliTiles.add(new MutliTile("skull", 10, 2, false));
        this.mutliTiles.add(new MutliTile("spikesUp", 25, 1, true));
        this.mutliTiles.add(new MutliTile("spikesUp", 26, 1, false));
        this.mutliTiles.add(new MutliTile("pluvace", 30, 5, true));
        this.mutliTiles.add(new MutliTile("potionBlueGreen", 23, 2, false));
        this.mutliTiles.add(new MutliTile("pillar", 27, 3, true));
        // this.mutliTiles.add(new MutliTile("bomb", 44, 3, false));
        this.mutliTiles.add(new MutliTile("walls", 35, 7, true));
        this.mutliTiles.add(new MutliTile("heart", 77, 3, false));
        this.mutliTiles.add(new MutliTile("lever", 105, 2, false));

        for (MutliTile structure : this.mutliTiles) {
            
            this.loadStructure(structure);
        }
    }

    public void scale(int newSize) {
        for (int i = 0; i < this.tiles.length; i++) {
            if (this.tiles[i] != null) {
                this.tiles[i] = this.tiles[i].getScaledInstance(newSize, newSize, Image.SCALE_DEFAULT);
            }
        }       
    }

    public void setMapSize() {
        if (sc.hasNextInt()) {
            this.mapWidth = sc.nextInt();
        }
        if (sc.hasNextInt()) {
            this.mapHeight = sc.nextInt();
        }
    }
    
    public void getMapSize() {
        String line = "";
        while(sc.hasNextLine()) {
            this.mapHeight += 1;
            line = sc.nextLine();
        }
        this.mapWidth = line.split(" ").length;
        System.out.println(mapWidth + " " + mapHeight);
    }

    private int randomFloor(int tileNum) {
        if (tileNum != this.basicFloor) {
            return tileNum;
        }
        if (Math.random() > 0.85) {
            if (Math.random() >= 0.5) {
                return 17;
            }
            return random.nextInt(this.brokenFloors.length - 1) + this.brokenFloors[1];
        }
        return tileNum;
    } 

    public void loadMap(ItemSpawner itemSpawner) {
        int t;
        this.tileMapMatrix = new int[this.mapHeight][this.mapWidth];
        for (int y = 0; y < this.mapHeight; y++) {
            for (int x = 0; x < this.mapWidth; x++) {
                if (sc.hasNextInt()) {
        
                    t = randomFloor(sc.nextInt());
                    this.tileMapMatrix[y][x] = t;
                    if (this.actionTiles.contains(t)) {
                        if (t == 105) {
                            itemSpawner.addItem(new ActionItem("actionTile", x, y, 106));
                        }
                        itemSpawner.addItem(new ActionItem("actionTile", x, y, t));
                    }
                    if (t == 25 || t == 26) {
                        this.spikes.add(new Pair(x, y));
                    } else if (t == 1 || t == 2 || t == 5 || t == 6) {
                        this.door.add(new Pair(x, y));
                    }
                }
            }
        }
    }
    public void openDoor() {
        if (this.door.size() == 0) {
            System.out.println("BAD");
            return;
        }
        if (this.tileMapMatrix[this.door.get(0).y()][this.door.get(0).x()] == 1) {
            for (int i = 0; i < this.door.size(); i++) {
                this.tileMapMatrix[this.door.get(i).y()][this.door.get(i).x()] = 140 + i;
            } 
        }
    }

    public void actionUsed(Item item) {
        switch (item.getContent()) {
            case "Lever":
                this.leverPress(item.xCord, item.yCord);
                break;
            case "Chest":
                this.openChest(item.xCord, item.yCord);
                break;
            case "Mimic":
                this.mimic(item.xCord, item.yCord);
            default:
                throw new AssertionError();
        }
    }

    private void mimic(int x, int y) {
        if (this.tileMapMatrix[y][x] == 118) {
            this.tileMapMatrix[y][x] = 119;
        }
    }
    private void leverPress(int x, int y) {
        if (this.tileMapMatrix[y][x] == 105) {
            this.tileMapMatrix[y][x] = 106;
        } else if (this.tileMapMatrix[y][x] == 106) {
            this.tileMapMatrix[y][x] = 105;
        }
        this.spikeSwap();
    }

    private void spikeSwap() {
        for (Pair cords : this.spikes) {
            if (this.tileMapMatrix[cords.y()][cords.x()] == 25) {
                this.tileMapMatrix[cords.y()][cords.x()] = 26;
            } else if (this.tileMapMatrix[cords.y()][cords.x()] == 26) {
                this.tileMapMatrix[cords.y()][cords.x()] = 25;
            }
        }
    }

    private void openChest(int x, int y) {
        if (this.tileMapMatrix[y][x] == 22) {
            this.tileMapMatrix[y][x] = 110;
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
        else if (cordXRight >= (this.mapWidth - 1) * Game.TILE_SIZE) {
            position.setX(this.mapWidth * (Game.TILE_SIZE - 1));
        }
        else if ((deltaX < 0) && (cordXLeft < this.mapWidth) && // Go up - if edge snap to current square
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
                int x = j * Game.TILE_SIZE - xFracOff;
                int y = i * Game.TILE_SIZE - yFracOff;
                if (yMatrixOff + i < 0 || yMatrixOff + i >= this.mapHeight
                    || xMatrixOff + j < 0 || xMatrixOff + j >= this.mapWidth) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, Game.TILE_SIZE, Game.TILE_SIZE);
                } else {
                    int tileNum = this.tileMapMatrix[yMatrixOff + i][xMatrixOff + j];

                    if ((xMatrixOff + j == 0 || yMatrixOff + i == 0 || xMatrixOff + j == (this.mapWidth - 1) || yMatrixOff + i == this.mapHeight -1)
                        || tileNum == -1) {
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, Game.TILE_SIZE, Game.TILE_SIZE);
                    }
                    else if (tileNum != this.basicFloor) {
                        g.drawImage(this.tiles[this.basicFloor], x, y, null);
                    }
                    if (tileNum != -1) {
                        g.drawImage(this.tiles[tileNum], x, y, null);
                    }
                }
            }
        }
    }
}
