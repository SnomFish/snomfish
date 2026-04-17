import { useContext, useState } from "react";
import { FriendContext } from "../../contexts/FriendContext";
import { ProfileContext } from "../../contexts/ProfileContext";

export function UseDeleteFriend() {
    const { removeFriend } = useContext(FriendContext);
    const { refresh } = useContext(ProfileContext);

    const [ error, setError ] = useState<string | null>(null);
    const [ loading, setLoading ] = useState(false);

    async function deleteUserFriend(
        username: string
    ) {
        setError(null);
        setLoading(true);

        try {
            await removeFriend(username);
            await refresh();
        } catch (err) {
            setError(err instanceof Error ? err.message : "failed");
            throw err;
        } finally {
            setLoading(false);
        }
    }

    return { deleteUserFriend, error, loading };
}