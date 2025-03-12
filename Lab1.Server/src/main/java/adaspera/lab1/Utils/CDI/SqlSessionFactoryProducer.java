package adaspera.lab1.Utils.CDI;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class SqlSessionFactoryProducer {

    @Produces
    @ApplicationScoped
    public SqlSessionFactory createSqlSessionFactory() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mybatis-config.xml")) {
            return new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("SqlSessionFactoryProducer.createSqlSessionFactory: ", e);
        }
    }
}