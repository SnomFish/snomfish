import { createContext, useEffect, useState } from "react";
import type { ReactNode } from "react";
import * as ProfileApi from "../api/ProfileApi";


interface ProfileContextType {
    profile: ProfileApi.Profile | null;
    isAuthenticated: boolean;
    loading: boolean;

    refresh: (   
    ) => void;

    updateUser: (
        updated: ProfileApi.Profile
    ) => void
}


export const ProfileContext = createContext<ProfileContextType>({
    profile: null,
    isAuthenticated: false,
    loading: true,
    refresh: async() => {},
    updateUser: async() => {}
});


export function UserProvider({ children }: { children: ReactNode }) {
    const [profile, setUser] = useState<ProfileApi.Profile | null>(null);
    const [loading, setLoading] = useState(true);


    const refresh = async () => {
        try {
            const data = await ProfileApi.me();
            setUser(data);
        } catch {
            setUser(null);
        }
    };
    useEffect(() => {
        const init = async () => {
            setLoading(true);
            await refresh();
            setLoading(false);
        };
        init();
    }, []);


    const updateUser = (updated: ProfileApi.Profile) => {
        setUser(updated);
    };


    return (
        <ProfileContext.Provider value={{
            profile,
            isAuthenticated: !!profile,
            loading,
            refresh,
            updateUser
        }}>
            {children}
        </ProfileContext.Provider>
    );
}