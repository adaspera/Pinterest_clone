package adaspera.lab1.Beans;

import adaspera.lab1.Dao.MybatisTopicDao;
import adaspera.lab1.Dao.TopicDao;
import adaspera.lab1.Models.Topic;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class TopicBean implements Serializable {
    @Inject
    private MybatisTopicDao topicDao;

    @Getter @Setter
    private Topic topic;

    @Getter @Setter
    private Topic newTopic;

    @Getter @Setter
    private List<Topic> topics;

    @PostConstruct
    public void init() {
        topic = new Topic();
        newTopic = new Topic();
        getAllTopics();
    }

    public void getAllTopics() {
        topics = topicDao.getAll();
    }

    @Transactional
    public void addTopic() {
        topicDao.create(newTopic);
        topics.add(newTopic);
        newTopic = new Topic();
    }
}