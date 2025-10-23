import java.awt.Graphics;

public class ProofLine {
    private Item proofItem;
    private int CorrectID;
    private boolean isCorrect;
    public ProofLine(int correctID){

        this.CorrectID = correctID;
        this.proofItem = new PropItem("...", -1, "...", -1, -1);
    }

    public void draw(Graphics g, int x, int y){
        if (this.proofItem != null){
            this.proofItem.setPos(x, y);
            this.proofItem.drawInInv(g, x, y);
        }
    }

    public void setProofSting(String s){
        this.proofItem.setSprite(s);
    }
    public void setProofItem(Item item){
        this.proofItem = item;
        if (item.getID() == this.CorrectID){
            this.isCorrect = true;
        }else{
            this.isCorrect = false;
        }
    }
        
    public boolean isCorrect(){
        return this.isCorrect;
    }
    public int getWidth(){
        if (this.proofItem != null){
            return this.proofItem.getInvWidth();
        }
        return 0;
    }
    public int getHeight(){
        if (this.proofItem != null){
            return this.proofItem.getInvHeight();
        }
        return 0;
    }
    public int getCorrectID(){
        return this.CorrectID;
    }
    public Item getItem(){
        return this.proofItem;
    }

}
