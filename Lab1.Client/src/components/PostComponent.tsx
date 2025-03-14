import {useEffect, useState} from "react";
import {deletePost, fetchPostById, fetchPostsByTopicId, Post} from "../api/postApi.ts";
import {useLocation} from "react-router";
import {useNavigate} from "react-router-dom";
import Masonry from "@mui/lab/Masonry";
import {Box, Button, Card, CardContent, CardMedia, Chip, ImageListItem, TextField, Typography} from "@mui/material";
import {createComment} from "../api/commentApi.ts";

const PostComponent = () => {
    const [post, setPost] = useState<Post | null>(null);
    const [posts, setPosts] = useState<Post[]>([]);
    const [username, setUsername] = useState("");
    const [commentContent, setCommentContent] = useState("");
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        const params = new URLSearchParams(location.search);
        const postId = params.get("postId");

        if (postId)
            fetchPostById(Number(postId)).then(setPost).catch(console.error);
    }, [location.search]);


    useEffect(() => {
        if (post?.topics) {
            fetchPostsByTopicId(Number(post.topics[0].id)).then(setPosts).catch(console.error);
        }
    }, [post]);

    const handleCommentSubmit = async () => {
        if (!post || !username.trim() || !commentContent.trim()) {
            alert("Please fill out both fields");
            return;
        }

        const newComment = {
            postId: post.id.toString(),
            username: username,
            content: commentContent,
        };

        try {
            const createdComment = await createComment(newComment);

            setPost({...post, comments: [...post.comments, createdComment]});
            setUsername("");
            setCommentContent("");
        } catch (error) {
            console.error("Error creating comment: ", error);
            alert("Failed to create comment");
        }
    };

    const handleDeletePost = () => {
        if (post) {
            deletePost(post.id)
                .then( () => {
                    navigate("/");
                })
                .catch((error) => {
                    console.error("Error deleting post: ", error);
                    alert("Failed to delete post");
                });
        }

    }

    return (
        <Box sx={{ width: "100%", minHeight: "100vh", padding: 2 }}>
            {post && (
                <Card sx={{ display: "flex", gap: 4, marginBottom: 4 }}>
                    <Box sx={{ width: "50%", maxWidth: 600, padding: 2 }}>
                        <CardMedia
                            component="img"
                            image={post.imageBase64}
                            alt={post.title}
                            sx={{
                                borderRadius: 2,
                                width: "100%",
                                height: "auto",
                                objectFit: "contain",
                            }}
                        />
                        <Button
                            variant="outlined"
                            onClick={handleDeletePost}
                            sx={{m: 2}}
                        >
                            Delete
                        </Button>
                    </Box>

                    <Box sx={{ flex: 1, padding: 2 }}>
                        <CardContent>
                            <Typography variant="h4" gutterBottom>
                                {post.title}
                            </Typography>
                            <Box>
                                {post.topics.map((topic) => (
                                    <Chip label={topic.name}
                                          onClick={() => navigate(`/?topicId=${topic.id}`)}
                                          sx={{ marginRight: 1 }}
                                    />
                                ))}
                            </Box>
                            <Typography variant="body1" color="text.primary" gutterBottom>
                                Comments:
                            </Typography>
                            {post.comments.map((comment, index) => (
                                <Typography key={index} variant="body2" sx={{ marginBottom: 1 }}>
                                    <strong>{comment.username}:</strong> {comment.content}
                                </Typography>
                            ))}

                            <Box sx={{ marginTop: 4 }}>
                                <TextField
                                    fullWidth
                                    label="Username"
                                    variant="outlined"
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                    sx={{ marginBottom: 2 }}
                                />
                                <TextField
                                    fullWidth
                                    label="Comment"
                                    variant="outlined"
                                    multiline
                                    rows={4}
                                    value={commentContent}
                                    onChange={(e) => setCommentContent(e.target.value)}
                                    sx={{ marginBottom: 2 }}
                                />
                                <Button variant="contained" onClick={handleCommentSubmit}>
                                    Submit Comment
                                </Button>
                            </Box>
                        </CardContent>
                    </Box>
                </Card>
            )}

            <Masonry columns={3} spacing={2}>
                {posts.map((relatedPost, index) => (
                    <ImageListItem key={index}>
                        <img
                            src={relatedPost.imageBase64}
                            alt={`Image ${index}`}
                            loading="lazy"
                            style={{
                                borderRadius: 10,
                                display: "block",
                                width: "100%",
                                cursor: "pointer",
                            }}
                            onClick={() => navigate(`?postId=${relatedPost.id}`)}
                        />
                    </ImageListItem>
                ))}
            </Masonry>
        </Box>
    );
};

export default PostComponent;