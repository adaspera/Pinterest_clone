package adaspera.lab1.Dao;

import adaspera.lab1.Models.Topic;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TopicDao {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public void create(Topic topic) {
        em.persist(topic);
    }

    public Topic findById(int id) {
        return em.find(Topic.class, id);
    }

    public List<Topic> getAll() {
        return em.createQuery("select t from Topic t", Topic.class).getResultList();
    }

    public void update(Topic topic) {
        em.merge(topic);
    }

    public void delete(Topic topic) {
        em.remove(em.merge(topic));
    }
}
