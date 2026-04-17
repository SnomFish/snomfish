import { createContext } from "react";
import type { ReactNode } from "react";
import * as AuthApi from "../api/AuthApi";


interface AuthContextType {
    register: (
        username: string,
        email: string,
        password: string
    ) => Promise<void>;

    login: (
        username: string,
        password: string
    ) => Promise<void>;

    logout: (
    ) => Promise<void>;
}


export const AuthContext = createContext<AuthContextType>({
    register: async () => {},
    login: async () => {},
    logout: async () => {}
});


export function AuthProvider({ children } : { children : ReactNode}) {

    const register = async(
        username: string,
        email: string,
        password: string
    ) => {
        await AuthApi.register(
            username,
            email, 
            password
        );
    }


    const login = async(
        username: string,
        password: string
    ) => {
        await AuthApi.login(
            username,
            password
        )
    }


    const logout = async() => {
        await AuthApi.logout();
    }


    return (
        <AuthContext.Provider value={{
            register, 
            login, 
            logout
        }}>
            {children}
        </AuthContext.Provider>
    );
}
