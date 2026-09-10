package com.bricfern.config

import com.google.genai.kotlin.Client
import com.google.genai.kotlin.types.HttpOptions
import com.google.genai.kotlin.types.HttpRetryOptions

object GeminiConfig {
    
    val client = Client(
        httpOptions = HttpOptions(
            retryOptions = HttpRetryOptions(
                attempts = 3, // Including the initial call.
                initialDelay = 1.0, // seconds
                maxDelay = 30.0, // seconds
                jitter = 1.0,
                httpStatusCodes = listOf(408, 429, 500, 502, 503, 504)
            ),
        )
    )
}