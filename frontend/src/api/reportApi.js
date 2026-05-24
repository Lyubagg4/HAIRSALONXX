import { api } from "./axiosInstance";

export async function getClientsByDate(date) {
  const response = await api.get("/api/reports/clients-by-date", {
    params: { date },
  });
  return response.data;
}

export async function getMasterIncome(masterId) {
  const response = await api.get("/api/reports/master-income", {
    params: { masterId },
  });
  return response.data;
}

export async function getPopularService() {
  const response = await api.get("/api/reports/popular-service");
  return response.data;
}

export async function getGenderStats() {
  const response = await api.get("/api/reports/gender-stats");
  return response.data;
}

export async function getRegularClientsCount() {
  const response = await api.get("/api/reports/regular-clients");
  return response.data;
}

export async function getTopMaster() {
  const response = await api.get("/api/reports/top-master");
  return response.data;
}