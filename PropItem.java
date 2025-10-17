import java.awt.Graphics;
import java.awt.image.BufferedImage;



public class PropItem extends Item {

    private BufferedImage sprite;
    private int size = 20;
    private SubWindow sw;
    private int width;
    private int height;
    private int subWidth;
    private int subHeight;
    

    public PropItem(int type, int x, int y, String id) {
        super(type, x, y, id); 
        this.sw = new SubWindow();
    }

    @Override
    public void setSprite(Object sprite) {
        TextToGraphics t = new TextToGraphics("Arial Unicode MS", size);
        this.sprite = t.convert((String) sprite);
        this.width = t.getWidth();
        this.height = t.getHeight();
        this.subWidth = this.width + Game.SUBWINDOW_BONUS_SIZE;
        this.subHeight = this.height + Game.SUBWINDOW_BONUS_SIZE;
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        //this.sw.drawSubWindow(g, getX()-20, getY()-20, this.sprite.getWidth()+40, this.sprite.getHeight()+40);
        this.sw.drawSubWindow(g, getX()-offsetX, getY()-offsetY, subWidth, subHeight);
        g.drawImage(sprite, getX()-offsetX+Game.SUBWINDOW_BONUS_SIZE/2, getY()-offsetY+Game.SUBWINDOW_BONUS_SIZE/2, null);
    }

    @Override
    public void drawInInv(Graphics g, int x, int y){
        g.drawImage(sprite, x-15, y-15, null);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getSubWidth() {
        return this.subWidth;
    }       

    @Override
    public int getSubHeight() {
        return this.subHeight;
    }   


    
    
    
}
