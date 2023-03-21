package tests;
import org.junit.jupiter.api.Test;
import tetravex.consoleUI.ConsoleUI;
import tetravex.core.GameField;
import tetravex.core.StoreField;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
public class StoreFieldTests {
    private Random randomGenerator = new Random();
    private StoreField field;
    private int matrixsize;

    public StoreFieldTests() {
        matrixsize = randomGenerator.nextInt(10) + 5;
        field = new StoreField(matrixsize);
        field.generate(matrixsize);
    }
    @Test
    public void solvableGenerationOfField(){
        GameField gameField = new GameField(matrixsize);
        gameField.setTiles(field.getTiles());
        assertTrue(gameField.checkSolution());
    }
    @Test
    public void minimumTiles(){
        matrixsize = randomGenerator.nextInt(0) + 5;
        assertTrue((matrixsize<2 && field!=null) || (matrixsize>=2 && field!=null));
    }

    @Test
    public void checkShuffledTiles(){
        field.getShuffledTiles();
        GameField gameField = new GameField(matrixsize);
        for (int row=0;row<matrixsize;row++){
            for(int col=0;col<matrixsize;col++){
                gameField.setTile(row,col,field.getTile(row,col));
            }
        }
        assertTrue(!gameField.checkSolution());
    }
    @Test
    private void CheckTilesSwitch(){
        GameField gameField = new GameField(matrixsize);
        ConsoleUI consoleUI = new ConsoleUI(matrixsize);
        int[] move={0,0,0,0};
        for (int i=0;i<4;i++){
            int tile_position = randomGenerator.nextInt(1) + matrixsize ;
            move[i] = tile_position;
        }

        consoleUI.switchTiles(move);

    }
}
