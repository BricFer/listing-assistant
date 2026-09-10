package com.bricfern.service

import com.bricfern.dto.GenerateListingResponse
import com.bricfern.exceptions.AiServiceException
import com.bricfern.exceptions.AiTimeoutException
import com.bricfern.exceptions.EmptyDescriptionException
import com.bricfern.exceptions.InvalidAiResponseException
import com.google.genai.kotlin.GenAiApiException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class ServiceLocator {
    private val isMockMode = System.getenv("MOCK_MODE") != "false"

    private val mockService = MockListingService(System.getenv("MOCK_RESPONSE") ?: "success")
    private val aiService = AiListingService()

    fun generateListing(description: String): GenerateListingResponse {
        if(description.isBlank()) {
            throw EmptyDescriptionException(message = "Invalid argument. The description received is empty")
        }

        try {
            val jsonString: String = if (isMockMode) mockService.sendPromptMock(description) else aiService.sendPrompt(description)
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
            throw InvalidAiResponseException("Sorry! The AI response has an invalid format", e)
        }
    }
}