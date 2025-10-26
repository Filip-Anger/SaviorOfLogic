package com.savioroflogic.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import com.savioroflogic.entity.PlayerSprite;

/**Class that handles drawing the outro state of the game. 
 * The rendered text is loaded from txt file in constructor*/
public class Outro extends JTextPane {

    Image OutroImage;
    int storyTextCounter = 0;
    int x;
    int y;
    int width;
    int height;
    Scanner sc;
    ArrayList<String> textPragraphs;
    JTextPane pane;
    SubWindow sb;
    private JTextArea textArea;

    public Outro() {
        try{
            this.OutroImage = ImageIO.read(new File("com\\Static\\Intro\\Baas.jpg"));
            this.sc = new Scanner(new File("com\\Static\\Intro\\OutroStoryText.txt"));
        } catch (IOException e){
            e.getStackTrace();
        }
        this.textPragraphs = new ArrayList<>();
        String word;
        String paragraph = "";
        while (sc.hasNext()) {
            word = sc.next();
            if (word.equals("<linebreak>")) {
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
        this.textArea = new JTextArea();
        this.textArea.setEditable(false);
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        this.textArea.setOpaque(false);     
        //this.textArea.setFocusable(false);  
        this.textArea.setBorder(null);      
        this.textArea.setForeground(Color.WHITE);
        this.textArea.setFont(new Font("Arial Unicode MS", Font.PLAIN, 30));
        this.textArea.setVisible(false);
        

    }
    public JTextArea getOutroStoryTextComp(){
        return this.textArea;
    }

    public Integer getStoryCounter(){
        return this.textPragraphs.size()-1;
    }

    public int getCounter() {
        return this.storyTextCounter;
    }

    public void setOutroStoryText(int i){
        this.textArea.setText(this.textPragraphs.get(i));
    }

    public void hideOutroStoryTextComp(){
        this.textArea.setVisible(false);
    }
    
    
    public void drawEndScreen(Graphics g, PlayerSprite player){
        g.setFont(g.getFont().deriveFont(Font.BOLD,96));
        String text = "You have won!";
        g.setColor(Color.GRAY);
        g.drawString(text, 62, 105);
        g.setColor(Color.WHITE);
        g.drawString(text, 57, 100);
        
        BufferedImage playerImage =  player.getIdleImage();
        int loadScale = 2;
        com.savioroflogic.util.Pair size = new com.savioroflogic.util.Pair(playerImage.getWidth() * loadScale,  playerImage.getHeight() * loadScale);
        g.drawImage(playerImage, (com.savioroflogic.core.Game.WIDTH - size.x()) / 2, (com.savioroflogic.core.Game.HEIGHT - size.y()) / 2, size.x(), size.y(), null);
        g.setFont(g.getFont().deriveFont(Font.BOLD,52));
        text = "Press E to close the game";
        g.setColor(Color.WHITE);
        g.drawString(text, 80, 500);
    }
            

    public void drawOutroStoryScreen(Graphics g) {
        g.drawImage(this.OutroImage, 0, 0, com.savioroflogic.core.Game.WIDTH, com.savioroflogic.core.Game.HEIGHT, null);
        this.sb = new SubWindow();
        this.x = 20;
        this.y = com.savioroflogic.core.Game.HEIGHT/2 - 50;
        this.width = com.savioroflogic.core.Game.WIDTH - 2*x - 15;
        this.height = com.savioroflogic.core.Game.HEIGHT/2 - 50 - 15;
        this.sb.drawSubWindow(g, x, y,  width, height);
        this.textArea.setBounds(x+com.savioroflogic.core.Game.SUBWINDOW_BONUS_SIZE, y+com.savioroflogic.core.Game.SUBWINDOW_BONUS_SIZE, width-2*com.savioroflogic.core.Game.SUBWINDOW_BONUS_SIZE, height-2*com.savioroflogic.core.Game.SUBWINDOW_BONUS_SIZE);
        this.textArea.setVisible(true);
    }
}
