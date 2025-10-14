import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


public class TextToGraphics {
    
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
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();

        // Create the final image with calculated dimensions
        img = new BufferedImage(width+10, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the outline (black)
        Color c = new Color(0,0,0,200);
        g2d.setColor(c);
        g2d.fillRoundRect(0, 0, width+10, height, 20, 20);

        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        int outlineThickness = 1; // Adjust for thicker outline
        // Draw the text multiple times to create an outline effect
        g2d.drawString(text, outlineThickness+5, fm.getAscent() + outlineThickness);
        g2d.drawString(text, outlineThickness+5, fm.getAscent() - outlineThickness);
        g2d.drawString(text, outlineThickness+5, fm.getAscent());
        g2d.drawString(text, outlineThickness + 6, fm.getAscent());
        g2d.drawString(text, outlineThickness +4, fm.getAscent());

        // Draw the main text (white)
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, 5, fm.getAscent());

        g2d.dispose();
        return img;
    }
}