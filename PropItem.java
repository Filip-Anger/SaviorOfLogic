import java.awt.Graphics;
import java.awt.image.BufferedImage;



public class PropItem extends Item {

    private BufferedImage sprite;
    private BufferedImage invSprite;
    private int size = 20;
    private SubWindow sw;
    private int invWidth;
    private int invHeight;
    private int subWidth;
    private int subHeight;
    private TextToGraphics t;
    

    public PropItem(int type, int x, int y, String id) {
        super(type, x, y, id); 
        this.sw = new SubWindow();
        this.t  = new TextToGraphics("Arial Unicode MS", size);
    }

    @Override
    public void setSprite(Object sprite) {
        
        this.sprite = t.convert((String) sprite);
        this.subWidth = t.getWidth() + Game.SUBWINDOW_BONUS_SIZE;
        this.subHeight = t.getHeight() + Game.SUBWINDOW_BONUS_SIZE;
        this.invSprite = t.convertWithDash((String) sprite);
        this.invWidth = t.getWidth();
        this.invHeight = t.getHeight();
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        //this.sw.drawSubWindow(g, getX()-20, getY()-20, this.sprite.getWidth()+40, this.sprite.getHeight()+40);
        this.sw.drawSubWindow(g, getX()-offsetX, getY()-offsetY, subWidth, subHeight);
        g.drawImage(sprite, getX()-offsetX+Game.SUBWINDOW_BONUS_SIZE/2, getY()-offsetY+Game.SUBWINDOW_BONUS_SIZE/2, null);
    }

    @Override
    public void drawInInv(Graphics g, int x, int y){
        
        g.drawImage(invSprite, x-15, y-15, null);
    }

    @Override
    public int getInvWidth() {
        return this.invWidth;
    }

    @Override
    public int getInvHeight() {
        return this.invHeight;
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
