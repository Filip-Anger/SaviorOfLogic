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

    public PropItem(String type, int id, String content, int x, int y) {
        super(type, id, content, x, y);
        if (x == -1 && y == -1){
            x = (int) Math.random() * (500*32 + 1);
            y = (int) Math.random() * (40*32 + 1) + 5*32;
        }
        this.setPos(x, y);
        this.sw = new SubWindow();
        this.t  = new TextToGraphics("Arial Unicode MS", size);
        this.setSprite(content);
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
    public void draw(Graphics g, Pair offset) {
        //this.sw.drawSubWindow(g, getX()-20, getY()-20, this.sprite.getWidth()+40, this.sprite.getHeight()+40);
        this.sw.drawSubWindow(g, getX() - offset.x(), getY() - offset.y(), subWidth, subHeight);
        g.drawImage(sprite, getX() - offset.x() +Game.SUBWINDOW_BONUS_SIZE/2, getY() - offset.y() + Game.SUBWINDOW_BONUS_SIZE/2, null);
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
