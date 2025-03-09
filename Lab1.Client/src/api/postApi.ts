import axiosInstance from './axiosInstance';
import { Comment } from './commentApi.ts'
import {Topic} from "./topicApi.ts";

export interface Post {
    id: number;
    title: string;
    imageData: Uint8Array;
    imageType: string;
    topics: Topic[];
    comments: Comment[];
    imageBase64?: string;
}

export const fetchPosts = async (): Promise<Post[]> => {
    const response = await axiosInstance.get<Post[]>('/posts');

    const postsWithBase64 = response.data.map((post) => {
        if (post.imageData) {
            const base64 = btoa(
                new Uint8Array(post.imageData).reduce(
                    (data, byte) => data + String.fromCharCode(byte), ''
                )
            );
            post.imageBase64 = `data:${post.imageType};base64,${base64}`;
        }
        return post;
    });

    return postsWithBase64;
};

export const fetchPostById = async (id: number): Promise<Post> => {
    const response = await axiosInstance.get<Post>(`/posts/${id}`);
    const post = response.data;

    if (post.imageData) {
        const base64 = btoa(
            new Uint8Array(post.imageData).reduce(
                (data, byte) => data + String.fromCharCode(byte), ''
            )
        );
        post.imageBase64 = `data:${post.imageType};base64,${base64}`;
    }

    return post;
};

export const fetchPostsByTopicId = async (id: number): Promise<Post[]> => {
    const response = await axiosInstance.get<Post[]>(`/posts/byTopic/${id}`);

    const postsWithBase64 = response.data.map((post) => {
        if (post.imageData) {
            const base64 = btoa(
                new Uint8Array(post.imageData).reduce(
                    (data, byte) => data + String.fromCharCode(byte), ''
                )
            );
            post.imageBase64 = `data:${post.imageType};base64,${base64}`;
        }
        return post;
    });

    return postsWithBase64;
}

export const createPost = async (data: Post): Promise<Post> => {
    const response = await axiosInstance.post<Post>('/posts', data);
    return response.data;
};

