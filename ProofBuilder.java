import java.io.FileInputStream;
import java.io.InputStream;
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
    //private int[]
    

    public ProofBuilder(){
        try {
        FileInputStream fis = new FileInputStream("ProofBuilderInstruct.txt");
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        int ch;
        while((ch = isr.read()) != -1 ){
            tempInputString += (char)ch;
        }
        isr.close();
        } catch (Exception e) {
            System.out.println("Error reading ProofBuilderInstruct.txt");
        }
        
        this.inputStrings = tempInputString.split("\\r?\\n");
        this.lineCount = this.inputStrings.length;

        this.proofLines = new ProofLine[this.lineCount];
        //this.finalProof = finalProof;
        for (int i = 0; i < proofLines.length; i++){
            proofLines[i] = new ProofLine(i);
        }
        proofLines[lineCount-1].setProofSting(this.inputStrings[lineCount-1]);
        this.rowHeight = proofLines[0].getHeight()+20;

    }
    
    
    

    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        int y = 55;
        int i = 0;
        for (int line = 0; line <  lineCount; line++) {
            
            int x = Game.WIDTH/2+(Game.MAP_RESOLTION*Game.SCALE)+35;
            int width = proofLines[i].getWidth();
            int height = proofLines[i].getHeight();
            Color c = Color.WHITE;
            int flagOffset = 0;
            for (String s : this.inputStrings[line].split("")) {
                //System.out.println(c);
                if (s.equals("F")){
                    
                    g2.setStroke(new BasicStroke(1));
                    g2.setColor(c);
                    g2.drawRect(x, y, width+15, height+5);
                    x += 20;
                    flagOffset += 1;
                    //y += 10;
                }
                if (s.equals("|")){
                    g2.setStroke(new BasicStroke(1));
                    g2.setColor(c);
                    g2.drawLine(x, y-20, x, y+height+5);
                    x += 20;
                    //y += 10;
                    
                }
            }
            //y += flagOffset*10;
            proofLines[i].draw(g2, x, y+15);
            y += this.rowHeight;
            i++;
        }
    }
    public void changeProofLine(int line, Item item){
        proofLines[line].setProofItem(item);
    }

    public int getRowHeight(){
        return this.rowHeight;
    }
    public int getLineCount(){
        return this.lineCount;
    }
}
