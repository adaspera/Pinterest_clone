import axiosInstance from "./axiosInstance.ts";

export interface ResultResponse {
    result: number;
    status: string;
}

export const startCalculation = async (data: number): Promise<number> => {
    const response = await axiosInstance.post<number>(`/calculation/start/${data}`);
    return response.data;
};

export const getCalculationResult = async (): Promise<ResultResponse> => {
    const response = await axiosInstance.get<ResultResponse>(`/calculation/result`);
    return response.data;
};