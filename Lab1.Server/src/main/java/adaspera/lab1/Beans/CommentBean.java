package adaspera.lab1.Beans;

import adaspera.lab1.Dao.CommentDao;
import adaspera.lab1.Dao.PostDao;
import adaspera.lab1.Models.Comment;
import adaspera.lab1.Models.Post;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
public class CommentBean {

    @Inject
    private CommentDao commentDao;

    @Inject
    private PostBean postBean;

    @Getter @Setter
    private Comment newComment = new Comment();

    @Transactional
    public void addComment() {
        newComment.setPost(postBean.getPost());
        postBean.getPost().getComments().add(newComment);

        commentDao.create(newComment);
        newComment = new Comment();
    }
}