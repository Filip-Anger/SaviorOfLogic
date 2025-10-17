import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


public class TextToGraphics {
    
    private int width;
    private int height;
    final private int subWindowAdj = 30;
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
        this.width = fm.stringWidth(text)+subWindowAdj;
        this.height = fm.getHeight()+subWindowAdj;
        g2d.dispose();

        // Create the final image with calculated dimensions
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the outline (black)
        
        

        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, subWindowAdj/2, fm.getAscent()+15);

        g2d.dispose();
        return img;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}