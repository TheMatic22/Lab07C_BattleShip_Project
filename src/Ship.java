public class Ship {

    private final int size;
    private int hitCount;

    public Ship(int size) {
        this.size = size;
        this.hitCount = 0;
    }

    public int getSize() {
        return size;
    }

    public void registerHit() {
        hitCount++;
    }

    public boolean isSunk() {
        return hitCount >= size;
    }
}