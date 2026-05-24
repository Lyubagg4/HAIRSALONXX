import { api } from "./axiosInstance";

export async function login(phone, password) {
  const response = await api.post("/api/auth/login", {
    phone,
    password,
  });

  return response.data;
}