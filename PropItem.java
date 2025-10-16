import java.awt.Graphics;
import java.awt.image.BufferedImage;



public class PropItem extends Item {

    private BufferedImage sprite;
    private int size = 20;
    private int width;
    private int height;
    
    //private SubWindow sw;
    public PropItem(int type, int x, int y, String id) {
        super(type, x, y, id); 
        //this.sw = new SubWindow();
    }

    @Override
    public void setSprite(Object sprite) {
        TextToGraphics t = new TextToGraphics("Arial Unicode MS", size);
        this.sprite = t.convert((String) sprite);
        this.width = t.getWidth();
        this.height = t.getHeight();
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        //this.sw.drawSubWindow(g, getX()-20, getY()-20, this.sprite.getWidth()+40, this.sprite.getHeight()+40);
        g.drawImage(sprite, getX()-offsetX, getY()-offsetY, null);
    }
    @Override
    public int getWidth() { return this.width; }
    @Override
    public int getHeight() { return this.height; }
}
