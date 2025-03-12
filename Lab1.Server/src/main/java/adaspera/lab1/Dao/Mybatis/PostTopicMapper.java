package adaspera.lab1.Dao.Mybatis;

import adaspera.lab1.Models.Mybatis.PostTopicExample;
import adaspera.lab1.Models.Mybatis.PostTopicKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PostTopicMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post_topic
     *
     * @mbg.generated Wed Mar 12 11:37:10 EET 2025
     */
    long countByExample(PostTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post_topic
     *
     * @mbg.generated Wed Mar 12 11:37:10 EET 2025
     */
    int deleteByExample(PostTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post_topic
     *
     * @mbg.generated Wed Mar 12 11:37:10 EET 2025
     */
    int deleteByPrimaryKey(PostTopicKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post_topic
     *
     * @mbg.generated Wed Mar 12 11:37:10 EET 2025
     */
    int insert(PostTopicKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post_topic
     *
     * @mbg.generated Wed Mar 12 11:37:10 EET 2025
     */
    int insertSelective(PostTopicKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post_topic
     *
     * @mbg.generated Wed Mar 12 11:37:10 EET 2025
     */
    List<PostTopicKey> selectByExample(PostTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post_topic
     *
     * @mbg.generated Wed Mar 12 11:37:10 EET 2025
     */
    int updateByExampleSelective(@Param("record") PostTopicKey record, @Param("example") PostTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post_topic
     *
     * @mbg.generated Wed Mar 12 11:37:10 EET 2025
     */
    int updateByExample(@Param("record") PostTopicKey record, @Param("example") PostTopicExample example);
}