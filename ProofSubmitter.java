import java.awt.Graphics;

public class ProofSubmitter {
    private int x = 1000;
    private int y = 1000;
    private int width = Game.MAP_RESOLTION*Game.SCALE;
    private int height = Game.MAP_RESOLTION*Game.SCALE;
    private SubWindow sb;

    public ProofSubmitter(){
        this.sb = new SubWindow();
    }

    public void draw(Graphics g){
        this.sb.drawSubWindowXY(g, Game.WIDTH/2+(Game.MAP_RESOLTION*Game.SCALE), 25, Game.WIDTH-40, Game.HEIGHT-50);
    }

    public boolean IsNear(Pair playerPos, Pair size){
        int submitterWidth = this.width/2; //middle of submitter
        int submitterHeight = this.height/2; //middle of submitter
        int submitterX = this.x + submitterWidth; //middle of submitter
        int submitterY = this.y + submitterHeight; //middle of submitter
        int playerWidth = size.x()/2; //middle of player
        int playerHeight = size.y()/2; //middle of player 
        int playerX = playerPos.x() + playerWidth; //middle of player
        int playerY = playerPos.y() + playerHeight; //middle of player
        if(Math.abs(submitterX - playerX) <= (submitterWidth + playerWidth) && Math.abs(submitterY - playerY) <= (submitterHeight + playerHeight)){ //add proximity distance in case of borders
            return true;
        }
        return false;
    }
}
