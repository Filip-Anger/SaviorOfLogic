import java.awt.Graphics;


public class Inventory extends SubWindow{
    public void draw(Graphics g){
        drawSubWindow(g, 25, 25, Game.SCREEN_WIDTH-66, Game.SCREEN_HEIGHT-91); //i dont understan this fucking hell why is it not -50
    }
}
