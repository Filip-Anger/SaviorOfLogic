import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class ItemSpawner {
    // itemDatabase.txt
    // itemSprites.png
    //load these 2
    private String[] inputStrings;
    public ArrayList<Item> allItems = new ArrayList<Item>();
    //public ArrayList<ArrayList<Integer>> ItemsPos = new ArrayList<ArrayList<Integer>>();
    public ArrayList<Item> visibleItems = new ArrayList<Item>();
    //public ArrayList<Item> allProps = new ArrayList<Item>();
    private String tempInputString = "";
    


    public ItemSpawner(){
        
        try {
            FileInputStream fis = new FileInputStream("ItemDatabase.txt");
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            int ch;
            while ((ch = isr.read()) != -1) {
                //System.out.println(ch);
                
                tempInputString += (char)ch;
                
            }
            isr.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(
                "File not found, ensure the file exists");
        }
        catch (IOException e) {
            System.out.println(
                "An error occurred while reading the file");
        }
        
        inputStrings = tempInputString.split("\n");
        for (String i : inputStrings){
            i = i.replaceAll("\\n|\\r", "");
            String[] line = i.split(";");
            if (line.length == 5) {
                String type = line[0];
                int id = Integer.parseInt(line[1]);
                String content = line[2];
                int x = Integer.parseInt(line[3]);
                int y = Integer.parseInt(line[4]);
                Item newI = new PropItem(type, id, content, x, y);
                this.addItem(newI);
            }
            
        }
        
        
    }
    public void drawItems(Graphics g, Pair offset){
        for (Item i : this.visibleItems){
            i.draw(g, offset);
        }
        //this.allItems.get(0).drawInInv(g, 0, 0);
        
    }

    
    public void addItem(Item item){
        //type, id, whatever, x, y
        // If has type Proposition
        if (item.getType().equals("Prop")){
            allItems.add(item);
            visibleItems.add(item);
        } else if (item.getType().equalsIgnoreCase("ActionTile")) {
            allItems.add(item);
        }
    }
    public Item getItem(Pair playerPos, Pair size){
        for(Item item : visibleItems){
            int itemWidth = item.getSubWidth()/2; //middle of item
            int itemHeight = item.getSubHeight()/2; //middle of item
            int itemX = item.getX() + itemWidth; //middle of item
            int itemY = item.getY() + itemHeight; //middle of item
            int playerWidth = size.x()/2; //middle of player
            int playerHeight = size.y()/2; //middle of player
            int playerX = playerPos.x() + playerWidth; //middle of player
            int playerY = playerPos.y() + playerHeight; //middle of player
            //System.out.println(itemX + " " + (x + width) + " " + itemWidth + " " + width);

            
            //if(Math.abs(itemX - (x + width)) <= (itemWidth + width) && (((y - height)) <= itemY && (itemY - itemHeight) <= (y + height))){ //-20 to adjust, looks bettter
            if(Math.abs(itemX - playerX) <= (itemWidth + playerWidth) && Math.abs(itemY - playerY) <= (itemHeight + playerHeight)){ //mareks implementation
                visibleItems.remove(item);
                // remove put into separate method
                return item;
            }
        }
        return null;
    }
}
