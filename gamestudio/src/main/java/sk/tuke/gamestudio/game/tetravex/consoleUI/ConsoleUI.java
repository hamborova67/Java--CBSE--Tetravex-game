package sk.tuke.gamestudio.game.tetravex.consoleUI;
import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.tetravex.core.*;
import sk.tuke.gamestudio.pom.ScoreServiceJDBC;
import sk.tuke.gamestudio.service.*;


import java.util.Date;
import java.util.Scanner;

public class ConsoleUI {
    private StoreField source;
    private String nickname;
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



    private ScoreService scoreService;

    private  RatingService ratingService;

    private CommentService commentService;
    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
    @Autowired
    public void setScoreService(ScoreService scoreService) {
        this.scoreService = scoreService;
    }
    @Autowired
    public void setRatingService(RatingService ratingService) {
        this.ratingService = ratingService;
    }


    public ConsoleUI(int matrixsize){
        this.matrixsize = matrixsize;
    }

    public void newGame(int size){
        if(size<2){
            return;
        }
        source = new StoreField(size);
        source.generate(size);
        source.getShuffledTiles();
        destination = new GameField(size);
        destination.generate(size);
        matrixsize = size;
        this.play();
    }
    public void switchTiles(int[] move){
        Tile pomTile;
        this.move =move;
        if(move ==  null ){
            System.out.println("Incorrect move.");
            return;
        }
        if (source == null || destination==null) {
            return;
        }
        pomTile =   source.getTile(move[0],move[1]);
        source.setTile(move[0],move[1],destination.getTile(move[2],move[3]));
        destination.setTile(move[2],move[3],pomTile);
    }

    public void start(){
        System.out.println("What is your nickname?\nNickname:");
        Scanner scanner =new Scanner(System.in);
        this.nickname = scanner.nextLine();
        System.out.println("\nWelcome in game tetravex!");
        gameMenu();
    }
     public void gameMenu(){
        Scanner scanner;
        System.out.println("\nChoose one number of the options in the menu:\n" +
                "1 - PLAY GAME\n" +
                "2 - RATE THE GAME\n" +
                "3 - LEAVE A COMMENT\n" +
                "4 - SHOW LEADERBOARD\n" +
                "5 - SHOW COMMENTS\n" +
                "6 - EXIT GAME"
        );
        scanner = new Scanner(System.in);
        int menu_input=0;
        boolean mistake = false;
        try {
             menu_input = scanner.nextInt();
        }catch (Exception e){
            System.out.println("*MUST BE NUMBER*");
            mistake=true;
        }

         while(!(menu_input>0 && menu_input<=6) || mistake) {
             System.out.println("INVALID NUMBER. Let's try it again: ");
             try{
                 scanner  =  new Scanner(System.in);
                 menu_input = scanner.nextInt();
                 mistake =false;
             }catch (Exception e){
                 System.out.println("This is not correct move.");
                 mistake=true;
             }
         }


         if(menu_input==1){
             newGame(matrixsize);
         }
         if(menu_input==2){
             rateGame();
         }
         if(menu_input==3){
             leaveComment();
         }
         if(menu_input==4){
             printScores();
         }
         if(menu_input==5){
            printComments();
         }
         if(menu_input==6){
             System.out.println("Bye Bye! See you nextime!");
         }
     }

     public void rateGame(){
        Scanner scanner;
        boolean mistake= false;
        System.out.println("How would you rate tetravex game? (1 - 5 stars, where 5 is the BEST)\nRate game:");
        int rating = 0;
        while(!(rating>0 && rating<=5) || mistake) {
             try{
                 scanner  =  new Scanner(System.in);
                 rating=scanner.nextInt();
                 if(!(rating>0 && rating<=5)){
                     System.out.println("Incorrect input. Rate game:");
                 }
                 mistake =false;
             }catch (Exception e){
                 System.out.println("You can only imput a numbers.");
                 mistake=true;
             }
         }

         Rating rating_to_table = new Rating("tetravex",nickname,rating, new Date());

         ratingService.setRating(rating_to_table);

         System.out.println("THANK YOU FOR RATING THIS GAME!!! \nAverage rating is: "+ratingService.getAverageRating("tetravex"));
         gameMenu();
     }

     public void leaveComment(){
         Scanner scanner;
         System.out.println("LEAVE A  COMMENT: ");
         scanner  =  new Scanner(System.in);
         String comment = scanner.nextLine();

         Comment comment_to_table = new Comment("tetravex",nickname,comment,new Date());
         commentService.addComment(comment_to_table);
         System.out.println("THANK YOU FOR COMMENTING THIS GAME!!!");
         gameMenu();
     }


     public void play(){
        int input;
        int score = 100*matrixsize;
        Scanner scanner  =  new Scanner(System.in);
        System.out.println("Hello!\nWelcome in game tetravex. Point of this game is that you have to match edges of the tiles.\n" +
                "First field is your storage. Second field is your game board. Enjoy. :)\n");
        while (!destination.checkSolution() && destination.getGameFieldState()== GameFieldState.PLAYING){
            drawFields();
            move = new int[4];
            boolean mistake = false;

            System.out.println("What is your next move? Example: 1 2 3 4 (row,column of store field,row,column of game field)\nMove: ");
            input=0;
            for (int input_moves=0;input_moves<4;input_moves++){

                try{
                    input = scanner.nextInt();

                }catch (Exception e){
                    System.out.println("Try again from the start. You can use only numbers.");
                    mistake=true;
                }

                while(!(input>0 && input<=matrixsize) || mistake) {
                    System.out.println("Invalid input. Try again (input "+(input_moves+1)+". number): ");
                    try{
                        scanner  =  new Scanner(System.in);
                        input = scanner.nextInt();
                        mistake =false;
                    }catch (Exception e){
                        System.out.println("This is not correct move.");
                        mistake=true;
                    }
                }
                mistake=false;
                move[input_moves]=(input-1);
            }
            score=score-(2*matrixsize);
            if(score<0){
                score=0;
            }
            switchTiles(move);
        }
        drawFields();


        if(destination.getGameFieldState()== GameFieldState.SOLVED){
            System.out.println("Congratulation! You are a winner.");
        }
        if(destination.getGameFieldState()==GameFieldState.FAILED){
            System.out.println("Sorry. You loose. Maybe next time.");
            score=0;
        }

        Score score_to_table = new Score("tetravex",nickname,score,new Date());

        scoreService.addScore(score_to_table);
        gameMenu();
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
            case 8: return BLUE;
            case 1: return WHITE;
            case 7: return WHITE;
            case 2: return GREEN;
            case 3: return CYAN;
            case 4: return RED;
            case 9: return RED;
            case 5: return YELLOW;
            case 6: return PURPLE;
            default: return ANSI_RESET;
        }

    }

    public void printScores(){

        System.out.println();
        var scores  = scoreService.getTopScores("tetravex");
        for (Score score : scores) {
            System.out.println(score.getPlayer() + " " + score.getPoints() + " " + score.getPlayedOn());
        }
        gameMenu();
    }

    public void printComments(){
        System.out.println();

        var comments = commentService.getComments("tetravex");
        for (Comment comment : comments) {
            System.out.println(comment.getPlayer() + " " + comment.getComment() + " " + comment.getCommentedOn());
        }
        gameMenu();
    }
}
