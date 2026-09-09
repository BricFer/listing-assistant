package com.bricfern

import com.bricfern.controller.ListingController
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress


fun main() {
    val server = HttpServer.create(InetSocketAddress(8080), 0)

    server.createContext("/api/listing", ListingController())
    server.executor = null
    server.start()

    println("Server started, listening on $server, address: ${server.address}")
}