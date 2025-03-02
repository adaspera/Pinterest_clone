import { Drawer, List, ListItem, ListItemButton, ListItemText, Box } from "@mui/material";
import { useNavigate } from "react-router-dom";

const AppDrawer = () => {
    const navigate = useNavigate();

    const topics = [
        { name: "Topic 1", path: "/topic1" },
        { name: "Topic 2", path: "/topic2" },
        { name: "Topic 3", path: "/topic3" },
        { name: "Topic 4", path: "/topic4" },
    ];

    const handleTopicClick = (path: string) => {
        navigate(path);
    };

    return (
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
                    {topics.map((topic, index) => (
                        <ListItem key={index} disablePadding>
                            <ListItemButton onClick={() => handleTopicClick(topic.path)}>
                                <ListItemText primary={topic.name} />
                            </ListItemButton>
                        </ListItem>
                    ))}
                </List>
            </Box>
        </Drawer>
    );
};

export default AppDrawer;