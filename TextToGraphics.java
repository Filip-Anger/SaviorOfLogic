import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


public class TextToGraphics {
    
    private int width;
    private int height;

    SubWindow sw;
    private Font font;
    public TextToGraphics(String fontName, int size){
        this.font = new Font(fontName, Font.PLAIN, size);
        this.sw = new SubWindow();
    }
    public BufferedImage convert(String text) {
        // Create a temporary image to calculate dimensions
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setFont(this.font);
        FontMetrics fm = g2d.getFontMetrics();
        this.width = fm.stringWidth(text)+30;
        this.height = fm.getHeight()+30;
        g2d.dispose();

        
        // Create the final image with calculated dimensions
        img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the outline (black)
        this.sw.drawSubWindow(g2d, 0, 0, this.width, this.height);
        

        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
         // Adjust for thicker outline
        // Draw the text multiple times to create an outline effect
        /* 
        g2d.drawString(text, outlineThickness+5, fm.getAscent() + outlineThickness);
        g2d.drawString(text, outlineThickness+5, fm.getAscent() - outlineThickness);
        g2d.drawString(text, outlineThickness+5, fm.getAscent());
        g2d.drawString(text, outlineThickness + 6, fm.getAscent());
        g2d.drawString(text, outlineThickness +4, fm.getAscent());
        */

        // Draw the main text (white)
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, 15, fm.getAscent()+15);

        g2d.dispose();
        return img;
    }
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
}