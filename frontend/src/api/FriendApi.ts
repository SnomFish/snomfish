import { apiFetch } from "./HttpClient";


const BASE_URL = "/api/users/me";


export interface Friend {
    username: string,
    createdAt: string
}
export interface FriendRequest {
    username: string
}
export interface FriendList {
    friends: Friend[]
}
export interface FriendRequestList {
    friendRequests: FriendRequest[]
}


export function getFriends() {
    return apiFetch<FriendList>(`${BASE_URL}/friends`, {
        method: "GET"
    })
}


export function getFriendRequests() {
    return apiFetch<FriendRequestList>(`${BASE_URL}/friend-requests`, {
        method: "GET"
    })
}


export function sendFriendRequest(
    username: string
) {
    return apiFetch<void>(`${BASE_URL}/friend-requests`, {
        method: "POST",
        body: JSON.stringify({
            username
        })
    })
}


export function acceptFriendRequest(
    username: string
) {
    return apiFetch<void>(`${BASE_URL}/friend-requests/accept`, {
        method: "POST",
        body: JSON.stringify({
            username
        })
    })
}


export function rejectFriendRequest(
    username: string
) {
    return apiFetch<void>(`${BASE_URL}/friend-requests/reject`, {
        method: "POST",
        body: JSON.stringify({
            username
        })
    })
}


export function removeFriend(
    username: string
) {
    return apiFetch<void>(`${BASE_URL}/friends`, {
        method: "POST",
        body: JSON.stringify({
            username
        })
    })
}