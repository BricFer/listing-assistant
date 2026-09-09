package com.bricfern.config

import com.sun.net.httpserver.Filter
import com.sun.net.httpserver.HttpExchange

object CorsFilter : Filter() {

    override fun description(): String = "CORS Filter"

    override fun doFilter(exchange: HttpExchange, chain: Chain) {
        // Configure the mandatory CORS headers
        val headers = exchange.responseHeaders

        // Allow any origin
        headers.set("Access-Control-Allow-Origin", "*")

        // HTTP methods allow
        headers.set("Access-Control-Allow-Methods", "POST, OPTIONS")

        headers.set("Access-Control-Allow-Headers", "Content-Type")

        if (exchange.requestMethod.equals("OPTIONS", ignoreCase = true)) {
            exchange.sendResponseHeaders(204, 0)
            exchange.close()
            return
        }
        chain.doFilter(exchange)
    }
}