package com.savioroflogic.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/** Helper class to convert string into image, 
 * that can be used as sprite for proposition items. */
public class TextToGraphics {
    
    private int width;
    private int height;
    private Font font;

    public TextToGraphics(String fontName, int size){
        this.font = new Font(fontName, Font.PLAIN, size);
    }

    /** Creates an temporary BufferedImage to get the dimenisons of the text,
     * and afterwards creates the final image with the correct size.
     */
    public BufferedImage convert(String text) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setFont(this.font);
        FontMetrics fm = g2d.getFontMetrics();
        this.width = fm.stringWidth(text);
        this.height = fm.getHeight();
        g2d.dispose();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);   
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();
        return img;
    }

    /** Same as convert, but adds a dash at the beginning of the text. */
    public BufferedImage convertWithDash(String text) {
        text = "- " + text;
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setFont(this.font);
        FontMetrics fm = g2d.getFontMetrics();
        this.width = fm.stringWidth(text);
        this.height = fm.getHeight();
        g2d.dispose();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);   
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, 0, fm.getAscent());
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
