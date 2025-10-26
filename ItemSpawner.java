import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ItemSpawner {

    private String[] inputStrings;
    public ArrayList<Item> allItems = new ArrayList<Item>();
    public ArrayList<Item> visibleItems = new ArrayList<Item>();
    private String tempInputString = "";

    public ItemSpawner() {
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream("ItemDatabase.txt"), 
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
                Item newI = new PropItem(type, id, content, x, y);
                this.addItem(newI);
            }
        }
    }


    public void drawItems(Graphics g, Pair offset) {
        for (Item i : this.visibleItems) {
            i.draw(g, offset);
        }
    }
    
    public void addItem(Item item) {
        if (item.getType().equals("Prop")) {
            this.allItems.add(item);
            this.visibleItems.add(item);
        } else if (item.getType().equalsIgnoreCase("ActionTile")) {
            this.allItems.add(item);
            this.visibleItems.add(item);
        }
    }
    
    public void removeItem(Item item) {
        this.visibleItems.remove(item); 
    }

    public Item getItem (Pair playerPos, Pair size) {
        for (Item item : visibleItems) {
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
