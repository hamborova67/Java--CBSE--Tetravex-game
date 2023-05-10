package sk.tuke.gamestudio.server.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.tetravex.core.*;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;
import java.util.Date;

@Controller
@RequestMapping("/tetravex")
@Scope(WebApplicationContext.SCOPE_SESSION)
//http://localhost:8080/tetravex/new
public class TetravexController {
    @Autowired
    private UserController userController;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;
    private int matrixsize =5;

    private int score =100;
    private StoreField storeField = new StoreField(matrixsize);
    private GameField gameField = new GameField(matrixsize);

    private Integer col1,col2,row1,row2;


    @RequestMapping
   public String tetravex(@RequestParam(required = false) Integer row1, @RequestParam(required = false) Integer col1,@RequestParam(required = false) Integer row2, @RequestParam(required = false) Integer col2, Model model,@RequestParam(required = false) String comment,@RequestParam(required = false) String rating){

        if(userController.isLogged()){
            model.addAttribute("scores",scoreService.getTopScores("tetravex"));
            model.addAttribute("ratings",ratingService.getAverageRating("tetravex"));
            model.addAttribute("comments",commentService.getComments("tetravex"));
            if(comment!=null){
                commentService.addComment(new Comment("tetravex",userController.getLoggedUser().getLogin(),comment,new Date()));
                model.addAttribute("comments",commentService.getComments("tetravex"));
            }
                Integer intRating = null;
            try {
                intRating = Integer.parseInt(rating);

                if(intRating!=null &&( intRating<=5 || intRating>=1) ){
                    System.out.println("\n\nCISLO(rating):"+intRating);
                    ratingService.setRating(new Rating("tetravex",userController.getLoggedUser().getLogin(),intRating,new Date()));
                    model.addAttribute("ratings",ratingService.getAverageRating("tetravex"));
                }
            } catch(NumberFormatException ex) {
                System.out.println("NIE JE TO CISLO(rating)\n\n");
                // Handle the case where the String cannot be parsed as an Integer
                // e.g. show an error message to the user or set a default value
                intRating = 0; // set default value to 0
            }





        }


        if(gameField.checkSolution()==true && gameField.getGameFieldState()==GameFieldState.FAILED){
            return "tetravex";
        }

        Tile pomTile;
        try{
            if (col1!=null && row1!=null && col2==null && row2==null) {
                this.col1 = col1;
                this.row1= row1;
            }

            if (col1==null && row1==null && col2!=null && row2!=null) {
                this.col2 = col2;
                this.row2= row2;
            }

            if (this.col1!=null && this.row1!=null && this.col2!=null && this.row2!=null) {
                pomTile =   storeField.getTile(this.row1,this.col1);
                storeField.setTile(this.row1,this.col1,gameField.getTile(this.row2,this.col2));
                gameField.setTile(this.row2,this.col2,pomTile);
                this.col1=null;
                this.col2=null;
                this.row1=null;
                this.row2=null;
                this.score=this.score-1;
                System.out.println("\nR:"+this.score);
            }
        }catch (Exception e){

        }


       return "tetravex";
   }



   @RequestMapping("/new")
   public String newGame(){
       this.storeField.generate(matrixsize);
       this.gameField.generate(matrixsize);
       this.score = 100;
       return "tetravex";

   }
    public String play(){
        StringBuilder sb = new StringBuilder();
        if(this.storeField.getTiles()==null || this.gameField.getTiles()==null){
            newGame();
        }
        sb.append(drawFields());



        if(gameField.checkSolution()==true){
            sb.append("vyhral si.");
            if(userController.isLogged()){
                scoreService.addScore(new Score("tetravex",userController.getLoggedUser().getLogin(),this.score, new Date()));
                this.score=100;
                newGame();
            }
        }
        if(gameField.getGameFieldState()==GameFieldState.FAILED){
            sb.append("prehral si.");
            if(userController.isLogged()){
                scoreService.addScore(new Score("tetravex",userController.getLoggedUser().getLogin(),this.score, new Date()));
                this.score=100;
                newGame();
            }
        }
        return sb.toString();
    }


