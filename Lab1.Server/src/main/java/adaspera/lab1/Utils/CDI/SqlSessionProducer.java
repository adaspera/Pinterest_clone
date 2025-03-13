package adaspera.lab1.Utils.CDI;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class SqlSessionProducer {

    @Inject
    private SqlSessionFactory sqlSessionFactory;

    @Produces
    @RequestScoped
    public SqlSession createSqlSession() {
        return sqlSessionFactory.openSession(true);
    }

    public void closeSqlSession(@Disposes SqlSession sqlSession) {
        sqlSession.close();
    }
}