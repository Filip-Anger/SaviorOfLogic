import java.awt.Graphics;
import java.util.ArrayList;

public class ItemSpawner {
    // itemDatabase.txt
    // itemSprites.png
    //load these 2
    public ArrayList<Item> allItems = new ArrayList<Item>();
    public ArrayList<Item> visibleItems = new ArrayList<Item>();

    public ItemSpawner(){
        
        
    }
    public void drawItems(Graphics g, int offsetX, int offsetY){
        PropItem a = new PropItem(0, 0, 0, "test");
        a.setSprite("∀A=>B");
        for (Item i : this.visibleItems){
            i.draw(g, offsetX, offsetY);
        }
        
    }
}
