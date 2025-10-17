import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.*;



public class Inventory extends SubWindow{

     public ArrayList<Item> items = new ArrayList<>();

    PointerInfo a = MouseInfo.getPointerInfo();
    public void draw(Graphics g){
        drawSubWindow(g, 25, 25, Game.WIDTH - 66, Game.HEIGHT/2-Game.ORIGINAL_TILE*Game.SCALE-8); //i dont understan this fucking hell why is it not -50
        
    }
    public void hoverItem(int x, int y){
        
    }
    public void addItem(Item item){
        
    }
}
