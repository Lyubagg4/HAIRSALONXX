import { api } from "./axiosInstance";

function extractError(error) {
  return (
    error?.response?.data ||
    error?.message ||
    "Ошибка запроса"
  );
}

export async function getMasters() {
  try {
    const response = await api.get("/api/masters");
    return response.data;
  } catch (error) {
    throw new Error(extractError(error));
  }
}

export async function createMaster(master) {
  try {
    const response = await api.post("/api/masters", master);
    return response.data;
  } catch (error) {
    throw new Error(extractError(error));
  }
}

export async function deleteMaster(id) {
  try {
    await api.delete(`/api/masters/${id}`);
  } catch (error) {
    throw new Error(extractError(error));
  }
}