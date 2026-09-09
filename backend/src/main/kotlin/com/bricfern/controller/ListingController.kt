package com.bricfern.controller

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.bricfern.data.AiListingService
import com.bricfern.dto.GenerateListingRequest
import com.bricfern.exceptions.AiServiceException
import com.bricfern.exceptions.AiTimeoutException
import com.bricfern.exceptions.EmptyDescriptionException
import com.bricfern.exceptions.InvalidAiResponseException
import kotlinx.serialization.json.Json
import java.io.OutputStream

/***
 * Class use to handle the petition for the listing generation
 */
class ListingController: HttpHandler {
    private val service: AiListingService = AiListingService()

    override fun handle(exchange: HttpExchange) {
        val method = exchange.requestMethod
        val path = exchange.requestURI.path

        when {
            method == "POST" && path == "/api/listing/listing-assistant" -> generateListing(exchange)
            else -> sendResponse(exchange, 404, "Page not found")
        }
    }

    /***
     * Method responsible for getting the listing to show to the user
     */
    private fun generateListing(exchange: HttpExchange) {
        try {
            // Read the body from the request (POST Body)
            val body = exchange.requestBody.bufferedReader().use { it.readText() }

            // from JSON to GenerateListingRequest
            val request = Json.decodeFromString<GenerateListingRequest>(body)
            val response = service.generateListing(request.description)

            val jsonResponse = Json.encodeToString(response)
            exchange.responseHeaders.add("Content-Type", "application/json")
            sendResponse(exchange, 200, jsonResponse)
        } catch (e: EmptyDescriptionException) {
            sendResponse(exchange, 400, e.message ?: "Bad request")
        } catch (e: AiTimeoutException) {
            sendResponse(exchange, 504, e.message ?: "Timeout")
        } catch (e: AiServiceException) {
            sendResponse(exchange, 502, e.message ?: "AI service error")
        } catch (e: InvalidAiResponseException) {
            sendResponse(exchange, 502, e.message ?: "Invalid AI response")
        } catch (e: Exception) {
            sendResponse(exchange, 500, "Unexpected error")
        }
    }

    /***
     * Method responsible for writing the response byte by byte
     */
    private fun sendResponse(exchange: HttpExchange, status: Int, message: String) {
        val bytes = message.toByteArray(Charsets.UTF_8)
        exchange.sendResponseHeaders(status, bytes.size.toLong())
        val os: OutputStream = exchange.responseBody
        os.write(bytes)
        os.close()
    }
}