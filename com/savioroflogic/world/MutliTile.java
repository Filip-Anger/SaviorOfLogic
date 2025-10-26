package com.savioroflogic.world;

/**Helper class to group multitile structures */
public class MutliTile {
    String name;
    int start;
    int length;
    boolean collision;
    
    public MutliTile(String name, int start, int length, boolean collision) {
        this.name = name;
        this.start = start;
        this.length = length;
        this.collision = collision;
    }

    public String getName() {
        return this.name;
    }

    public int getStrat() {
        return this.start;
    }

    public int getLength() {
        return this.length;
    }

    public boolean getCollision() {
        return this.collision;
    }
}
