import { api } from "./axiosInstance";

function extractError(error) {
  return (
    error?.response?.data ||
    error?.message ||
    "Ошибка запроса"
  );
}

export async function getClients() {
  try {
    const response = await api.get("/api/clients");
    return response.data;
  } catch (error) {
    throw new Error(extractError(error));
  }
}

export async function createClient(client) {
  try {
    const response = await api.post("/api/clients", client);
    return response.data;
  } catch (error) {
    throw new Error(extractError(error));
  }
}

export async function deleteClient(id) {
  try {
    await api.delete(`/api/clients/${id}`);
  } catch (error) {
    throw new Error(extractError(error));
  }
}