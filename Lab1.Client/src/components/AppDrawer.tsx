import {
    Drawer, List, ListItem, ListItemButton, ListItemText, Box, Button, Modal,
    Typography, TextField, Select, MenuItem, InputLabel, FormControl
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { createTopic, getAllTopics, Topic } from "../api/topicApi.ts";
import { createPost } from "../api/postApi.ts";

const AppDrawer = () => {
    const navigate = useNavigate();
    const [topics, setTopics] = useState<Topic[]>([]);
    const [openTopicModal, setOpenTopicModal] = useState(false);
    const [openPostModal, setOpenPostModal] = useState(false);
    const [newTopic, setNewTopic] = useState("");
    const [newPost, setNewPost] = useState({
        title: "",
        image: null as File | null,
        topicIds: [] as number[]
    });

    useEffect(() => {
        getAllTopics().then(setTopics).catch(console.error);
    }, []);

    const handleTopicClick = (path: string) => {
        navigate(`/?topicId=${path}`);
    };

    const handleOpenTopicModal = () => setOpenTopicModal(true);
    const handleCloseTopicModal = () => setOpenTopicModal(false);

    const handleOpenPostModal = () => setOpenPostModal(true);
    const handleClosePostModal = () => setOpenPostModal(false);

    const handleAddTopic = () => {
        if (newTopic.trim() !== "") {
            const topicToCreate = { name: newTopic };
            createTopic(topicToCreate)
                .then((createdTopic) => {
                    console.log(createdTopic);
                    setTopics((prevTopics) => [...prevTopics, createdTopic]);
                    setNewTopic("");
                    handleCloseTopicModal();
                })
                .catch(console.error);
        }
    };


    const handleAddPost = () => {
        if (newPost.title.trim() !== "" && newPost.image && newPost.topicIds.length > 0) {
            const reader = new FileReader();
            reader.onload = () => {
                if (!newPost.image) {
                    alert("Please select an image");
                    return;
                }

                const payload = {
                    title: newPost.title,
                    topicIds: newPost.topicIds,
                    imageData: reader.result,
                    imageType: newPost.image.type,
                };

                createPost(payload)
                    .then(() => {
                        setNewPost({ title: "", image: null, topicIds: [] });
                        handleClosePostModal();
                        alert("Post created successfully");
                    })
                    .catch((error) => {
                        console.error("Error creating post: ", error);
                        console.log(error.message);
                        alert("Failed to create post");
                    });
            };
            reader.readAsDataURL(newPost.image);
        } else {
            alert("Please fill out all fields");
        }
    };

    return (
        <>
            <Drawer
                variant="permanent"
                sx={{
                    width: 240,
                    flexShrink: 0,
                    "& .MuiDrawer-paper": {
                        width: 240,
                        boxSizing: "border-box",
                    },
                }}
            >
                <Box sx={{ overflow: "auto" }}>
                    <List>
                        {topics && topics.map((topic, index) => (
                            <ListItem key={index} disablePadding>
                                <ListItemButton onClick={() => handleTopicClick(topic.id)}>
                                    <ListItemText primary={topic.name} />
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                    <Button variant="contained" sx={{ margin: 2 }} onClick={handleOpenTopicModal}>
                        Add Topic
                    </Button>
                    <Button variant="contained" sx={{ margin: 2 }} onClick={handleOpenPostModal}>
                        Create Post
                    </Button>
                </Box>
            </Drawer>

            {/* Add Topic Modal */}
            <Modal open={openTopicModal} onClose={handleCloseTopicModal}>
                <Box
                    sx={{
                        position: "absolute",
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: 400,
                        bgcolor: "background.paper",
                        boxShadow: 24,
                        p: 4,
                        borderRadius: 2,
                    }}
                >
                    <Typography variant="h6" gutterBottom>
                        Add New Topic
                    </Typography>
                    <TextField
                        fullWidth
                        label="Topic Name"
                        variant="outlined"
                        value={newTopic}
                        onChange={(e) => setNewTopic(e.target.value)}
                        sx={{ marginBottom: 2 }}
                    />
                    <Button variant="contained" onClick={handleAddTopic}>
                        Add
                    </Button>
                </Box>
            </Modal>

            {/* Create Post Modal */}
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
                        Create New Post
                    </Typography>
                    <TextField
                        fullWidth
                        label="Title"
                        variant="outlined"
                        value={newPost.title}
                        onChange={(e) => setNewPost({ ...newPost, title: e.target.value })}
                        sx={{ marginBottom: 2 }}
                    />
                    <input
                        type="file"
                        accept="image/*"
                        onChange={(e) => {
                            if (e.target.files && e.target.files[0]) {
                                setNewPost({ ...newPost, image: e.target.files[0] });
                            }
                        }}
                        style={{ marginBottom: 16 }}
                    />
                    <FormControl fullWidth sx={{ marginBottom: 2 }}>
                        <InputLabel>Topics</InputLabel>
                        <Select
                            multiple
                            value={newPost.topicIds}
                            onChange={(e) => setNewPost({ ...newPost, topicIds: e.target.value as number[] })}
                        >
                            {topics.map((topic) => (
                                <MenuItem key={topic.id} value={topic.id}>
                                    {topic.name}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <Button variant="contained" onClick={handleAddPost}>
                        Create
                    </Button>
                </Box>
            </Modal>
        </>
    );
};

export default AppDrawer;