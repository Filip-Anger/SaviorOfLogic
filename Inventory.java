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
        drawSubWindow(g, 25, 25, Game.WIDTH/2-Game.ORIGINAL_TILE*Game.SCALE-8, Game.HEIGHT-75); //i dont understan this fucking hell why is it not -50
        invWidth = Game.WIDTH/2-Game.ORIGINAL_TILE*Game.SCALE-8-25;
        invHeight = Game.HEIGHT-75-25;
        int remainingWidth = invWidth;
        int remainingHeight = invHeight;
        int x = 60;
        int y = 55;
        for (Item item: items){
            int itemSlotSize = item.getInvWidth() + slotOffset;
            if (itemSlotSize < remainingWidth){
                remainingWidth -= itemSlotSize;
                item.drawInInv(g, x, y);
                x += itemSlotSize;
                
            }else{
                if (item.getInvHeight()+5 < remainingHeight){
                    remainingHeight -= (item.getInvHeight()+5);
                    remainingWidth = invWidth;
                    x = 60;
                    y +=item.getInvHeight()+5;
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
