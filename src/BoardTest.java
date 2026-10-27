import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoardTest {

    private int countShipCells(Board board) {
        int count = 0;
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (board.getShipAt(row, col) != null) {
                    count++;
                }
            }
        }
        return count;
    }

    private int[] findCell(Board board, boolean wantShip) {
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                boolean hasShip = board.getShipAt(row, col) != null;
                if (hasShip == wantShip) {
                    return new int[] {row, col};
                }
            }
        }
        return null;
    }

    @Test
    void newBoardShowsAllBlankCells() {
        Board board = new Board();
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                assertEquals(CellState.BLANK, board.getCellState(row, col));
            }
        }
    }

    @Test
    void newBoardHasExactly17ShipCells() {
        Board board = new Board();
        assertEquals(17, countShipCells(board));
    }

    @Test
    void shipCellCountIsAlways17AcrossManyBoards() {
        for (int i = 0; i < 200; i++) {
            Board board = new Board();
            assertEquals(17, countShipCells(board));
        }
    }

    @Test
    void firingAtWaterIsMiss() {
        Board board = new Board();
        int[] water = findCell(board, false);
        assertEquals(CellState.MISS, board.fireAt(water[0], water[1]));
    }

    @Test
    void firingAtShipIsHit() {
        Board board = new Board();
        int[] shipCell = findCell(board, true);
        assertEquals(CellState.HIT, board.fireAt(shipCell[0], shipCell[1]));
    }

    @Test
    void notAllShipsAreSunkAtStart() {
        Board board = new Board();
        assertFalse(board.allShipsSunk());
    }

    @Test
    void allShipsAreSunkAfterFiringAtEveryShipCell() {
        Board board = new Board();
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (board.getShipAt(row, col) != null) {
                    board.fireAt(row, col);
                }
            }
        }
        assertTrue(board.allShipsSunk());
    }
}