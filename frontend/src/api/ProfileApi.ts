import { apiFetch } from "./HttpClient";


const BASE_URL = "/api/user";


export interface Profile {
    username: string,
    createdAt: string
}


export function me() {
    return apiFetch<Profile>(`${BASE_URL}/users/me`);
}