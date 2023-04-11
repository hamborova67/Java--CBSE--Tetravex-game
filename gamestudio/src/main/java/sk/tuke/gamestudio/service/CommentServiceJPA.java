package sk.tuke.gamestudio.service;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import sk.tuke.gamestudio.entity.Comment;


import java.util.List;
@Transactional
public class CommentServiceJPA implements CommentService{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addComment(Comment comment) throws CommentException {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return (List<Comment>) entityManager.createQuery("select s from Comment s where s.game = :game")
                .setParameter("game",game)
                .getResultList();
    }

    @Override
    public void reset() throws CommentException {
        entityManager.createNativeQuery("DELETE from comment").executeUpdate();
    }
}
