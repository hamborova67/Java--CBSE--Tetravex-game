package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.context.annotation.FilterType;
import sk.tuke.gamestudio.game.tetravex.consoleUI.ConsoleUI;
import sk.tuke.gamestudio.server.webservice.ScoreServiceRest;
import sk.tuke.gamestudio.service.*;


@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.gamestudio.server.*"))

public class SpringClient {


    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }
    @Bean
    public CommandLineRunner runner(){
        return s-> consoleUI().start();
    }
    @Bean
    public ConsoleUI consoleUI(){
        return new ConsoleUI(2);
    }


    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceRestClient();
    }
    // 1 1 1 1 2 1 2 1 1 2 1 2 2 2 2 2

    @Bean
    public RatingService ratingService(){

        return new RatingServiceRestClient();
    }
    @Bean
    public CommentService commentService(){
       // return new CommentServiceJPA();
        return new CommentServiceRestClient();
    }

}
