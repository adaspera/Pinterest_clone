package adaspera.lab1.Dao;

import adaspera.lab1.Models.Post;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PostDao {
    @Inject
    private EntityManager em;

    @Transactional
    public void create(Post post) {
        em.persist(post);
    }

    public Post findById(int id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        return em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
    }

    @Transactional
    public void update(Post post) {
        em.merge(post);
    }

    @Transactional
    public void delete(Post post) {
        em.remove(post);
    }
}
