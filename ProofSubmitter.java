import java.awt.Graphics;

public class ProofSubmitter {
    private int x = 1000;
    private int y = 1000;
    private int width = Game.ORIGINAL_TILE*Game.SCALE;
    private int height = Game.ORIGINAL_TILE*Game.SCALE;
    private SubWindow sb;

    public ProofSubmitter(){
        this.sb = new SubWindow();
    }

    public void draw(Graphics g){
        this.sb.drawSubWindow(g, 25, 25, Game.WIDTH-50, Game.HEIGHT-75);
    }

    public boolean IsNear(int x, int y, int width, int height){
        if(Math.abs((this.x+this.width/2) - (x + width)) <= this.width + width+Game.PROXIMITY_DIS && Math.abs((this.y+this.height/2) - (y + height)) <= this.height + height+Game.PROXIMITY_DIS){
            return true;
        }
        return false;
    }
}
