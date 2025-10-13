import java.awt.Graphics;
import java.awt.image.BufferedImage;


public abstract class Item {

    private int type;
    private int x;
    private int y;
    private String name;
    private BufferedImage sprite;
    
    
    public Item(int type, int x, int y, String name){
        this.type = type;
        this.x = x;
        this.y = y;
        this.name = name;
        

    }

    public void setSprite(Object sprite){
        this.sprite = (BufferedImage)sprite;
    }

    public int getType() { return this.type; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public String getName() { return this.name; }
    public BufferedImage getSprite() { return this.sprite; }


    public void moveTo(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        //this.sw.drawSubWindow(g, getX()-20, getY()-20, this.sprite.getWidth()+40, this.sprite.getHeight()+40);
        g.drawImage(sprite, getX(), getY(), null);
    }



}
// idealy subclass this