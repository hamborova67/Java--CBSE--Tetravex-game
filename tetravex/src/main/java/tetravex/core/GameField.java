package tetravex.core;

import tests.GameFieldTests;

public class GameField extends Field {
    private Tile[][] tiles;
    private GameFieldState gameFieldState;
    private int size;
    public GameField(int size) {
        super(size);
        this.size =  size;
    }

    public void generate(int size){
        setGameFieldState(GameFieldState.PLAYING);
        tiles = new Tile[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int[] nums = new int[4];
                for (int number=0;number<4;number++){
                    nums[number]=10;
                }
                tiles[row][col] = new Tile(nums);
            }}
    }

    public boolean checkSolution() {

        for(int row = 0; row < size-1;row++){
            for(int column = 0; column < size-1;column++){

                if(getTile(row,column)==null || getTile(row,column).getEast() !=  getTile(row,column+1).getWest() || getTile(row,column).getEast()==10){
                    doubleCheck();
                    return false;
                }
                if(getTile(row,column).getSouth() !=  getTile(row+1,column).getNorth() ||  getTile(row,column).getSouth()==10){
                    doubleCheck();
                    return false;
                }
                if(getTile(size-1,column).getEast()==10){
                    doubleCheck();
                    return false;
                }
            }
            if(getTile(row,size-1).getEast()==10 || getTile(size-1,size-1).getEast()==10){
                doubleCheck();
                return false;
            }
        }



        setGameFieldState(GameFieldState.SOLVED);


        return true;
    }

    public void doubleCheck(){
        int count =0;
        for(int row = 0; row < size;row++){
            for(int column = 0; column < size;column++) {
                if( getTile(row,column)!= null && getTile(row,column).getEast()==10){
                    count++;
                }
            }
        }
        if(count==0){
            setGameFieldState(GameFieldState.FAILED);
        }
    }


    @Override
    public Tile getTile(int i, int j) {
        if(tiles==null){
            return null;
        }
        return tiles[i][j];
    }

    public Tile[][] getTiles() {
        return  tiles;
    }
    public void setTile(int i, int j, Tile tile) {
        if(tile==null || tiles==null){
            return;
        }
        tiles[i][j] = tile ;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public GameFieldState getGameFieldState() {
        return gameFieldState;
    }

    public void setGameFieldState(GameFieldState gameFieldState) {
        this.gameFieldState = gameFieldState;
    }
}