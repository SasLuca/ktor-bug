package com.example

import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.sessions.*
import io.ktor.application.*
import io.ktor.features.ContentTransformationException
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.serialization.*
import kotlinx.serialization.Serializable

fun main(args: Array<String>): Unit =  io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false)
{
    install(ContentNegotiation) { json() }

    routing {
        post("/test")
        {
            @Serializable data class RequestInput(val data: String)
            try {
                call.receive<RequestInput>()
            }
            catch(e: Exception)
            {
                assert(e is ContentTransformationException)
            }
        }
    }
}