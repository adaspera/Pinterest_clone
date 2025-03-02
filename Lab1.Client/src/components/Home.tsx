import { useEffect, useState } from "react";
import axios from "axios";
import Masonry from "@mui/lab/Masonry"
import {Box, ImageListItem} from "@mui/material";

function Home() {
    const API_URL = "http://localhost:8080/Lab1.Server-1.0-SNAPSHOT/api/post";

    const [images, setImages] = useState<string[]>([]);

    useEffect(() => {
        axios.get(API_URL)
            .then((response) => {
                const base64Images = response.data.map((post: any) => {
                    const base64 = btoa(
                        new Uint8Array(post.imageData).reduce(
                            (data, byte) => data + String.fromCharCode(byte), ''
                        )
                    );
                    return `data:image/jpeg;base64,${base64}`;
                });
                setImages(base64Images);
            })
            .catch((error) => {
                console.error("Error fetching data:", error);
            });
    }, []);

    return (
        <Box sx={{ width: "100%", height: "100vh", overflowY: "scroll" }}>
            <Masonry columns={3} spacing={2}>
                {images.map((image, index) => (
                    <ImageListItem key={index}>
                        <img
                            src={image}
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