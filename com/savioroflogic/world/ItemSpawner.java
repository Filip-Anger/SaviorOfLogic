package com.savioroflogic.world;

import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.savioroflogic.items.PropItem;

/** Class that handles spawning items in the game.
 * Read item database file and make items list.
 * File lines look like: type;id;content;x;y.
 */
public class ItemSpawner {

    private String[] inputStrings;
    public ArrayList<com.savioroflogic.items.Item> allItems = new ArrayList<>();
    public ArrayList<com.savioroflogic.items.Item> visibleItems = new ArrayList<>();
    private String tempInputString = "";

    public ItemSpawner() {
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream("com\\savioroflogic\\items\\ItemDatabase.txt"), 
            StandardCharsets.UTF_8);
            int ch;
            while ((ch = isr.read()) != -1) {
                tempInputString += (char)ch;
            }
            isr.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
        inputStrings = tempInputString.split("\n");

        for (String i : inputStrings) {
            i = i.replaceAll("\\n|\\r", "");
            String[] line = i.split(";");

            if (line.length == 5) {
                String type = line[0];
                int id = Integer.parseInt(line[1]);
                String content = line[2];
                int x = Integer.parseInt(line[3]);
                int y = Integer.parseInt(line[4]);
                com.savioroflogic.items.Item newI = new PropItem(type, id, content, x, y);
                this.addItem(newI);
            }
        }
    }


    public void drawItems(Graphics g, com.savioroflogic.util.Pair offset) {
        for (com.savioroflogic.items.Item i : this.visibleItems) {
            i.draw(g, offset);
        }
    }
    

    public void addItem(com.savioroflogic.items.Item item) {
        if (item.getType().equals("Prop")) {
            this.allItems.add(item);
            this.visibleItems.add(item);
        } else if (item.getType().equalsIgnoreCase("ActionTile")) {
            this.allItems.add(item);
            this.visibleItems.add(item);
        }
    }
    
    public void removeItem(com.savioroflogic.items.Item item) {
        this.visibleItems.remove(item); 
    }

    /**
     * Find item near player and return it using center point distance check.
     * If found, remove from visible list and return that item.
     */
    public com.savioroflogic.items.Item getItem (com.savioroflogic.util.Pair playerPos, com.savioroflogic.util.Pair size) {
        for (com.savioroflogic.items.Item item : visibleItems) {
            int itemWidth = item.getSubWidth() / 2; 
            int itemHeight = item.getSubHeight() / 2;
            int itemX = item.getX() + itemWidth; 
            int itemY = item.getY() + itemHeight; 
            int playerWidth = size.x()/2;
            int playerHeight = size.y()/2; 
            int playerX = playerPos.x() + playerWidth; 
            int playerY = playerPos.y() + playerHeight; 
            if (Math.abs(itemX - playerX) <= (itemWidth + playerWidth) && Math.abs(itemY - playerY) <= (itemHeight + playerHeight)) {
                removeItem(item); 
                return item;
            }
        }
        return null;
    }
}
