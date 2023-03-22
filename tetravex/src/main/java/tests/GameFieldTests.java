package tests;

import org.junit.jupiter.api.Test;
import tetravex.core.GameField;
import tetravex.core.GameFieldState;
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
        assertTrue(!gameField.checkSolution() && gameField.getGameFieldState()== GameFieldState.PLAYING);
    }

    @Test
    public void solvedTetravex(){
        StoreField storeField =new StoreField(matrixsize);
        storeField.generate(matrixsize);
        gameField.setTiles(storeField.getTiles());
        assertTrue(gameField.checkSolution());
    }
    @Test
    public void failedTetravex(){
        StoreField storeField =new StoreField(matrixsize);
        storeField.generate(matrixsize);
        storeField.getShuffledTiles();
        gameField.setTiles(storeField.getTiles());
        assertTrue(!gameField.checkSolution());
    }
    @Test
    public void solvedTetravexState(){
        StoreField storeField =new StoreField(matrixsize);
        storeField.generate(matrixsize);
        gameField.setTiles(storeField.getTiles());
        gameField.checkSolution();
        assertTrue(gameField.getGameFieldState()==GameFieldState.SOLVED);
    }
    @Test
    public void failedTetravexState(){
        StoreField storeField =new StoreField(matrixsize);
        storeField.generate(matrixsize);
        storeField.getShuffledTiles();
        gameField.setTiles(storeField.getTiles());
        gameField.checkSolution();
        assertTrue(gameField.getGameFieldState()==GameFieldState.FAILED);
    }



}
