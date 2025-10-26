package com.savioroflogic.ui;

import java.awt.Graphics;

import java.util.ArrayList;


/**TODO: Class description. */
public class Inventory extends SubWindow{

    public ArrayList<com.savioroflogic.items.Item> items = new ArrayList<>();
    private int invWidth;
    private int invHeight;  
    private final int slotOffset = 35;
    private com.savioroflogic.items.Item draggedItem = null;
    
    
    /**
     * Draw inventory window and items inside it.
     * Places items in rows and moves to next row when full.
     */
    public void draw(Graphics g){

        drawSubWindowXY(g, 25, 25, com.savioroflogic.core.Game.WIDTH / 2 - (com.savioroflogic.core.Game.TILE_SIZE), com.savioroflogic.core.Game.HEIGHT - 50); //i dont understan this fucking hell why is it not -50
        invWidth = com.savioroflogic.core.Game.WIDTH / 2 - (com.savioroflogic.core.Game.TILE_SIZE) - 25;
        invHeight = com.savioroflogic.core.Game.HEIGHT - 75;
        int remainingWidth = invWidth;
        int remainingHeight = invHeight;
        int x = 60;
        int y = 55;

        for (com.savioroflogic.items.Item item: items){
            int itemSlotSize = item.getInvWidth() + slotOffset;
            if (itemSlotSize < remainingWidth){
                remainingWidth -= itemSlotSize;
                item.drawInInv(g, x, y);
                item.setPos(x, y);
                x += itemSlotSize;
            } else {
                if (item.getInvHeight() + 5 < remainingHeight){
                    remainingHeight -= (item.getInvHeight()+5);
                    remainingWidth = invWidth;
                    x = 60;
                    y += item.getInvHeight() + 5;
                    item.drawInInv(g, x, y);
                    item.setPos(x, y);
                    x += itemSlotSize;
                    remainingWidth -= itemSlotSize;
                }
            }
        }
    }

    public void addItem(com.savioroflogic.items.Item item){
        items.add(item);
    }
    
    /**
     * Let user pick up an item by clicking and drag it.
     * First click tries to find an item near mouse start.
     * If found, remove it from list and keep it as draggedItem.
     * If already dragging, draw the dragged item following mouse.
     */
    public void dragItem(Graphics g, int mouseStartX, int mouseStartY, int mouseX, int mouseY){
        if (draggedItem == null) {
            for (com.savioroflogic.items.Item item: items) {
                int itemWidth = item.getInvWidth() / 2; 
                int itemHeight = item.getInvHeight() / 2;   
                int itemX = item.getX() + itemWidth; 
                int itemY = item.getY() + itemHeight; 
                if (Math.abs(itemX - mouseStartX) <= (itemWidth) && Math.abs(itemY - mouseStartY) <= (itemHeight + 10)) {
                    this.draggedItem = item;
                    this.items.remove(item);
                    return;
                }
            }
        } else {
            int drawOffsetX = Math.abs(draggedItem.getX() - mouseStartX);
            this.draggedItem.drawInInv(g, mouseX-drawOffsetX, mouseY);
        }
    }


    public com.savioroflogic.items.Item getDragItem() {
        return this.draggedItem;
    }

    public void setDragItem(com.savioroflogic.items.Item item) {
        this.draggedItem = item;
    }

    public void nullDragItem() {
        this.draggedItem = null;
    }
}
