package me.okko.agatha.manager.ktor.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*

fun Application.configureCors() {
    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        host("*")
        exposeHeader("x-links-current")
        exposeHeader("x-links-next")
        exposeHeader("x-links-previous")
        allowSameOrigin = true
        allowNonSimpleContentTypes = true
        allowCredentials = true
        allowHeaders { true }
    }
}