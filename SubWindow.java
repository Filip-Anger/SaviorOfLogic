import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

public class SubWindow extends JComponent {
    public void drawSubWindow(Graphics g, int x, int y, int width, int height){
        Graphics2D g2 = (Graphics2D) g;
        Color c = new Color(0,0,0,220);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+10, y+10, width-20, height-20, 35, 35);
    }
    public void drawSubWindowXY(Graphics g, int x, int y, int endX, int endY){//end points are also coordinates not width and height
        Graphics2D g2 = (Graphics2D) g;
        Color c = new Color(0,0,0,220);
        g2.setColor(c);
        g2.fillRoundRect(x, y, endX-x, endY-y, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+10, y+10, endX-x-20, endY-y-20, 35, 35);
    }
}
