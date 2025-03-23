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

export interface CreatePostDto {
    title: string;
    imageData: string | ArrayBuffer | null;
    imageType: string;
    topicIds: number[];
}

export interface UpdatePostDto {
    id: number;
    title: string;
    topicIds: number[];
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

export const createPost = async (data: CreatePostDto): Promise<Post> => {
    const response = await axiosInstance.post<Post>('/posts', data);
    return response.data;
};

export const deletePost = async (id: number) => {
    const response = await axiosInstance.delete<Post>(`/posts/${id}`);
    return response;
};

export const updatePost = async (data: UpdatePostDto) => {
    const response = await axiosInstance.put<Post>(`/posts`, data);
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