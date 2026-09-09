package com.bricfern.service

import com.bricfern.dto.MockListing
import kotlinx.serialization.json.Json

class MockListingService {
    private val mockResponse =
        System.getenv("MOCK_RESPONSE") ?: "success"

    fun loadMockExamples(fileName: String): List<MockListing> {
        val content = object {}.javaClass
            .getResourceAsStream("/$fileName")
            ?.bufferedReader()
            ?.readText() ?: throw IllegalStateException("Mock file not found: $fileName")

        return Json.decodeFromString<List<MockListing>>(content)
    }

    fun sendPromptMock(description: String): String {
        val fileName = when (mockResponse) {
            "invalid" -> "mock/invalid.json"
            "malformed" -> "mock/malformed.json"
            else -> "mock/success.json"
        }

        val keywords = description.split(" ")
        val mocks = loadMockExamples(fileName)

        val mock = mocks.find {
            keywords.any { keyword -> it.request.description.contains(keyword, ignoreCase = true) } }
            ?: throw IllegalStateException("No mock found for description")

        return Json.encodeToString(mock.response)
    }
}