public class BattleshipGame {
    private static final int GRID_SIZE = 10;
    private Gameboard playerBoard;
    private Gameboard opponentBoard;
    private boolean placingShips = true;
    private boolean gameOver = false; // Flag to stop game
    private Ship[] ships = {new Ship(5), new Ship(4), new Ship(3), new Ship(2)};
    private int currentShipIndex = 0;
    private boolean horizontal = true; // Default
    private int lastHitX = -1, lastHitY = -1;
    private boolean hardMode = false; // Flag for hm

    private BattleshipUI ui;
    private String playerName;

    public BattleshipGame(BattleshipUI ui) {
        this.ui = ui;
        playerBoard = new Gameboard(GRID_SIZE);
        opponentBoard = new Gameboard(GRID_SIZE);
    }

    public void startGame() {
        if (currentShipIndex < ships.length) {
            ui.showMessage("Place all ships before starting the game.");
        } else {
            placingShips = false;
            Ship[] opponentFleet = {new Ship(5), new Ship(4), new Ship(3), new Ship(2)};
            opponentBoard.placeFleetRandomly(opponentFleet);
            ui.showMessage("Game starts! Fire at the opponent's grid.");
        }
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public void placeShip(int x, int y) {
        if (gameOver) return; // Prevent placing if game over

        Ship currentShip = ships[currentShipIndex];
        if (playerBoard.placeShip(currentShip, x, y, horizontal)) {
            ui.markShipOnBoard(x, y, currentShip.getLength(), horizontal, true);
            currentShipIndex++;
            if (currentShipIndex >= ships.length) {
                placingShips = false;
                startGame(); // Automatically start after placing all ships
            }
        } else {
            ui.showMessage("Invalid position for the ship.");
        }
    }

    public void playerAttack(int x, int y) {
        if (gameOver || placingShips) return; // Stop actions if game is over or still placing ships

        if (opponentBoard.attack(x, y)) {
            ui.markHitOnOpponentBoard(x, y, true);
            if (checkWin(opponentBoard)) {
                ui.showMessage(playerName + " wins!");
                gameOver = true; // Set game over flag
            } else {
                computerTurn();
            }
        } else {
            ui.markHitOnOpponentBoard(x, y, false);
            computerTurn();
        }
    }

    private void computerTurn() {
        if (gameOver) return; // prevent moves after game over

        int x = -1, y = -1;

        if (lastHitX != -1 && lastHitY != -1) {
            // Shoot neighbor field if in grid
            if (lastHitX + 1 < GRID_SIZE && !playerBoard.isHit(lastHitX + 1, lastHitY)) {
                x = lastHitX + 1;
                y = lastHitY;
            } else if (lastHitX - 1 >= 0 && !playerBoard.isHit(lastHitX - 1, lastHitY)) {
                x = lastHitX - 1;
                y = lastHitY;
            } else if (lastHitY + 1 < GRID_SIZE && !playerBoard.isHit(lastHitX, lastHitY + 1)) {
                x = lastHitX;
                y = lastHitY + 1;
            } else if (lastHitY - 1 >= 0 && !playerBoard.isHit(lastHitX, lastHitY - 1)) {
                x = lastHitX;
                y = lastHitY - 1;

                // logic for hard mode
            } else if (hardMode) {
                if (lastHitX + 1 < GRID_SIZE && lastHitY + 1 < GRID_SIZE && !playerBoard.isHit(lastHitX + 1, lastHitY + 1)) {
                    x = lastHitX + 1;
                    y = lastHitY + 1;
                } else if (lastHitX - 1 >= 0 && lastHitY - 1 >= 0 && !playerBoard.isHit(lastHitX - 1, lastHitY - 1)) {
                    x = lastHitX - 1;
                    y = lastHitY - 1;
                    // Reset
                } else {
                    lastHitX = -1;
                    lastHitY = -1;
                }
            } else {
                lastHitX = -1;
                lastHitY = -1;
            }
        }

        // Random shot when nothing found or if lastHitX/Y was reset
        if (x == -1 || y == -1) {
            do {
                x = (int) (Math.random() * GRID_SIZE);
                y = (int) (Math.random() * GRID_SIZE);
            } while (playerBoard.isHit(x, y));
        }

        boolean hit = playerBoard.attack(x, y);
        if (hit) {
            lastHitX = x;
            lastHitY = y;
            ui.markHitOnPlayerBoard(x, y, true);
            if (checkWin(playerBoard)) {
                ui.showMessage("Computer wins!");
                gameOver = true; // Set the game over flag
            }
        } else {
            lastHitX = -1;
            lastHitY = -1;
            ui.markHitOnPlayerBoard(x, y, false);
        }
    }

    private boolean checkWin(Gameboard board) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Ship ship = board.getShipAt(i, j);
                if (ship != null && !ship.isSunk()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void enableHardMode() {
        hardMode = true;
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }
}
