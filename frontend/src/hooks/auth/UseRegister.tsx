import { useContext, useState } from "react";
import { AuthContext } from "../../contexts/AuthContext"
import { ProfileContext } from "../../contexts/ProfileContext";


export function UseRegister() {
    const { register, login } = useContext(AuthContext);
    const { refresh } = useContext(ProfileContext);

    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);

    async function registerUser(
        username: string,
        password: string,
    ) {
        setError(null);
        setLoading(false);

        try {
            await register(username, password);
            //await refresh();
            await login(username, password);
            await refresh();
        } catch (err) {
            setError(err instanceof Error ? err.message : "Registration failed");
            throw err;
        } finally {
            setLoading(false);
        }
    }
}