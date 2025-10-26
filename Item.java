import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**TODO: Class description. */
public abstract class Item {

    protected String type;
    protected int x;
    protected int y;
    protected int id;
    int xCord;
    int yCord;
    protected String content;
    private BufferedImage sprite;
    private int width = Game.MAP_RESOLUTION*Game.SCALE;
    private int height = Game.MAP_RESOLUTION*Game.SCALE;

    private int subWidth = width+Game.SUBWINDOW_BONUS_SIZE;
    private int subHeight = height+Game.SUBWINDOW_BONUS_SIZE;
    

    
    public Item(String type, int id, String content, int x, int y){
        this.type = type;
        this.x = x;
        this.y = y;
        this.id = id;
        this.content = content;
        

    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSprite(Object sprite){
        this.sprite = (BufferedImage)sprite;
    }

    public String getType() { return this.type; }
    public String getContent() { return this.content; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public int getID() { return this.id; }
    public BufferedImage getSprite() { return this.sprite; }
    public int getInvWidth() { return this.width; }
    public int getInvHeight() { return this.height; }
    public int getSubWidth() { return this.subWidth; }
    public int getSubHeight() { return this.subHeight; }
    


    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g, Pair offset) {
        //this.sw.drawSubWindow(g, getX()-20, getY()-20, this.sprite.getWidth()+40, this.sprite.getHeight()+40);
        g.drawImage(sprite, getX() - offset.x(), getY() - offset.y(), null);
    }

    public void drawInInv(Graphics g, int x, int y){
        g.drawImage(sprite, x, y, null);
    }
}
// idealy subclass this