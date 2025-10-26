package com.savioroflogic.proof;

import java.awt.Graphics;

import com.savioroflogic.core.Game;
import com.savioroflogic.ui.SubWindow;
import com.savioroflogic.ui.Inventory;
import com.savioroflogic.util.Pair;
import com.savioroflogic.items.Item;
import com.savioroflogic.items.PropItem;


/** Class that handles the proof submission window.
 * The window shows the inventory and proof builder.
 */
public class ProofSubmitter {
    private ProofBuilder pb;
    private int x = 320;
    private int y = 500;
    private int width = Game.MAP_RESOLUTION*Game.SCALE;
    private int height = Game.MAP_RESOLUTION*Game.SCALE;
    private SubWindow sb;
    private Inventory inventory;
    
    public ProofSubmitter(Inventory inv) {
        this.sb = new SubWindow();
        this.inventory = inv;
        pb = new ProofBuilder();
    }


    public void draw(Graphics g) {
        this.sb.drawSubWindowXY(g, Game.WIDTH / 2 + (Game.MAP_RESOLUTION * Game.SCALE), 25, Game.WIDTH - 40, Game.HEIGHT - 50);
        this.inventory.draw(g);
        this.pb.draw(g);
    }

    public boolean isNear(Pair playerPos, Pair size) {
        /**
         * Check if player is close enough to submitter.
         * Uses center-distance
         */
        int submitterWidth = this.width / 2; 
        int submitterHeight = this.height / 2; 
        int submitterX = this.x + submitterWidth; 
        int submitterY = this.y + submitterHeight; 
        int playerWidth = size.x() / 2; 
        int playerHeight = size.y() / 2;  
        int playerX = playerPos.x() + playerWidth; 
        int playerY = playerPos.y() + playerHeight; 
        if (Math.abs(submitterX - playerX) <= (submitterWidth + playerWidth) && Math.abs(submitterY - playerY) <= (submitterHeight + playerHeight)){ //add proximity distance in case of borders
            return true;
        }
        return false;
    }

    public void dropItem(int mouseX, int mouseY) {
        /**
         * Drop the currently dragged item.
         * If drop is on right side (proof area) try to place
         * item into the correct proof row based on mouse Y.
         * If a row had an item, give it back to inventory.
         * If drop is not on right side, return item back to inventory.
         */
        Item item = inventory.getDragItem();
        if (item == null){
            return;
        }
        else if (mouseX > Game.WIDTH / 2) {

            int rowHeight = pb.getRowHeight();
            int lineCount = pb.getLineCount();
            
            for(int i = 1; i < lineCount + 1; i++) {
                
                if (i * rowHeight + 55 >= mouseY) {
                    Item tempItem = pb.getItem(i - 1);
                    if (tempItem.getID() != -1) {
                        inventory.addItem(tempItem);
                    }
                    pb.changeProofLine(i - 1, item);
                    inventory.nullDragItem();
                    return;
                }
            }
        }
        inventory.addItem(item);
        inventory.nullDragItem();
    }
    
    public void setDragItem(Graphics g, int mouseStartX, int mouseStartY, int mouseX, int mouseY) {
        /**
         * Sets the dragged item from the submitter window in inventory.
         * Replace that row with a blank.
         */
        Item item = inventory.getDragItem();
        int rowHeight = pb.getRowHeight();
        int lineCount = pb.getLineCount();
        if (item == null){
            for(int i = 1; i < lineCount+1; i++) {
    
                if (i*rowHeight + 55 >= mouseY) {
                    Item draggedItem = pb.getItem(i-1);
                    if (draggedItem.getID() == -1) {
                        return;
                    }
                    Item tempItem = new PropItem("...",-1,"...",-1,-1);
                    pb.changeProofLine(i-1, tempItem);
                    inventory.setDragItem(draggedItem);
                    return;
                }
            }
        
        }
    }

    public boolean submitProofs() {
        return pb.checkProofs();
    }
}
