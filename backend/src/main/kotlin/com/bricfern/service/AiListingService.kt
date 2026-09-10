package com.bricfern.service

import com.bricfern.config.GeminiConfig
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

class AiListingService {
    
    fun sendPrompt(description: String): String = runBlocking {

        withTimeout(120_000L) {

            val prompt = """
                Impersonate a marketing expert specialize in copywriting. Your goal is to assist customers create
                appealing ads for a second hand marketplace, like Wallapop.
                Based on the following brief description:
                $description
                Return a SINGLE JSON with the exact same structure, without any additional text.
                {
                    "tags": ["xsport", "ciclismo", "freestyle"],
                    "title": "Bicicleta BMX en excelente estado",
                    "minPrice": 70.0,
                    "maxPrice": 125.5
                }
                Requirements:
                - "tags" must contain between 3 and 5 relevant search tags.
                - "title" must be a short title with maximum of 60 characters.
                - "minPrice" must be the minimum price based on the market and the characteristics of the product.
                - "maxPrice" must be the maximum price based on the market and the characteristics of the product.
                - "minPrice" and "maxPrice" must be plain numbers (no currency symbols, no text), representing the price
                in euros.
            """.trimIndent()

            val response = GeminiConfig.client.models.generateContent(
                model = "gemini-flash-latest",
                text = prompt
            )
            response.text ?: ""
        }
    }
}