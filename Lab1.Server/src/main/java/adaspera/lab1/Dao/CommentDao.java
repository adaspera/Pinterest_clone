package adaspera.lab1.Dao;

import adaspera.lab1.Models.Comment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CommentDao {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public void create(Comment comment) {
        em.persist(comment);
    }

    public Comment findById(int id) {
        return em.find(Comment.class, id);
    }

    public void update(Comment comment) {
        em.merge(comment);
    }

    public void delete(Comment comment) {
        em.remove(comment);
    }
}
