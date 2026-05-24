import { api } from "./axiosInstance";

function extractError(error) {
  return (
    error?.response?.data ||
    error?.message ||
    "Ошибка запроса"
  );
}

export async function getServices() {
  try {
    const response = await api.get("/api/services");
    return response.data;
  } catch (error) {
    throw new Error(extractError(error));
  }
}

export async function createService(service) {
  try {
    const response = await api.post("/api/services", service);
    return response.data;
  } catch (error) {
    throw new Error(extractError(error));
  }
}

export async function deleteService(id) {
  try {
    await api.delete(`/api/services/${id}`);
  } catch (error) {
    throw new Error(extractError(error));
  }
}