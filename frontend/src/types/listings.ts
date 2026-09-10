type AiListingRequest = {
    description: string;
}

type AiListingResponse = {
    title: string;
    tags: string[];
    minPrice: number;
    maxPrice: number;
}