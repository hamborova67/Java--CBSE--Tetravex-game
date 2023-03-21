package tetravex.consoleUI;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;
import tetravex.core.Field;
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
        if(size<2){
            return;
        }
        source = new StoreField(size);
        source.generate(size);
        //source.getShuffledTiles();
        destination = new GameField(size);
        destination.generate(size);
        matrixsize = size;
        play();
    }
    public void switchTiles(int[] move){
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
        Scanner scanner  =  new Scanner(System.in);
        System.out.println("Hello!\nWelcome in game tetravex. Point of this game is that you have to match edges of the tiles.\n" +
                "First field is your storage. Second field is your game board. Enjoy. :)\n");
        while (!destination.checkSolution()){
            drawFields();
            move = new int[4];
            boolean chyba = false;

            System.out.println("What is your next move? Example: 1 2 3 4 (row,column of store field,row,column of game field)\nMove: ");
            input=0;
            for (int input_moves=0;input_moves<4;input_moves++){

                try{
                    input = scanner.nextInt();

                }catch (Exception e){
                    System.out.println("Try again from the start. You can use only numbers.");
                    chyba=true;
                }

                while(!(input>0 && input<=matrixsize) || chyba) {
                    System.out.println("Invalid input. Try again (input "+(input_moves+1)+". number): ");
                    try{
                        scanner  =  new Scanner(System.in);
                        input = scanner.nextInt();
                        chyba =false;
                    }catch (Exception e){
                        System.out.println("This is not correct move.");
                        chyba=true;
                    }
                }
                chyba=false;
                move[input_moves]=(input-1);
            }
            switchTiles(move);
        }
        drawFields();
        System.out.println("Congratulation! You are a winner. Do you want to play again? If yes, write y or yes.");
        String string_input = scanner.nextLine();

        if(string_input.equals('y')){
            play();
        }

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

    public void drawCol(int row , Field field, int smer){

        System.out.print("|");
        for(int col = 0;col< matrixsize;col++)
        {
            if(smer!=2){
                System.out.print("  ");
            }

            if(field.getTile(row,col).getNorth()==10) {
                if(smer==2){
                    System.out.print(ANSI_RESET+"x   x|");
                }else {
                    System.out.print(ANSI_RESET + "x  |");
                }

            }else {
                if(smer==1){
                    System.out.print(colorNumber(field.getTile(row,col).getNorth())+field.getTile(row,col).getNorth()+ANSI_RESET+"  |");
                }
                if(smer==3){
                    System.out.print(colorNumber(field.getTile(row, col).getSouth()) + field.getTile(row, col).getSouth() + ANSI_RESET + "  |");
                }
                if(smer==2){
                    System.out.print(colorNumber(field.getTile(row, col).getWest()) + field.getTile(row, col).getWest() + "   ");
                    System.out.print(colorNumber(field.getTile(row, col).getEast()) + field.getTile(row, col).getEast() + ANSI_RESET + "|");
                }
            }

        }

    }

    public void drawFields(){
        printLines();
        for(int row = 0;row< matrixsize;row++)
        {
            for(int smer=1;smer<=3;smer++){
                drawCol(row,source,smer);
                System.out.print("                        ");
                drawCol(row, destination,smer);
                System.out.println();

            }
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

    public void printScore(){
        ScoreServiceJDBC scoreServiceJDBC = new ScoreServiceJDBC();
        var scores  = scoreServiceJDBC.getTopScores("tetravex");
        for (int i=0;i< scores.size();i++){
            var score = scores.get(i);
            System.out.println(score.getPlayer()+" "+score.getPoints()+" "+score.getPlayedOn());
        }
    }
}
