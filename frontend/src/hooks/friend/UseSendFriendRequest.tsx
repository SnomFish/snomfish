import { useContext, useState } from "react";
import { FriendContext } from "../../contexts/FriendContext";
import { ProfileContext } from "../../contexts/ProfileContext";

export function UseSendFriendRequest() {
    const { sendFriendRequest } = useContext(FriendContext);
    const { refresh } = useContext(ProfileContext);

    const [ error, setError ] = useState<string | null>(null);
    const [ loading, setLoading ] = useState(false);

    async function sendUserFriendRequest(
        username: string
    ) {
        setError(null);
        setLoading(true);

        try {
            await sendFriendRequest(username);
            await refresh();
        } catch (err) {
            setError(err instanceof Error ? err.message : "failed");
            throw err;
        } finally {
            setLoading(false);
        }
    }

    return { sendUserFriendRequest, error, loading };
}