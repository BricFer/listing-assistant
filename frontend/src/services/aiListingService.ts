export const AiListingService = async (description: string): Promise<AiListingResponse> => {
    const url = import.meta.env.VITE_API_URL;

    const requestBody: AiListingRequest = { description };

    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })

    if (!response.ok) {
        const error = await response.text()
        throw new Error(error);
    }

    const data = await response.json();
    return data;
}