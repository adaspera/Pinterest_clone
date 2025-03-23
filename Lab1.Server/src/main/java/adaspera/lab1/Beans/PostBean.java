package adaspera.lab1.Beans;

import adaspera.lab1.Dao.PostDao;
import adaspera.lab1.Dao.TopicDao;
import adaspera.lab1.Models.Comment;
import adaspera.lab1.Models.Post;
import adaspera.lab1.Models.Topic;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class PostBean implements Serializable {
    @Inject
    private TopicBean topicBean;

    @Inject
    private PostDao postDao;

    @Inject
    private TopicDao topicDao;

    @Getter @Setter
    private Post post;

    @Getter @Setter
    private Post newPost;

    @Getter @Setter
    private List<Post> posts;

    @Getter @Setter
    private int selectedTopicId;

    @Getter @Setter
    private int selectedPostId;

    @Getter @Setter
    private List<Integer> selectedTopicIds;

    @PostConstruct
    public void init() {
        newPost = new Post();
        post = new Post();
        posts = postDao.getAll();
        selectedTopicIds = new ArrayList<>();
    }

    @Transactional
    public void addPost() {
        if (newPost.getTopics() == null) {
            newPost.setTopics(new ArrayList<>());
        }
        for (Integer topicId : selectedTopicIds) {
            Topic topic = topicDao.findById(topicId);
            newPost.getTopics().add(topic);
        }
        postDao.create(newPost);
        posts.add(newPost);
        newPost = new Post();
        selectedTopicIds.clear();
    }

    @Transactional
    public String handleDeletePost() {
        post = postDao.findById(selectedPostId);
        posts.remove(post);
        postDao.delete(post);

        return "index.xhtml?faces-redirect=true";
    }

    public String viewPostsByTopic(int topicId) {
        selectedTopicId = topicId;
        Topic topic = topicDao.findById(topicId);

        topicBean.setTopic(topic);

        posts = topic.getPosts();
        return "postsByTopic.xhtml?faces-redirect=true";
    }

    public String viewPostDetails(int postId) {
        selectedPostId = postId;
        post = postDao.findById(postId);

        return "post.xhtml?faces-redirect=true";
    }

    public String viewAllPosts() {
        posts = postDao.getAll();
        return "index.xhtml?faces-redirect=true";
    }
}