import { useContext, useState } from "react";
import { AuthContext } from "../../contexts/AuthContext";
import { ProfileContext } from "../../contexts/ProfileContext";


export function UseLogout() {
    const { logout } = useContext(AuthContext);
    const { refresh } = useContext(ProfileContext);

    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);

    async function logoutUser() {
        setError(null);
        setLoading(true);

        try {
            await logout();
            await refresh();
        } catch (err) {
            setError(err instanceof Error ? err.message : "Login failed");
            throw err;
        } finally {
            setLoading(false);
        }
    }

    return { logoutUser, loading, error };
}