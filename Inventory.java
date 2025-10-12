import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.*;



public class Inventory extends SubWindow{

    public ArrayList<Item> items = new ArrayList<Item>();

    PointerInfo a = MouseInfo.getPointerInfo();
    public void draw(Graphics g){
        drawSubWindow(g, 25, 25, Game.SCREEN_WIDTH-66, Game.SCREEN_HEIGHT-91); //i dont understan this fucking hell why is it not -50
    }
    public void hoverItem(int x, int y){
        
    }
}
