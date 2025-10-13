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

    public int getType() { return type; }
    public int getX() { return x; }
    public int getY() { return y; }
    public String getName() { return name; }


    public void moveTo(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g){
        g.drawImage(this.sprite, this.x, this.y, null);
    }



}
// idealy subclass this