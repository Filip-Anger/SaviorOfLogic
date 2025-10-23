public class MutliTile {
    String name;
    int start;
    int length;
    
    public MutliTile(String name, int start, int length) {
        this.name = name;
        this.start = start;
        this.length = length;
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
}
