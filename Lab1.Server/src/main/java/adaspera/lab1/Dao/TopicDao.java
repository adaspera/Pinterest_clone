package adaspera.lab1.Dao;

import adaspera.lab1.Models.Topic;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class TopicDao {

    @Inject
    private EntityManager em;

    public void create(Topic topic) {
        em.persist(topic);
    }

    public Topic findById(int id) {
        return em.find(Topic.class, id);
    }

    public void update(Topic topic) {
        em.merge(topic);
    }

    public void delete(Topic topic) {
        em.remove(em.merge(topic));
    }
}
