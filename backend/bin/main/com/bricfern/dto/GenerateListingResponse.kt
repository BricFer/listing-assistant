package com.bricfern.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenerateListingResponse(
    val title: String,
    val tags: List<String>,
    val minPrice: Double,
    val maxPrice: Double
)