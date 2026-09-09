package com.bricfern.dto

import kotlinx.serialization.Serializable

@Serializable
data class MockListing (
    val request: GenerateListingRequest,
    val response: GenerateListingResponse
)