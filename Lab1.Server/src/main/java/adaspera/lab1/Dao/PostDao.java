package adaspera.lab1.Dao;

import adaspera.lab1.Models.DTOs.GetPostDto;
import adaspera.lab1.Models.DTOs.UpdatePostDto;
import adaspera.lab1.Models.Post;
import adaspera.lab1.Models.Topic;
import adaspera.lab1.Utils.Mappers.PostMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class PostDao {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public void create(Post post) {
        em.persist(post);
    }

    public Post findById(int id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        return em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
    }

    public Post update(Post post) {
        return em.merge(post);
    }

    public void delete(Post post) {
        em.remove(post);
    }

    public void flush() {
        em.flush();
    }
}
