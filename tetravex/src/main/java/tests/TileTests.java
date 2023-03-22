package tests;
import org.junit.jupiter.api.Test;
import tetravex.core.Tile;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TileTests {

    private Tile tile;
    private Random randomGenerator = new Random();

    int[] move = {1,2,3,4};


    public TileTests() {
        for (int i = 0; i < 4; i++) {
            int tile_number = randomGenerator.nextInt(1) + 9;
            move[i] = tile_number;
        }
    }

    @Test
    public void checkNorth() {
        tile = new Tile(move);
        assertTrue(tile.getNorth() == move[0]);
    }

    @Test
    public void checkSouth() {
        tile = new Tile(move);
        assertTrue(tile.getSouth() == move[3]);
    }

    @Test
    public void checkEast() {
        tile = new Tile(move);
        assertTrue(tile.getEast() == move[2]);
    }

    @Test
    public void checkWest() {
        tile = new Tile(move);
        assertTrue(tile.getWest() == move[1]);
    }
}