package com.savioroflogic.items;

import com.savioroflogic.core.Game;

public class ActionItem extends Item {
    public ActionItem(String type, int xCord, int yCord, int action) {
        /**
         * Small helper that turns tile number into a simple name.
         * 105/106 -> Lever, 22 -> Chest, 118 -> Mimic.
         * Stores where the tile is in map coords too.
         */
        super("ActionTile", xCord * yCord , "undefined", xCord * Game.TILE_SIZE, yCord * Game.TILE_SIZE);
        String actionS = "not found";
        if (action == 105 || action == 106) {
            actionS = "Lever";
        } else if (action == 22) {
            actionS = "Chest";
        } else if (action == 118) {
            actionS = "Mimic";
        }
        this.setContent(actionS);
        this.xCord = xCord;
        this.yCord = yCord;
    }
}
