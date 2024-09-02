public class Ship {
    private int length;
    private boolean[] hitLocations;
    private int startX;
    private int startY;
    private boolean horizontal;

    public Ship(int length) {
        this.length = length;
        this.hitLocations = new boolean[length];
    }

    public void setStartCoordinates(int startX, int startY, boolean horizontal) {
        this.startX = startX;
        this.startY = startY;
        this.horizontal = horizontal;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public int getLength() {
        return length;
    }

    public boolean isSunk() {
        for (boolean hit : hitLocations) {
            if (!hit) return false;
        }
        return true;
    }

    public void hit(int position) {
        if (position >= 0 && position < length) {
            hitLocations[position] = true;
        }
    }
}
