import java.awt.Graphics;
import java.awt.image.BufferedImage;


public abstract class Item {

    private int type;
    private int x;
    private int y;
    private String ID;
    private BufferedImage sprite;
    private int width = Game.ORIGINAL_TILE*Game.SCALE;
    private int height = Game.ORIGINAL_TILE*Game.SCALE;

    private int subWidth = width+Game.SUBWINDOW_BONUS_SIZE;
    private int subHeight = height+Game.SUBWINDOW_BONUS_SIZE;

    
    
    
    public Item(int type, int x, int y, String ID){
        this.type = type;
        this.x = x;
        this.y = y;
        this.ID = ID;
        

    }

    public void setSprite(Object sprite){
        this.sprite = (BufferedImage)sprite;
    }

    public int getType() { return this.type; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public String getID() { return this.ID; }
    public BufferedImage getSprite() { return this.sprite; }
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
    public int getSubWidth() { return this.subWidth; }
    public int getSubHeight() { return this.subHeight; }
    


    public void moveTo(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        //this.sw.drawSubWindow(g, getX()-20, getY()-20, this.sprite.getWidth()+40, this.sprite.getHeight()+40);
        g.drawImage(sprite, getX()-offsetX, getY()-offsetY, null);
    }

    public void drawInInv(Graphics g, int x, int y){
        g.drawImage(sprite, x, y, null);
    }
    



}
// idealy subclass this