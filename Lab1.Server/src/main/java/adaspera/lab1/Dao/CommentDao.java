package adaspera.lab1.Dao;

import adaspera.lab1.Models.Comment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class CommentDao {

    @Inject
    private EntityManager em;

    private void create(Comment comment) {
        em.persist(comment);
    }

    private Comment findById(int id) {
        return em.find(Comment.class, id);
    }

    private void update(Comment comment) {
        em.merge(comment);
    }

    private void delete(Comment comment) {
        em.remove(comment);
    }
}
