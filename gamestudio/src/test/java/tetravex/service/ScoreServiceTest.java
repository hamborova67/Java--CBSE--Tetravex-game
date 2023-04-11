package tetravex.service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.pom.ScoreServiceJDBC;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.entity.Score;

import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;

public class ScoreServiceTest
{
    private ScoreService scoreService = new ScoreServiceJDBC();
    @Test
    public  void testReset()
    {
        scoreService.reset();
        assertEquals(0, scoreService.getTopScores("nine mens morris").size());
    }

    @Test
    public void testAddScores()
    {
        scoreService.reset();

        scoreService.addScore(new Score("tetravex", "slavka", 170,new Date()));
        scoreService.addScore(new Score("tetravex", "mino", 150, new Date()));
        scoreService.addScore(new Score("tetravex", "jozo", 180, new Date()));
        scoreService.addScore(new Score("tetravex", "nina", 105, new Date()));

        var scores = scoreService.getTopScores("tetravex");
        assertEquals(4,scores.size());

        assertEquals("jozo", scores.get(0).getPlayer());
        assertEquals("tetravex", scores.get(0).getGame());
        assertEquals(180, scores.get(0).getPoints());


        assertEquals("slavka", scores.get(1).getPlayer());
        assertEquals("tetravex", scores.get(1).getGame());
        assertEquals(170, scores.get(1).getPoints());

    }

    @Test
    public void testGetTopScores()
    {
        scoreService.reset();
        var date = new Date();

        scoreService.addScore(new Score("tetravec", "kiko", 120, date));
        scoreService.addScore(new Score("tetravex", "nina", 150, date));
        scoreService.addScore(new Score("tetravex", "talia", 186, date));
        scoreService.addScore(new Score("tetravex", "nora", 107, date));

        var scores = scoreService.getTopScores("tetravex");

        assertEquals(3, scores.size());

        assertEquals("tetravex", scores.get(0).getGame());
        assertEquals("talia", scores.get(0).getPlayer());
        assertEquals(186, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedOn());

        assertEquals("tetravex", scores.get(1).getGame());
        assertEquals("nina", scores.get(1).getPlayer());
        assertEquals(150, scores.get(1).getPoints());
        assertEquals(date, scores.get(1).getPlayedOn());

        assertEquals("tetravex", scores.get(2).getGame());
        assertEquals("nora", scores.get(2).getPlayer());
        assertEquals(107, scores.get(2).getPoints());
        assertEquals(date, scores.get(2).getPlayedOn());

    }

}