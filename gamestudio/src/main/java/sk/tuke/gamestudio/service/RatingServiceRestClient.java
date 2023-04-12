package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;

import java.util.Arrays;

public class RatingServiceRestClient implements RatingService{
    //See value of remote.server.api property in application.properties file
    @Value("${remote.server.api}/rating")
    private String url;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public void setRating(Rating rating) throws RatingException {
        restTemplate.postForEntity(url + "/rating", rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game)  {
        try {
            return restTemplate.getForEntity(url + "/rating"+game,Integer.class).getBody();

        }
        catch (NullPointerException e){
            return -1;
        }
    }

    @Override
    public int getRating(String game, String player) {
        try {
            return restTemplate.getForEntity(url + "/rating/"+game+"/"+player,Integer.class).getBody();

        }
        catch (NullPointerException e){
            return -1;
        }
    }

    @Override
    public void reset(){
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
