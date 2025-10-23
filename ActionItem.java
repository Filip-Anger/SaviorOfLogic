public class ActionItem extends Item {
    public ActionItem(String type, int xCord, int yCord, int action) {
        String actionS = "not found";
        if (action == 105 || action == 106) {
            actionS = "Lever";
        } else if (action == 22) {
            actionS = "Chest";
        }
        super("ActionTile", xCord * yCord , actionS, xCord * Game.TILE_SIZE, yCord * Game.TILE_SIZE);
        System.out.println(xCord * Game.TILE_SIZE + " " + yCord * Game.TILE_SIZE);
        this.xCord = xCord;
        this.yCord = yCord;
    }
}
