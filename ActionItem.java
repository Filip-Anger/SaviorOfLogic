public class ActionItem extends Item {
    public ActionItem(String type, int xCord, int yCord, int action) {
        super("ActionTile", xCord * yCord , "undefined", xCord * Game.TILE_SIZE, yCord * Game.TILE_SIZE);
        String actionS = "not found";
        if (action == 105 || action == 106) {
            actionS = "Lever";
        } else if (action == 22) {
            actionS = "Chest";
        }
        this.setContent(actionS);
        System.out.println(xCord * Game.TILE_SIZE + " " + yCord * Game.TILE_SIZE);
        this.xCord = xCord;
        this.yCord = yCord;
    }
}
