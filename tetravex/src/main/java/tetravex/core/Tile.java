package tetravex.core;


public class Tile {
    private int[] numbers;
    public Tile(){
    }
    public Tile(int[] nums) {
        numbers = nums;
    }

    public int getNorth() {return numbers[0];}
    public int getEast() {return numbers[1];}
    public int getSouth() {return numbers[2];}
    public int getWest() {return numbers[3];}

    public int[] getNumbers() {
        return numbers;
    }
}