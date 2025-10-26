package com.savioroflogic.util;

/**Helper class for everything with X, Y cordinates */
public class Pair {
    private int x;
    private int y;
    
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }
    public int y() {
        return this.y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void increment(int iX, int iY) {
        this.x += iX;
        this.y += iY;
    }
    public void incrementX (int iX) {
        this.x += iX;
    }
    public void incrementY (int iY) {
        this.y += iY;
    }
    public void multyPercentage(int percentage) {
        this.x = this.x * percentage / 100;
        this.y = this.y * percentage / 100;
    }
    public Pair giveNew() {
        return new Pair(this.x, this.y);
    }

    public Pair subtractAndGive (Pair other) {
        return new Pair(this.x() - other.x(), this.y() - other.y());
    }
    public Pair AddAndGive (Pair other) {
        return new Pair(this.x() + other.x(), this.y() + other.y());
    }
}
