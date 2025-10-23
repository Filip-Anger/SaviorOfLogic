import java.awt.Graphics;

public class ProofSubmitter {
    private ProofBuilder pb;
    private int x = 1000;
    private int y = 1000;
    private int width = Game.MAP_RESOLUTION*Game.SCALE;
    private int height = Game.MAP_RESOLUTION*Game.SCALE;
    private SubWindow sb;
    private Inventory inventory;
    private int lineCount = 9;

    public ProofSubmitter(Inventory inv){
        this.sb = new SubWindow();
        this.inventory = inv;
        pb = new ProofBuilder();
    }

    public void draw(Graphics g){
        this.sb.drawSubWindowXY(g, Game.WIDTH/2+(Game.MAP_RESOLUTION*Game.SCALE), 25, Game.WIDTH-40, Game.HEIGHT-50);
        this.inventory.draw(g);
        this.pb.draw(g);
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

    public void dropItem(int mouseX, int mouseY){
        Item item = inventory.getDragItem();
        if (item == null){
            return;
        }
        int rowHeight = pb.getRowHeight();
        int lineCount = pb.getLineCount();
        for(int i = 1; i < lineCount+1; i++){
    
            if (i*rowHeight+55 >= mouseY){
                pb.changeProofLine(i-1, item);
                inventory.nullDragItem();
                return;
            }
        }
        inventory.addItem(item);
        inventory.nullDragItem();
    }
    
}
