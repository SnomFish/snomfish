import { useContext, useState } from "react";
import { FriendContext } from "../../contexts/FriendContext";
import { ProfileContext } from "../../contexts/ProfileContext";

export function UseAcceptFriendRequest() {
    const { acceptFriendRequest } = useContext(FriendContext);
    const { refresh } = useContext(ProfileContext);

    const [ error, setError ] = useState<string | null>(null);
    const [ loading, setLoading ] = useState(false);

    async function acceptUserFriendRequest(
        username: string
    ) {
        setError(null);
        setLoading(true);

        try {
            await acceptFriendRequest(username);
            await refresh();
        } catch (err) {
            setError(err instanceof Error ? err.message : "failed");
            throw err;
        } finally {
            setLoading(false);
        }
    }

    return { acceptUserFriendRequest, error, loading };
}