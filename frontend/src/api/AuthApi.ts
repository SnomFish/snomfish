import { apiFetch } from "./HttpClient"


const BASE_URL = "/api/auth";


export function login(
    username: string,
    password: string
) {
    return apiFetch<void>(`${BASE_URL}/login`, {
        method: "POST",
        body: JSON.stringify({
            username,
            password
        })
    });
}


export function register(
    username: string,
    email: string,
    password: string
) {
    return apiFetch<void>(`${BASE_URL}/register`, {
        method: "POST",
        body: JSON.stringify({
            username,
            email,
            password
        })
    });
}


export function logout() {
    return apiFetch<void>(`${BASE_URL}/logout`, {
        method: "POST"
    });
}