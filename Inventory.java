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
    private Item draggedItem = null;
    

    PointerInfo a = MouseInfo.getPointerInfo();
    public void draw(Graphics g){
        drawSubWindowXY(g, 25, 25, Game.WIDTH/2-(Game.TILE_SIZE), Game.HEIGHT-50); //i dont understan this fucking hell why is it not -50
        invWidth = Game.WIDTH/2-(Game.TILE_SIZE)-25;
        invHeight = Game.HEIGHT-50-25;
        int remainingWidth = invWidth;
        int remainingHeight = invHeight;
        int x = 60;
        int y = 55;
        for (Item item: items){
            int itemSlotSize = item.getInvWidth() + slotOffset;
            if (itemSlotSize < remainingWidth){
                remainingWidth -= itemSlotSize;
                item.drawInInv(g, x, y);
                item.setPos(x, y);
                x += itemSlotSize;
                
            }else{
                if (item.getInvHeight()+5 < remainingHeight){
                    remainingHeight -= (item.getInvHeight()+5);
                    remainingWidth = invWidth;
                    x = 60;
                    y +=item.getInvHeight()+5;
                    item.drawInInv(g, x, y);
                    item.setPos(x, y);
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
    
    public void dragItem(Graphics g, int mouseStartX, int mouseStartY, int mouseX, int mouseY){
        if (draggedItem == null) {
            for (Item item: items){
                int itemWidth = item.getInvWidth()/2; //middle of item
                int itemHeight = item.getInvHeight()/2; //middle of item    
                int itemX = item.getX() + itemWidth; //middle of item
                int itemY = item.getY() + itemHeight; //middle of item
                //System.out.println(itemX + " " + mouseStartX + " " + itemWidth);
                //System.out.println(itemY + " " + mouseStartY + " " + itemHeight);
                if (Math.abs(itemX - mouseStartX) <= (itemWidth) && Math.abs(itemY - mouseStartY) <= (itemHeight+10)){
                    this.draggedItem = item;
                    this.items.remove(item);
                    //item.setPos(mouseStartX, mouseStartY);
                    System.out.println("Started dragging item at " + mouseStartX + " " + mouseStartY);
                    return;
                }
            }
        } else {
            int drawOffsetX = Math.abs(draggedItem.getX() - mouseStartX);
            int drawOffsetY = Math.abs(draggedItem.getY() - mouseStartY);
            //draggedItem.setPos(mouseX, mouseY);
            //System.out.println("X: " + drawOffsetX + " " + mouseStartX + " " + draggedItem.getX());
            //System.out.println("Y: " + drawOffsetY + " " + mouseStartY + " " + draggedItem.getY());
            this.draggedItem.drawInInv(g, mouseX-drawOffsetX, mouseY);
        }
    }
    public Item getDragItem(){
        return this.draggedItem;
    }
    public void nullDragItem(){
        this.draggedItem = null;
    }
    
    public void setDragItem(Item item){
        this.draggedItem = item;
    }
}
