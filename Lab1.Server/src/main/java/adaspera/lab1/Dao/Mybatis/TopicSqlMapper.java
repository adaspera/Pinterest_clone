package adaspera.lab1.Dao.Mybatis;

import adaspera.lab1.Models.Topic;

import java.util.List;

public interface TopicSqlMapper {
    void create(Topic topic);
    Topic findById(int id);
    List<Topic> getAll();
    void update(Topic topic);
    void delete(Topic topic);
}
