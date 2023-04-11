package sk.tuke.gamestudio.game.tetravex.core;

public class StoreField extends Field{
    private int matrixSize;
    private Tile[][] tiles;
    public StoreField(int size) {
        super(size);
    }
    public void generate(int size){
        matrixSize =size;
        tiles = new Tile[matrixSize][matrixSize];
        for (int row = 0; row < matrixSize; row++) {
            for (int col = 0; col < matrixSize; col++) {
                int[] nums = new int[4];
                // top left
                if (row == 0 && col == 0) {
                    nums[0] = (int)(Math.random()*10);
                    nums[1] = (int)(Math.random()*10);
                    nums[2] = (int)(Math.random()*10);
                    nums[3] = (int)(Math.random()*10);
                    tiles[row][col] = new Tile(nums);
                    // first row
                } else if (row == 0) {
                    nums[0] = (int)(Math.random()*10);
                    nums[1] = (int)(Math.random()*10);
                    nums[2] = (int)(Math.random()*10);
                    nums[3] = tiles[0][col-1].getEast();
                    tiles[row][col] = new Tile(nums);
                    // first column
                } else if (col == 0) {
                    nums[0] = tiles[row-1][0].getSouth();
                    nums[1] = (int)(Math.random()*10);
                    nums[2] = (int)(Math.random()*10);
                    nums[3] = (int)(Math.random()*10);
                    tiles[row][col] = new Tile(nums);
                    // remaining tiles
                } else {
                    nums[0] = tiles[row-1][col].getSouth();
                    nums[1] = (int)(Math.random()*10);
                    nums[2] = (int)(Math.random()*10);
                    nums[3] = tiles[row][col-1].getEast();
                    tiles[row][col] = new Tile(nums);
                }

            }
        }
    }

    public void getShuffledTiles() {
        Tile[][] result =tiles;
        for (int row = 0; row < matrixSize; row++) {
            for (int col = 0; col< matrixSize; col++) {
                int row_random = (int) (Math.random()*matrixSize);
                int col_random = (int) (Math.random()*matrixSize);
                Tile temp = result[row][col];
                result[row][col] = result[row_random][col_random];
                result[row_random][col_random] = temp;
            }
        }
        GameField gameField = new GameField(matrixSize);
        gameField.setTiles(result);
        if(gameField.checkSolution()){
            getShuffledTiles();
        }
        tiles=result;
    }

    @Override
    public Tile getTile(int i, int j) {
        return tiles[i][j];
    }

    public void setTile(int i, int j, Tile tile) {
        tiles[i][j] = tile ;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}