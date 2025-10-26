import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

public class ProofBuilder {

    private ProofLine[] proofLines;
    private String tempInputString = "";
    private String[] inputStrings;
    private int rowHeight;
    private int lineCount;
    
    public ProofBuilder() {
        try {
        InputStreamReader isr = new InputStreamReader(new FileInputStream("ProofBuilderInstruct.txt"), "UTF-8");
        int ch;
        while ((ch = isr.read()) != -1 ) {
            tempInputString += (char)ch;
        }
        isr.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
        this.inputStrings = tempInputString.split("\\r?\\n");
        this.lineCount = this.inputStrings.length;
        this.proofLines = new ProofLine[this.lineCount];

        for (int i = 0; i < proofLines.length; i++) {
            proofLines[i] = new ProofLine(i);
        }

        proofLines[lineCount-1].setProofSting(this.inputStrings[lineCount-1]);
        this.rowHeight = proofLines[0].getHeight()+20;
    }
    

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int y = 55;
        int i = 0;
        for (int line = 0; line <  lineCount; line++) {
            int x = Game.WIDTH/2+(Game.MAP_RESOLUTION*Game.SCALE) + 35;
            int width = proofLines[i].getWidth();
            int height = proofLines[i].getHeight();
            Color c = Color.WHITE;

            for (String s : this.inputStrings[line].split("")) {
                if (s.equals("F")){
                    g2.setStroke(new BasicStroke(1));
                    g2.setColor(c);
                    g2.drawRect(x, y, width + 15, height + 5);
                    x += 20;
                }
                if (s.equals("|")) {
                    g2.setStroke(new BasicStroke(1));
                    g2.setColor(c);
                    g2.drawLine(x, y - 20, x, y+height + 5);
                    x += 20;                    
                }
            }
            proofLines[i].draw(g2, x, y + 15);
            y += this.rowHeight;
            i++;
        }
    }


    public void changeProofLine(int line, Item item) {
        this.proofLines[line].setProofItem(item);
    }

    public boolean checkProofs() {
        boolean allCorrect = true;
        for (int i = 0; i < this.lineCount - 1; i++){
            ProofLine iLine = this.proofLines[i];
            if (!iLine.isCorrect()) {
                allCorrect = false;
            }
        }
        if (allCorrect) {
            return true;
        } else {
            return false;
        }
    }


    public int getRowHeight() {
        return this.rowHeight;
    }

    public int getLineCount() {
        return this.lineCount;
    }

    public Item getItem(int line) {
        return this.proofLines[line].getItem();
    }
}
