package adaspera.lab1.Beans;

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

    @PersistenceContext
    private EntityManager em;

    @Inject
    private PostBean postBean;

    @Getter @Setter
    private Comment newComment = new Comment();

//    @Transactional
//    public String addComment() {
//        Post currentPost = postBean.getCurrentPost();
//        if (currentPost != null) {
//            newComment.setPost(currentPost);
//            em.persist(newComment);
//
//            Post refreshedPost = em.find(Post.class, currentPost.getId());
//            postBean.setCurrentPost(refreshedPost);
//
//            newComment = new Comment();
//        }
//
//        return "viewPost?faces-redirect=true";
//    }
}