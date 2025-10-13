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
    public void drawItems(Graphics g){
        PropItem a = new PropItem(0, 100, 100, "test");
        a.setSprite("A=>B");
        for (Item i : this.visibleItems){
            i.draw(g);
        }
        a.draw(g);
    }
}
