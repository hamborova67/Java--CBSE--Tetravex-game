package tetravex.consoleUI;
import tetravex.core.GameField;
import tetravex.core.StoreField;
import tetravex.core.Tile;

import java.util.Scanner;

public class ConsoleUI {
    private StoreField source;
    private GameField destination;
    private int matrixsize;
    private  int[] move;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    public ConsoleUI(){
    }

    public void newGame(int size){
        source = new StoreField(size);
        source.generate(size);
        source.getShuffledTiles();
        destination = new GameField(size);
        destination.generate(size);
        matrixsize = size;
        play();
    }
    public void switchTiles(){
        Tile pomTile;
        if(move ==  null ){
            System.out.println("Incorrect move.");
            return;
        }
        pomTile =   source.getTile(move[0],move[1]);
        source.setTile(move[0],move[1],destination.getTile(move[2],move[3]));
        destination.setTile(move[2],move[3],pomTile);
    }

    void play(){
        int input=0;
        System.out.println("Hello!\nWelcome in game tetravex. Point of this game is that you have to match edges of the tiles.\n" +
                "First field is your storage. Second field is your game board. Enjoy. :)\n");
        while (!destination.checkSolution()){
            drawFields();
            move = new int[4];
            Scanner scanner  =  new Scanner(System.in);
            System.out.println("What is your next move? (row,column of store field,row,column of game field)\nMove: ");

            for (int input_moves=0;input_moves<4;input_moves++){

               go: try{
                    input = scanner.nextInt();

                }catch (Exception e){
                    System.out.println("Invalid input. Try again: ");

                }
                while(!(input>0 && input<=matrixsize)) {
                    System.out.println("Invalid input. Try again: ");
                    try{
                        input = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("Invalid input. Try again: ");
                    }
                }
                move[input_moves]=input;
            }
            switchTiles();
        }
        drawFields();
        System.out.println("Congratulation! You are a winner. Do you want to play again? (y/n)");
        //dorobit
    }

    public void printLines(){
        for(int col = 0; col<matrixsize;col++){
            System.out.print("------");
        }
        System.out.print("                          ");
        for(int col = 0; col<matrixsize;col++){
            System.out.print("------");
        }

        System.out.print("");
        System.out.println();
    }

    public void drawFields(){
        printLines();
        for(int i = 0;i< matrixsize;i++)
        {
            System.out.print("|");
            for(int j = 0;j< matrixsize;j++)
            {
                System.out.print("  ");
                if(source.getTile(i,j).getNorth()==10) {
                    System.out.print(ANSI_RESET + "x  |");
                }else {
                    System.out.print(colorNumber(source.getTile(i,j).getNorth())+source.getTile(i,j).getNorth()+ANSI_RESET+"  |");
                }
            }
            System.out.print("                        |");
            for(int j = 0;j< matrixsize;j++)
            {
                System.out.print("  ");
                if(destination.getTile(i,j).getNorth()==10){
                    System.out.print(ANSI_RESET+"x  |");
                }else {
                    System.out.print(colorNumber(destination.getTile(i,j).getNorth())+destination.getTile(i,j).getNorth()+ANSI_RESET+"  |");
                }
            }
            System.out.println();
            System.out.print("|");

            // FIRST NUMBER
            for(int j = 0;j< matrixsize;j++)
            {
                if(source.getTile(i,j).getEast()==10){
                    System.out.print(ANSI_RESET+"x   x|");
                }else{
                    System.out.print(colorNumber(source.getTile(i, j).getWest()) + source.getTile(i, j).getWest() + "   ");
                    System.out.print(colorNumber(source.getTile(i, j).getEast()) + source.getTile(i, j).getEast() + ANSI_RESET + "|");
                }
            }
            System.out.print("                        |");

            for(int j = 0;j< matrixsize;j++)
            {
                if(destination.getTile(i,j).getEast()==10){
                    System.out.print(ANSI_RESET+"x   x|");
                }else {
                    System.out.print(colorNumber(destination.getTile(i, j).getWest()) + destination.getTile(i, j).getWest() + "   ");
                    System.out.print(colorNumber(destination.getTile(i, j).getEast()) + destination.getTile(i, j).getEast() + ANSI_RESET + "|");
                }
            }
            System.out.println();
            System.out.print("|");

            // second two rows

            for(int j = 0;j< matrixsize;j++)
            {
                System.out.print("  ");
                if(source.getTile(i,j).getSouth()==10) {
                    System.out.print(ANSI_RESET + "x  |");
                }else {
                    System.out.print(colorNumber(source.getTile(i,j).getSouth())+source.getTile(i,j).getSouth()+ANSI_RESET+"  |");
                }
            }
            System.out.print("                        |");
            for(int j = 0;j< matrixsize;j++) {
                System.out.print("  ");
                if(destination.getTile(i,j).getSouth()==10) {
                    System.out.print(ANSI_RESET + "x  |");
                }else {
                    System.out.print(colorNumber(destination.getTile(i, j).getSouth()) + destination.getTile(i, j).getSouth() + ANSI_RESET + "  |");
                }
            }
            System.out.println();
            printLines();
        }
    }



    public String colorNumber(int number){
        switch (number){
            case 0: return BLUE;
            case 1: return WHITE;
            case 2: return GREEN;
            case 3: return CYAN;
            case 4: return RED;
            case 5: return YELLOW;
            case 6: return PURPLE;
            case 7: return WHITE;
            case 8: return BLUE;
            case 9: return RED;
            default: return ANSI_RESET;
        }

    }
}
