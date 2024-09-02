import java.util.Random;

public class Gameboard {
    private final Ship[][] board;
    private final boolean[][] hits;
    private final int size;

    public Gameboard(int size) {
        this.size = size;
        this.board = new Ship[size][size];
        this.hits = new boolean[size][size];
    }

    public boolean placeShip(Ship ship, int x, int y, boolean horizontal) {
        int length = ship.getLength();

        if (horizontal) {
            if (x + length > size) return false;
        } else {
            if (y + length > size) return false;
        }

        for (int i = 0; i < length; i++) {
            if (horizontal) {
                if (board[x + i][y] != null) return false;
            } else {
                if (board[x][y + i] != null) return false;
            }
        }

        for (int i = 0; i < length; i++) {
            if (horizontal) {
                board[x + i][y] = ship;
            } else {
                board[x][y + i] = ship;
            }
        }
        ship.setStartCoordinates(x, y, horizontal);
        return true;
    }

    public boolean attack(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size) {
            hits[x][y] = true;
            Ship ship = board[x][y];
            if (ship != null) {
                int position = ship.isHorizontal() ? x - ship.getStartX() : y - ship.getStartY();
                ship.hit(position);
                return true;
            }
        }
        return false;
    }

    public boolean isHit(int x, int y) {
        return hits[x][y];
    }

    public Ship getShipAt(int x, int y) {
        return board[x][y];
    }

    public void placeFleetRandomly(Ship[] fleet) {
        Random random = new Random();
        for (Ship ship : fleet) {
            boolean placed = false;
            while (!placed) {
                int x = random.nextInt(size);
                int y = random.nextInt(size);
                boolean horizontal = random.nextBoolean();
                placed = placeShip(ship, x, y, horizontal);
            }
        }
    }
}
