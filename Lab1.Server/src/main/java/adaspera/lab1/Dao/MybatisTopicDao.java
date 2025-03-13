package adaspera.lab1.Dao;

import adaspera.lab1.Dao.Mybatis.TopicSqlMapper;
import adaspera.lab1.Models.Topic;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

@ApplicationScoped
public class MybatisTopicDao {

    @Inject
    private SqlSession sqlSession;

    public void create(Topic topic) {
        sqlSession.getMapper(TopicSqlMapper.class).create(topic);
    }

    public Topic findById(int id) {
        return sqlSession.getMapper(TopicSqlMapper.class).findById(id);
    }

    public List<Topic> getAll() {
        return sqlSession.getMapper(TopicSqlMapper.class).getAll();
    }
}
