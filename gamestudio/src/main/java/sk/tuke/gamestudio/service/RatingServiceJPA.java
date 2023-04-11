package sk.tuke.gamestudio.service;


import org.hibernate.NonUniqueResultException;
import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;



@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void setRating(Rating rating) throws RatingException {

        entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        if(game == null || game.trim().isEmpty()) {
            throw new RatingException("Invalid game name");
        }

        try {
            TypedQuery<Double> query = entityManager.createQuery(
                    "select avg(s.rating) from Rating s where s.game = :game", Double.class);
            query.setParameter("game", game);
            Double result = query.getSingleResult();
            return result != null ? result.intValue() : 0;
        } catch (NoResultException | NonUniqueResultException e) {
            throw new RatingException("Error getting average rating for game: " + game, e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return entityManager.createQuery("select s from Rating s where game = :game and player =:player")
                .setParameter("game",game)
                .setParameter("player",player)
                .getFirstResult();
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNativeQuery("DELETE from Rating").executeUpdate();
    }
}
