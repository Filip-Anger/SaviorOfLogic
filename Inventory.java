import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.*;



public class Inventory extends SubWindow{

    public ArrayList<Item> items = new ArrayList<>();

    private int invWidth;
    private int invHeight;  
    private final int slotOffset = 35;
    

    PointerInfo a = MouseInfo.getPointerInfo();
    public void draw(Graphics g){
        drawSubWindow(g, 25, 25, Game.WIDTH-66, Game.HEIGHT/2-Game.ORIGINAL_TILE*Game.SCALE-8); //i dont understan this fucking hell why is it not -50
        invWidth = Game.WIDTH-66 - 25;
        invHeight = Game.HEIGHT/2-Game.ORIGINAL_TILE*Game.SCALE-8-25;
        int remainingWidth = invWidth;
        int remainingHeight = invHeight;
        int x = 55;
        int y = 55;
        for (Item item: items){
            int itemSlotSize = item.getWidth() + slotOffset;
            if (itemSlotSize < remainingWidth){
                remainingWidth -= itemSlotSize;
                item.drawInInv(g, x, y);
                x += itemSlotSize;
                
            }else{
                System.out.println(x + " " + y);
                if (item.getHeight()+5 < remainingHeight){
                    remainingHeight -= (item.getHeight()+5);
                    remainingWidth = invWidth;
                    x = 55;
                    y +=item.getHeight()+5;
                    item.drawInInv(g, x, y);
                    x += itemSlotSize;
                    remainingWidth -= itemSlotSize;
                }
            }
        }
    }
    public void hoverItem(int x, int y){
        
    }
    public void addItem(Item item){
        items.add(item);
    }
}
