package sk.tuke.kpi.kp.tetravex.service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.RatingServiceJDBC;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RatingServiceTest
{
    private RatingService ratingService = new RatingServiceJDBC();

    private Date date = new Date();
    @Test
    public void testReset()
    {
        ratingService.reset();
        assertEquals(0,ratingService.getRating("tetravex","tetravex"));
    }

    @Test
    public void testGetAverageRating()
    {
        ratingService.reset();

        ratingService.setRating(new Rating("xx","tetravexx",1,date));
        ratingService.setRating(new Rating("mino","tetravexx",5,date));
        ratingService.setRating(new Rating("monika","tetravex",1,date));
        ratingService.setRating(new Rating("janko","tetrave",1,date));
        ratingService.setRating(new Rating("genci","tetrave",1,date));
        ratingService.setRating(new Rating("nina","tetravex",5, date));
        ratingService.setRating(new Rating("Minko","tetravex",1, date));
        int rating = ratingService.getAverageRating("tetravexx");
        assertEquals(3,rating);
        rating = ratingService.getAverageRating("tetravex");
        assertEquals(2,rating);
    }

    @Test
    public void testSetRating()
    {
        ratingService.reset();
        ratingService.setRating(new Rating("xx","tetravexx",1,date));
        ratingService.setRating(new Rating("mino","tetravexx",5,date));
        ratingService.setRating(new Rating("monika","tetravex",1,date));
        ratingService.setRating(new Rating("janko","tetrave",3,date));
        ratingService.setRating(new Rating("genci","tetrave",1,date));
        ratingService.setRating(new Rating("nina","tetravex",5, date));
        ratingService.setRating(new Rating("Minko","tetravex",1, date));
        var rating = ratingService.getRating("tetravex", "nina");
        assertEquals(5,rating);
        rating = ratingService.getRating("tetravex", "monika");
        assertEquals(1,rating);
        rating = ratingService.getRating("tetrave", "janko");
        assertEquals(3,rating);
    }

    @Test
    public void testGetRating()
    {
        ratingService.reset();
        ratingService.setRating(new Rating("monika","tetravex",1,date));
        ratingService.setRating(new Rating("janko","tetrave",3,date));
        ratingService.setRating(new Rating("genci","tetrave",2,date));

        var rating = ratingService.getRating("tetravex", "monika");
        assertEquals(1,rating);
        rating = ratingService.getRating("tetrave", "janko");
        assertEquals(3,rating);
        rating = ratingService.getRating("tetrave", "genci");
        assertEquals(2,rating);
    }

}
