package sk.tuke.gamestudio.service;
import sk.tuke.gamestudio.entity.Rating;
import java.sql.*;
public class RatingServiceJDBC implements RatingService{

    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "longboardOK";
    public static final String SELECT = "SELECT AVG(rating) FROM rating WHERE game=?";
    public static final String SELECT_PLAYER = "SELECT game, player, rating FROM rating WHERE game=? AND player=?" ;
    public static final String SELECT_PLAYER_RATING = "UPDATE rating SET  rating=? WHERE game=? AND player=?";
    public static final String DELETE = "DELETE FROM rating";
    public static final String INSERT = "INSERT INTO rating (game, player, rating, ratedOn) VALUES (?, ?, ?, ?)";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT);
        ) {
            statement.setInt(3, rating.getRating());
            statement.setString(2, rating.getGame());
            statement.setString(1, rating.getPlayer());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ScoreException("Problem inserting rating.", e);
        }

    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                int avg = rs.getInt(1);
                return avg;
            }
        } catch (SQLException e) {
            throw new ScoreException("Problem selecting average rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_PLAYER);
        ) {
            statement.setString(1, game);
            statement.setString(2, player);
            int rating =0;
            try (ResultSet rs = statement.executeQuery()) {
                if(rs.next()){
                    rating = rs.getInt(3);
                }

                return rating;
            }
        } catch (SQLException e) {
            throw new ScoreException("Problem selecting rating", e);

        }
    }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new ScoreException("Problem deleting score", e);
        }

    }
}
