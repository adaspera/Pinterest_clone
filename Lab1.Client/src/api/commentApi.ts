import axiosInstance from "./axiosInstance.ts";

export interface Comment {
    id: string;
    content: string;
    username: string;
    postId: string;
}

export interface CreateCommentDto {
    content: string;
    username: string;
    postId: string;
}

export const getCommentsByPostId = async (postId: string): Promise<Comment[]> => {
    const response = await axiosInstance.get<Comment[]>(`/comments/byPost/${postId}`);
    return response.data;
}

export const createComment = async (newComment: CreateCommentDto): Promise<Comment> => {
    const response = await axiosInstance.post<Comment>(`/comments`, newComment);
    return response.data;
}