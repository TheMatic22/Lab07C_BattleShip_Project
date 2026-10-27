public class BattleshipGame {

    private static final int MAX_STRIKES = 3;
    private final Board board;
    private int missCount;
    private int strikeCount;
    private int totalMiss;
    private int totalHit;

    public BattleshipGame() {
        board = new Board();
        resetCounters();
    }
    public void newGame() {
        board.reset();
        resetCounters();
    }
    public CellState fire(int row, int col) {
        CellState current = board.getCellState(row, col);
        if (current != CellState.BLANK) {
            return current;
        }
        CellState result = board.fireAt(row, col);
        if (result == CellState.HIT) {
            totalHit++;
            missCount = 0;
        } else {
            missCount++;
            totalMiss++;
            if (missCount == 5) {
                strikeCount++;
                missCount = 0;
            }
        }
        return result;
    }
    public boolean isWon(){
        return board.allShipsSunk();
    }
    public boolean isLost(){
        return strikeCount >= MAX_STRIKES;
    }
    public boolean isShipSunkAt(int row, int col) {
        Ship ship = board.getShipAt(row, col);
        return ship != null && ship.isSunk();
    }

    private void resetCounters() {
        missCount = 0;
        strikeCount = 0;
        totalMiss = 0;
        totalHit = 0;
    }
    public int getMissCount()   { return missCount; }
    public int getStrikeCount() { return strikeCount; }
    public int getTotalMiss()   { return totalMiss; }
    public int getTotalHit()    { return totalHit; }
}
