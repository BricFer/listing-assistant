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
        throw new Error('It was not possible to stablish a connection');
    }

    const data = await response.json();
    return data;
}