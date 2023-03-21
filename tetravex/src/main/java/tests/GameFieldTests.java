package tests;

import org.junit.jupiter.api.Test;
import tetravex.core.GameField;
import tetravex.core.StoreField;


import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GameFieldTests {
    private Random randomGenerator = new Random();
    private int matrixsize;

    private GameField gameField;

    public GameFieldTests(){
        matrixsize = randomGenerator.nextInt(10) + 5;
        gameField = new GameField(matrixsize);
        gameField.generate(matrixsize);
    }

    @Test
    public void isNotSolvedRightFromStart(){
        assertTrue(!gameField.checkSolution());
    }

    @Test
    public void solvedTetravex(){
        StoreField storeField =new StoreField(matrixsize);
        storeField.generate(matrixsize);
        gameField.setTiles(storeField.getTiles());
        assertTrue(gameField.checkSolution());
    }


}
