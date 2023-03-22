import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.RatingServiceJDBC;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;


public class TestJDBC {
    public static void main(String[] args) throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/gamestudio", "postgres", "longboardOK");
        RatingService ratingService = new RatingServiceJDBC();
        ratingService.reset();

    }
}
