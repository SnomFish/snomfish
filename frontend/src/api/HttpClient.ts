export async function apiFetch<T = unknown>(
    url: string,
    options: RequestInit = {}
): Promise<T | null> {
    const response = await fetch(url, {
        credentials: "include",
        ...options,
        headers: {
            ...(options.body ? {"Content-Type": "application/json"} : {}),
            ...(options.headers || {})
        }
    });

    if (!response.ok) {
        let message = `HTTP ${response.status}`;

        try {
            const data = await response.json();
            if (data?.message) {
                message = data.message;
                console.log(message);
            }
        } catch {}

        throw new Error(message);
    }

    if (response.status === 204) {
        return null;
    }

    try {
        return await response.json();
    } catch {
        return null;
    }
}