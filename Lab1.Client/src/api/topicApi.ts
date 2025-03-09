import axiosInstance from "./axiosInstance.ts";

export interface Topic {
    id: string;
    name: string;
}

export const getAllTopics = async (): Promise<Topic[]> => {
    const response = await axiosInstance.get<Topic[]>('/topics');
    return response.data;
}

export const createTopic = async (topic: Omit<Topic, 'id'>): Promise<Topic> => {
    const response = await axiosInstance.post<Topic>('/topics', topic);
    return response.data;
}