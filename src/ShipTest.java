import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ShipTest {

    @Test
    void newShipHasCorrectSize() {
        Ship ship = new Ship(4);
        assertEquals(4, ship.getSize());
    }

    @Test
    void newShipIsNotSunk() {
        Ship ship = new Ship(3);
        assertFalse(ship.isSunk());
    }

    @Test
    void shipIsNotSunkUntilEveryCellIsHit() {
        Ship ship = new Ship(2);
        ship.registerHit();
        assertFalse(ship.isSunk());
    }

    @Test
    void shipIsSunkAfterEnoughHits() {
        Ship ship = new Ship(2);
        ship.registerHit();
        ship.registerHit();
        assertTrue(ship.isSunk());
    }
}