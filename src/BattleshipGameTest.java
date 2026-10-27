import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BattleshipGameTest {

    private CellState[][] fireEveryCell(BattleshipGame game) {
        CellState[][] results = new CellState[Board.SIZE][Board.SIZE];
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                results[row][col] = game.fire(row, col);
            }
        }
        return results;
    }

    @Test
    void newGameStartsAtZero() {
        BattleshipGame game = new BattleshipGame();
        assertEquals(0, game.getMissCount());
        assertEquals(0, game.getStrikeCount());
        assertEquals(0, game.getTotalMiss());
        assertEquals(0, game.getTotalHit());
        assertFalse(game.isWon());
        assertFalse(game.isLost());
    }

    @Test
    void firingEveryCellGives17HitsAnd83Misses() {
        BattleshipGame game = new BattleshipGame();
        fireEveryCell(game);
        assertEquals(17, game.getTotalHit());
        assertEquals(83, game.getTotalMiss());
        assertTrue(game.isWon());
    }

    @Test
    void missCountIsZeroAfterEveryHit() {
        BattleshipGame game = new BattleshipGame();
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                CellState result = game.fire(row, col);
                if (result == CellState.HIT) {
                    assertEquals(0, game.getMissCount());
                }
                assertTrue(game.getMissCount() <= 4);
            }
        }
    }

    @Test
    void fiveMissesInARowEarnAStrike() {
        BattleshipGame game = new BattleshipGame();
        int run = 0;
        int expectedStrikes = 0;

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                CellState result = game.fire(row, col);
                if (result == CellState.HIT) {
                    run = 0;
                } else {
                    run++;
                    if (run == 5) {
                        expectedStrikes++;
                        run = 0;
                    }
                }
                assertEquals(expectedStrikes, game.getStrikeCount());
                assertEquals(run, game.getMissCount());
            }
        }
        assertTrue(expectedStrikes >= 1);
    }

    @Test
    void repeatShotDoesNotChangeCounters() {
        BattleshipGame game = new BattleshipGame();
        game.fire(0, 0);
        int miss = game.getMissCount();
        int strike = game.getStrikeCount();
        int totalMiss = game.getTotalMiss();
        int totalHit = game.getTotalHit();

        game.fire(0, 0);

        assertEquals(miss, game.getMissCount());
        assertEquals(strike, game.getStrikeCount());
        assertEquals(totalMiss, game.getTotalMiss());
        assertEquals(totalHit, game.getTotalHit());
    }

    @Test
    void gameIsLostAfterThreeStrikes() {
        BattleshipGame game = new BattleshipGame();
        assertFalse(game.isLost());
        fireEveryCell(game);
        assertTrue(game.isLost());
    }

    @Test
    void shipIsSunkAtEveryHitCellAfterFullScan() {
        BattleshipGame game = new BattleshipGame();
        CellState[][] results = fireEveryCell(game);
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                boolean wasHit = results[row][col] == CellState.HIT;
                assertEquals(wasHit, game.isShipSunkAt(row, col));
            }
        }
    }

    @Test
    void newGameResetsEverything() {
        BattleshipGame game = new BattleshipGame();
        fireEveryCell(game);
        game.newGame();
        assertEquals(0, game.getMissCount());
        assertEquals(0, game.getStrikeCount());
        assertEquals(0, game.getTotalMiss());
        assertEquals(0, game.getTotalHit());
        assertFalse(game.isWon());
        assertFalse(game.isLost());
    }
}