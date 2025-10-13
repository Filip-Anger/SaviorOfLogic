import java.awt.Graphics;

public class PropItem extends Item {

    private String proposition;
    private SubWindow sw;
    public PropItem(int type, int x, int y, String name) {
        super(type, x, y, name); 
        this.sw = new SubWindow();
    }

    @Override
    public void setSprite(Object proposition) {
        this.proposition = (String)proposition;
    }

    @Override
    public void draw(Graphics g) {
        this.sw.drawSubWindow(g, getX()-50, getY()-50, 200, 200);
    }
}
