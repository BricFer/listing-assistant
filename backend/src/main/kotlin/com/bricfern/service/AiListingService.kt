package com.bricfern.data

import com.bricfern.config.GeminiConfig
import com.bricfern.dto.GenerateListingResponse
import com.bricfern.exceptions.AiServiceException
import com.bricfern.exceptions.AiTimeoutException
import com.bricfern.exceptions.EmptyDescriptionException
import com.bricfern.exceptions.InvalidAiResponseException
import com.google.genai.kotlin.GenAiApiException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

/***
 * Class responsible for providing the description and making the call to the AI
 */
class AiListingService {
    
    fun sendPrompt(description: String): String = runBlocking {

        withTimeout(120_000L) {

            val prompt = """
                Impersonate a marketing expert specialize in copywriting. Your goal is to assist customers create
                appealing ads for a second hand marketplace, like Wallapop.
                Base on the following brief description:
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

    fun generateListing(description: String): GenerateListingResponse {
        if(description.isBlank()) {
            throw EmptyDescriptionException(message = "Invalid argument. The description received is empty")
        }
        try {
            val jsonString: String = sendPrompt(description)
            val generatedListing = Json.decodeFromString<GenerateListingResponse>(jsonString)
            return generatedListing
        } catch (e: GenAiApiException) {
            e.printStackTrace()
            throw AiServiceException("There has been an error connecting with the AI service", e)
        } catch (e: TimeoutCancellationException) {
            e.printStackTrace()
            throw AiTimeoutException("The AI service is taking too long. Please try again later.", e)
        } catch (e: SerializationException) {
            e.printStackTrace()
            throw InvalidAiResponseException("The AI response has an invalid format", e)
        }
    }
}