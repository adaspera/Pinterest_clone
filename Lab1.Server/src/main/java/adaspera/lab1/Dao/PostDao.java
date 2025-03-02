package adaspera.lab1.Dao;

import adaspera.lab1.Models.Post;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class PostDao {
    @Inject
    private EntityManager em;

    public void create(Post post) {
        em.persist(post);
    }

    public Post findById(int id) {
        return em.find(Post.class, id);
    }

    public void update(Post post) {
        em.merge(post);
    }

    public void delete(Post post) {
        em.remove(post);
    }
}
