import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class TextToGraphics {
    
    private int width;
    private int height;
    private Font font;
    public TextToGraphics(String fontName, int size){
        this.font = new Font(fontName, Font.PLAIN, size);
    }
    public BufferedImage convert(String text) {
        // Create a temporary image to calculate dimensions
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setFont(this.font);
        FontMetrics fm = g2d.getFontMetrics();
        this.width = fm.stringWidth(text);
        this.height = fm.getHeight();
        g2d.dispose();

        // Create the final image with calculated dimensions
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);   
        
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, 0, fm.getAscent());

        g2d.dispose();
        return img;
    }

    public BufferedImage convertWithDash(String text) {
        // Create a temporary image to calculate dimensions
        text = "- " + text;
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setFont(this.font);
        FontMetrics fm = g2d.getFontMetrics();
        this.width = fm.stringWidth(text);
        this.height = fm.getHeight();
        g2d.dispose();

        // Create the final image with calculated dimensions
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);   
        
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, 0, fm.getAscent());

        g2d.dispose();
        return img;
    }

    public ArrayList<BufferedImage> convertAndSplit(String[] words, int width, int height){
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setFont(this.font);
        FontMetrics fm = g2d.getFontMetrics();
        ArrayList<String> linesList = new ArrayList<>();
        this.height = fm.getHeight();
        int CurrWidth = this.height;
        // TOTO JE TOTALNE CURSED LOOP 
        // FUJ
        String paragraph = "";
        for (int i = 0; i < words.length - 1; i ++){
            paragraph += words[i];
            if (fm.stringWidth(paragraph + " " + words[i + 1]) > width){ // Next word would make line too long
                linesList.add(paragraph); // Do add to list and reset temporary paragraph
                paragraph = "";
            } else { // Add space so its ready for next
                paragraph += " ";
            }
        }
        paragraph = paragraph + " " + words[words.length - 1];
        linesList.add(paragraph);

            // } else {
            //     if (CurrWidth + this.height + 10 <= width){
            //         System.out.println(paragraph);
            //         linesList.add(paragraph);
            //         paragraph = nextWord;
            //         CurrWidth += this.height + 10;
        int screenLines= 4;
        int index;
        int parags = linesList.size() % screenLines == 0 ? linesList.size() / screenLines : linesList.size() / screenLines + 1;
        for (int i = 0; i < parags; i++) {
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = img.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);   
        
            g2d.setFont(font);
            g2d.setColor(Color.WHITE);
            int y = fm.getAscent();
            for (int j = 0; j < 4; j++) {
                index = i * screenLines + j;
                if (index >= linesList.size()) {
                    break;
                }
                System.out.println(linesList.get(index));
                g2d.drawString(linesList.get(index), 0, y);
                y += y+10;
            }
            images.add(img);
            g2d.dispose();
        }       
        return images;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}