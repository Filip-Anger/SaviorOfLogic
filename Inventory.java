import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.PointerInfo;

import javax.swing.*;



public class Inventory extends SubWindow{
    PointerInfo a = MouseInfo.getPointerInfo();
    public void draw(Graphics g){
        drawSubWindow(g, 25, 25, Game.SCREEN_WIDTH-66, Game.SCREEN_HEIGHT-91); //i dont understan this fucking hell why is it not -50
    }
    public void hoverItem(int x, int y){
        
    }
}
