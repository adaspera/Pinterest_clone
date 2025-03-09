import axiosInstance from "./axiosInstance.ts";

export interface Comment {
    id: string;
    content: string;
    username: string;
    postId: string;
}

export const getCommentsByPostId = async (postId: string): Promise<Comment[]> => {
    const response = await axiosInstance.get<Comment[]>(`/comments/byPost/${postId}`);
    return response.data;
}

export const createComment = async (): Promise<Comment> => {
    const response = await axiosInstance.get<Comment>(`/comments`);
    return response.data;
}