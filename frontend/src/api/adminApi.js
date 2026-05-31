import { api } from "./axiosInstance";

export async function createAdmin(admin) {
  const response = await api.post("/api/admins", admin);
  return response.data;
}