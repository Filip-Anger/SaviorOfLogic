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
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.naming.SizeLimitExceededException;
import javax.swing.JTextPane;


public class Intro extends JTextPane{
    Image introImage;
    int storyTextCounter = 0;
    int x;
    int y;
    int width;
    int height;
    Scanner sc;
    ArrayList<String> textPragraphs;
    JTextPane pane;
    SubWindow sb;

    public Intro() {
        try{
            this.introImage = ImageIO.read(new File("Tileset/atlas.jpg"));
            this.sc = new Scanner(new File("StoryText.txt"));
        } catch (IOException e){
            e.getStackTrace();
        }
        this.textPragraphs = new ArrayList<>();
        String word;
        String paragraph = "";
        while (sc.hasNext()) {
            word = sc.next();
            if (word.equals("vykokotitSa")) {
                paragraph = paragraph.strip();
                this.textPragraphs.add(paragraph);
                paragraph = "";
            } else {
                paragraph += word + " ";
            }
        }
        if (paragraph.length() > 0) {
            this.textPragraphs.add(paragraph);
        }
        this.pane = new JTextPane();
            // int width = Game.WIDTH - 40 - 15;
            // int height = Game.HEIGHT/2 - 50 - 15;
            // TextToGraphics textToGraphics = new TextToGraphics("Arial Unicode MS", 20);
            // this.storyText = textToGraphics.convertAndSplit(wholeText.split(" "), 
            //     width - 2 * Game.SUBWINDOW_BONUS_SIZE, height - 2 * Game.SUBWINDOW_BONUS_SIZE);
        }



    public void drawStartScreen(Graphics g, PlayerSprite player){
        g.setFont(g.getFont().deriveFont(Font.BOLD,96));
        String text = "Saviour of Logic";
        g.setColor(Color.GRAY);
        g.drawString(text, 22, 105);
        g.setColor(Color.WHITE);
        g.drawString(text, 17, 100);

        BufferedImage playerImage =  player.getIdleImage();
        int loadScale = 2;
        Pair size = new Pair(playerImage.getWidth() * loadScale,  playerImage.getHeight() * loadScale);
        g.drawImage(playerImage, (Game.WIDTH - size.x()) / 2, (Game.HEIGHT - size.y()) / 2, size.x(), size.y(), null);
        g.setFont(g.getFont().deriveFont(Font.BOLD,52));
        text = "Press E to start a game";
        g.setColor(Color.WHITE);
        g.drawString(text, 105, 500);
    }

    public boolean drawNextEndLast(Graphics g){
        if (this.storyTextCounter == this.textPragraphs.size()){
            return true;
        }
        // TENTO RIADOK DISPLAJUJE BLACK SCREEN STORY TEXT JE STALE DIVNY ALE NEPADA TO
        System.out.println(this.textPragraphs.get(this.storyTextCounter));
        this.pane.setText(this.textPragraphs.get(this.storyTextCounter));
        // g.drawImage(this.storyText.get(this.storyTextCounter), this.x+Game.SUBWINDOW_BONUS_SIZE, this.y+Game.SUBWINDOW_BONUS_SIZE, null);
        this.storyTextCounter += 1;

        if (this.storyTextCounter + 1 == this.textPragraphs.size()){
            return true;
        }
        return false;
    }

    public void drawCurrent(Graphics g) {
        System.out.println(this.textPragraphs.get(this.storyTextCounter));
        this.pane.setText(this.textPragraphs.get(this.storyTextCounter));

    }
    public void drawStoryScreen(Graphics g) {
        g.drawImage(this.introImage, 0, 0, Game.WIDTH, Game.HEIGHT, null);
        this.sb = new SubWindow();
        this.x = 20;
        this.y = Game.HEIGHT/2 - 50;
        this.width = Game.WIDTH - 2*x - 15;
        this.height = Game.HEIGHT/2 - 50 - 15;
        this.sb.drawSubWindow(g, x, y,  width, height);
        this.sb.add(this.pane);
    }

    public int getCounter() {
        return this.storyTextCounter;
    }
}
