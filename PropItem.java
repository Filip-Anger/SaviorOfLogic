import java.awt.Graphics;
import java.awt.image.BufferedImage;



public class PropItem extends Item {

    private BufferedImage sprite;
    private SubWindow sw;
    public PropItem(int type, int x, int y, String name) {
        super(type, x, y, name); 
        this.sw = new SubWindow();
    }

    @Override
    public void setSprite(Object sprite) {
        TextToGraphics t = new TextToGraphics("Arial", 20);
        this.sprite = t.convert((String) sprite);
    }

    @Override
    public void draw(Graphics g) {
        //this.sw.drawSubWindow(g, getX()-20, getY()-20, this.sprite.getWidth()+40, this.sprite.getHeight()+40);
        g.drawImage(sprite, getX(), getY(), null);
    }
}
