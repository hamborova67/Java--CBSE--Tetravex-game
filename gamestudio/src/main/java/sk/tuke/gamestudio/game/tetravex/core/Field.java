package sk.tuke.gamestudio.game.tetravex.core;


public abstract class Field {
    private Tile[][] tiles;
    private final int size;

    public Field(int size){
        this.size = size;

    }
    public Tile getTile(int i, int j) {
        return tiles[i][j];
    }
}