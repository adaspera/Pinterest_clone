import { useEffect, useState } from "react";
import Masonry from "@mui/lab/Masonry"
import {Box, ImageListItem} from "@mui/material";
import {fetchPosts, fetchPostsByTopicId, Post} from "../api/postApi.ts";
import {useLocation} from "react-router";

function Home() {
    const [posts, setPosts] = useState<Post[]>([]);
    const location = useLocation();

    useEffect(() => {
        const params = new URLSearchParams(location.search);
        const topicId = params.get("topicId");

        if (topicId) {
            fetchPostsByTopicId(Number(topicId)).then(setPosts).catch(console.error);
        } else {
            fetchPosts().then(setPosts).catch(console.error);
        }
    }, [location.search]);

    return (
        <Box sx={{ width: "100%", height: "100vh" }}>
            <Masonry columns={3} spacing={2}>
                {posts.map((post, index) => (
                    <ImageListItem key={index}>
                        <img
                            src={post.imageBase64}
                            alt={`Image ${index}`}
                            loading="lazy"
                            style={{
                                borderRadius: 10,
                                display: 'block',
                                width: '100%',
                            }}
                        />
                    </ImageListItem>
                ))}
            </Masonry>
        </Box>
    );
}

export default Home;