package me.okko.agatha.manager.ktor.plugins

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import me.okko.agatha.manager.service.HelloService
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val service : HelloService by inject()

    routing {
        get("/") {
            call.respondText(service.sayHelloWorld())
        }
    }
}