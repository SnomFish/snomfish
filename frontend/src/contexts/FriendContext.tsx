import { createContext } from "react";
import type { ReactNode } from "react";
import * as FriendApi from "../api/FriendApi";


interface FriendContextType {
    sendFriendRequest: (
        username: string,
    ) => Promise<void>;

    acceptFriendRequest: (
        username: string,
    ) => Promise<void>;

    rejectFriendRequest: (
        username: string
    ) => Promise<void>;

    removeFriend: (
        username: string
    ) => Promise<void>;
}


export const FriendContext = createContext<FriendContextType>({
    sendFriendRequest: async () => {},
    acceptFriendRequest: async () => {},
    rejectFriendRequest: async () => {},
    removeFriend: async () => {}
});


export function FriendProvider({ children } : { children : ReactNode}) {

    const sendFriendRequest = async(username: string) => {
        await FriendApi.sendFriendRequest(username);
    }


    const acceptFriendRequest = async(username: string) => {
        await FriendApi.acceptFriendRequest(username);
    }


    const rejectFriendRequest = async(username: string) => {
        await FriendApi.rejectFriendRequest(username);
    }


    const removeFriend = async(username: string) => {
        await FriendApi.removeFriend(username);
    }


    return (
        <FriendContext.Provider value={{
            sendFriendRequest, 
            acceptFriendRequest, 
            rejectFriendRequest, 
            removeFriend 
        }}>
            {children}
        </FriendContext.Provider>
    );
}
