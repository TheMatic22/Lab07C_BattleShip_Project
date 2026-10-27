import java.util.ArrayList;
import  java.util.List;
import java.util.Random;

public class Board {
    public static final int SIZE = 10;
    private static final int[] SHIP_SIZES = {5,4,3,3,2};

    private final CellState[][] cellStates = new CellState[SIZE][SIZE];
    private final Ship[][] shipGrid = new Ship[SIZE][SIZE];
    private final List<Ship> ships = new ArrayList<>();
    private final Random rand = new Random();

    public Board() {
        reset();
    }

    public void reset() {
        ships.clear();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cellStates[row][col] = CellState.BLANK;
                shipGrid[row][col] = null;
            }
        }
        placeShips();
    }
    private boolean canPlace(int row, int col, int length, boolean horizontal) {
        for (int i = 0; i < length; i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;

            if (r >= SIZE || c >= SIZE) {
                return false;
            }
            if (shipGrid[r][c] != null) {
                return false;
            }
        }
        return true;
    }
    private List<int[]> findStartingSpots(int length, boolean horizontal) {
        List<int[]> spots = new ArrayList<>();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (canPlace(row, col, length, horizontal)) {
                    spots.add(new int[] {row, col});
                }
            }
        }
        return spots;
    }
    private void placeShip(Ship ship) {
        int length = ship.getSize();
        boolean horizontal = rand.nextBoolean();

        List<int[]> spots = findStartingSpots(length, horizontal);
        if (spots.isEmpty()) {
            horizontal = !horizontal;
            spots = findStartingSpots(length, horizontal);
        }

        int[] spot = spots.get(rand.nextInt(spots.size()));
        int row = spot[0];
        int col = spot[1];

        for (int i = 0; i < length; i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;
            shipGrid[r][c] = ship;
        }
        ships.add(ship);
    }
    public void placeShips(){
        for (int length : SHIP_SIZES) {
            placeShip(new Ship(length));
        }
    }
    public CellState fireAt(int row, int col) {
        if (cellStates[row][col] != CellState.BLANK) {
            return cellStates[row][col];
        }

        Ship ship = shipGrid[row][col];
        if (ship == null) {
            cellStates[row][col] = CellState.MISS;
        } else {
            ship.registerHit();
            cellStates[row][col] = CellState.HIT;
        }
        return cellStates[row][col];
    }

    public CellState getCellState(int row, int col) {
        return cellStates[row][col];
    }

    public Ship getShipAt(int row, int col) {
        return shipGrid[row][col];
    }

    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }
}

