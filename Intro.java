import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.naming.SizeLimitExceededException;

public class Intro {
    Image introImage;
    FileInputStream fileInputStream;
    InputStreamReader inputStreamReader;
    int storyTextCounter = 0;
    private ArrayList<BufferedImage> storyText;


    public Intro() {
        try{
            this.introImage = ImageIO.read(new File("Tileset/atlas.jpg"));
            this.fileInputStream = new FileInputStream("StoryText.txt");

        } catch (IOException e){
            e.getStackTrace();
        }

        try {
            this.inputStreamReader = new InputStreamReader(this.fileInputStream, StandardCharsets.UTF_8);
            int ch;
            String wholeText = "";
            while ((ch = this.inputStreamReader.read()) != -1) {
                
                wholeText += (char)ch;
                
            }
            int width = Game.WIDTH - 40 - 15;
            int height = Game.HEIGHT/2 - 50 - 15;
            TextToGraphics textToGraphics = new TextToGraphics("Arial Unicode MS", 20);
            this.storyText = textToGraphics.convertAndSplit(wholeText.split(" "), 
                width - 2 * Game.SUBWINDOW_BONUS_SIZE, height - 2 * Game.SUBWINDOW_BONUS_SIZE);
            this.fileInputStream.close();
        } catch (IOException e) {
            e.getStackTrace();
        }

    }


    public void drawStartScreen(Graphics g, PlayerSprite player){
        g.setFont(g.getFont().deriveFont(Font.BOLD,96));
        String text = "Saviour of Logic";
        g.setColor(Color.GRAY);
        g.drawString(text, 22, 105);
        g.setColor(Color.WHITE);
        g.drawString(text, 17, 100);

        Image playerImage =  player.getIdleImage();
        Pair size = player.getSize();
        g.drawImage(playerImage, (Game.WIDTH - size.x()) / 2, (Game.HEIGHT - size.y()) / 2, size.x(), size.y(), null);
        g.setFont(g.getFont().deriveFont(Font.BOLD,52));
        text = "Press E to start a game";
        g.setColor(Color.WHITE);
        g.drawString(text, 105, 500);
    }
    
    public boolean lastText(){
        if (this.storyTextCounter +1 == this.storyText.size()){
            return true;
        }
        this.storyTextCounter += 1;
        return false;
    }

    public void drawStoryScreen(Graphics g) {
        g.drawImage(this.introImage, 0, 0, Game.WIDTH, Game.HEIGHT, null);
        SubWindow sb = new SubWindow();
        int x = 20;
        int y = Game.HEIGHT/2 - 50;
        int width = Game.WIDTH - 2*x - 15;
        int height = Game.HEIGHT/2 - 50 - 15;
        sb.drawSubWindow(g, x, y,  width, height);
        g.drawImage(this.storyText.get(this.storyTextCounter), x+Game.SUBWINDOW_BONUS_SIZE, y+Game.SUBWINDOW_BONUS_SIZE, null);
    }
}
