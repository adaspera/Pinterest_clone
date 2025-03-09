package adaspera.lab1.Dao;

import adaspera.lab1.Models.Comment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CommentDao {

    @Inject
    private EntityManager em;

    @Transactional
    public void create(Comment comment) {
        em.persist(comment);
    }

    public Comment findById(int id) {
        return em.find(Comment.class, id);
    }

    @Transactional
    public void update(Comment comment) {
        em.merge(comment);
    }

    @Transactional
    public void delete(Comment comment) {
        em.remove(comment);
    }
}
