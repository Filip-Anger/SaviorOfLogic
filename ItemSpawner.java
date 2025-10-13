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
    public ArrayList<Item> visibleItems = new ArrayList<Item>();
    //public ArrayList<Item> allProps = new ArrayList<Item>();
    private String tempInputString = "";
    Random rand = new Random();


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
            addItem(i.split(";"));
        }
        
    }
    public void drawItems(Graphics g, int offsetX, int offsetY){
        for (Item i : this.visibleItems){
            i.draw(g, offsetX, offsetY);
        }
        
    }

    
    public void addItem(String[] itemData){
        
        if (itemData[0].equals("0")){
            if (itemData[3].equals("-1") && itemData[4].equals("-1")){
                Item i = new PropItem(0, rand.nextInt(500), rand.nextInt(500), itemData[1]);
                i.setSprite(itemData[2]);
                allItems.add(i);
                visibleItems.add(i);
                
            }else{
                Item i = new PropItem(0, Integer.parseInt(itemData[3]), Integer.parseInt(itemData[3]), itemData[1]);
                i.setSprite(itemData[2]);
                allItems.add(i);
                visibleItems.add(i);
            }
        }
    }
}