    public String drawFields(){
       StringBuilder sb = new StringBuilder();
       sb.append("<div id='content'> ");
       sb.append("<table>\n");
       sb.append("<tr>\n");
       sb.append("<td>\n");
       sb.append("<table border='1'>\n");

       for(int row = 0;row< matrixsize;row++)
       {
           sb.append("<tr>\n");
           for(int col = 0;col< matrixsize;col++)
           {
               sb.append("<td class=\"out\">\n");
               sb.append("<div class=\"source ui-droppable\">");
               sb.append("<div class=\"draggable ui-draggable\" id=\"orig_row0_col0\" style=\"position: relative;\">");

               sb.append("<a href='/tetravex?row1="+row+"&col1="+col+"'>");

               sb.append("<div class=\"up"+this.storeField.getTile(row,col).getNorth()+ " inner\">");
               sb.append("<div class=\"right"+this.storeField.getTile(row,col).getEast()+ " inner\">");
               sb.append("<div class=\"left"+this.storeField.getTile(row,col).getWest()+ " inner\">");
               sb.append("<div class=\"down"+this.storeField.getTile(row,col).getSouth()+ " inner\">");
               sb.append(" </div>");
               sb.append(" </div>");
               sb.append(" </div>");
               sb.append(" </div>");

               sb.append("</a>");

               sb.append(" </div>");
               sb.append(" </div>");
               sb.append("</td>\n");
       }

           sb.append("</tr>\n");
       }

       sb.append("</table>\n");
       sb.append("</td>\n");
       sb.append("</tr>\n");




       sb.append("<tr>\n");
       sb.append("<td>\n");
       sb.append("<table border='1' style=\"border: 4px solid black;\">\n");

       for(int row = 0;row< matrixsize;row++)
       {
           sb.append("<tr >\n");
           for(int col = 0;col< matrixsize;col++)
           {
               sb.append("<td class=\"rec\" >\n");

               sb.append("<div class=\"board droppable\" id=\"orig_row0_col0\" style=\"position: relative;\">");
               sb.append("<a href='/tetravex?row2="+row+"&col2="+col+"'>");
               sb.append("<div class=\"up"+this.gameField.getTile(row,col).getNorth()+ " inner\">");
               sb.append("<div class=\"right"+this.gameField.getTile(row,col).getEast()+ " inner\">");
               sb.append("<div class=\"left"+this.gameField.getTile(row,col).getWest()+ " inner\">");
               sb.append("<div class=\"down"+this.gameField.getTile(row,col).getSouth()+ " inner\">");
               sb.append(" </div>");
               sb.append(" </div>");
               sb.append(" </div>");
               sb.append(" </div>");
               sb.append("</a>");


               sb.append(" </div>");
               sb.append("</td>\n");
           }

           sb.append("</tr>\n");
       }

       sb.append("</table>\n");
       sb.append("</td>\n");
       sb.append("</tr>\n");



       sb.append("</table>\n");

       sb.append(" </div>");


       return sb.toString();
   }

   public String commenting() {
       StringBuilder sb = new StringBuilder();
       if(userController.isLogged()) {

           sb.append("<form>\n" +
                   "  <label for=\"comment\">Comment:</label><br>\n" +
                   "  <input type=\"text\" id=\"comment\" name=\"comment\"><br></form>");
       }
       return sb.toString();
   }
    public int getScore() {
        if(score<0){
            score=0;
        }
        return score;
    }

    public String rating(){
        StringBuilder sb = new StringBuilder();
        if(userController.isLogged()) {
            sb.append("<form>\n" +
                    "  <label for=\"rating\">Rate the game:</label><br>\n" +
                    "  <input type=\"text\" id=\"rating\" name=\"rating\"><br></form>");
        }
        return sb.toString();
    }
}
