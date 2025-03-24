import {useEffect, useState} from "react";
import {deletePost, fetchPostById, fetchPostsByTopicId, Post, updatePost, UpdatePostDto} from "../api/postApi.ts";
import {useLocation} from "react-router";
import {useNavigate} from "react-router-dom";
import Masonry from "@mui/lab/Masonry";
import {
    Box,
    Button,
    Card,
    CardContent,
    CardMedia,
    Chip, FormControl,
    ImageListItem, InputLabel, MenuItem,
    Modal, Select,
    TextField,
    Typography
} from "@mui/material";
import {createComment} from "../api/commentApi.ts";
import {getAllTopics, Topic} from "../api/topicApi.ts";

const PostComponent = () => {
    const [post, setPost] = useState<Post | null>(null);
    const [updatedPost, setUpdatedPost] = useState<UpdatePostDto>({id: NaN, title: "", topicIds: []});
    const [posts, setPosts] = useState<Post[]>([]);
    const [topics, setTopics] = useState<Topic[]>([]);
    const [username, setUsername] = useState("");
    const [commentContent, setCommentContent] = useState("");
    const [openPostModal, setOpenPostModal] = useState(false);
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

    const handleOpenPostModal = async () => {
        await getAllTopics().then(setTopics).catch(console.error);
        setOpenPostModal(true);
    }
    const handleClosePostModal = () => setOpenPostModal(false);

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

    const handleUpdatePost = () => {
        const updatedPostDto: UpdatePostDto = {...updatedPost, id: Number(post?.id)};
        updatePost(updatedPostDto)
            .then(setPost)
            .catch( (error) => {
                console.error("Error updating post: ", error);
                alert("Failed to update post");
            })
            .finally(handleClosePostModal);
    }

    return (
        <>
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
                            <Button
                                variant="outlined"
                                onClick={handleOpenPostModal}
                                sx={{m: 2}}
                            >
                                Edit
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

            <Modal open={openPostModal} onClose={handleClosePostModal}>
                <Box
                    sx={{
                        position: "absolute",
                        top: "50%",
                        left: "50%",
                        transform: "translate(-50%, -50%)",
                        width: 400,
                        bgcolor: "background.paper",
                        boxShadow: 24,
                        p: 4,
                        borderRadius: 2,
                    }}
                >
                    <Typography variant="h6" gutterBottom>
                        Edit post
                    </Typography>
                    <TextField
                        fullWidth
                        label="Title"
                        variant="outlined"
                        value={updatedPost.title}
                        onChange={(e) => setUpdatedPost({ ...updatedPost, title: e.target.value })}
                        sx={{ marginBottom: 2 }}
                    />
                    <FormControl fullWidth sx={{ marginBottom: 2 }}>
                        <InputLabel>Topics</InputLabel>
                        <Select
                            multiple
                            value={updatedPost.topicIds}
                            onChange={(e) => setUpdatedPost({ ...updatedPost, topicIds: e.target.value as number[] })}
                        >
                            {topics.map((topic) => (
                                <MenuItem key={topic.id} value={topic.id}>
                                    {topic.name}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <Button variant="contained" onClick={handleUpdatePost}>
                        Submit
                    </Button>
                </Box>
            </Modal>
        </>

    );
};

export default PostComponent;