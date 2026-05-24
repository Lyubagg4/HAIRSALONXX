import { api } from "./axiosInstance";

function extractError(error) {
  return (
    error?.response?.data ||
    error?.message ||
    "Ошибка запроса"
  );
}

export async function getVisits() {
  try {
    const response = await api.get("/api/visits");
    return response.data;
  } catch (error) {
    throw new Error(extractError(error));
  }
}

export async function createVisit(visit) {
  try {
    const response = await api.post("/api/visits", visit);
    return response.data;
  } catch (error) {
    throw new Error(extractError(error));
  }
}