import { useContext } from "react";
import { ProfileContext } from "../../contexts/ProfileContext";

export function UseProfile() {
    const { profile, loading, isAuthenticated, refresh } = useContext(ProfileContext);
    return { profile, loading, isAuthenticated, refresh };
}