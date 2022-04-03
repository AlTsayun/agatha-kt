package me.okko.agatha.manager.ktor


import io.ktor.server.engine.*
import io.ktor.server.netty.*
import me.okko.agatha.manager.ktor.plugins.configureKoin
import me.okko.agatha.manager.ktor.plugins.configureRouting

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost"){
        configureKoin()
        configureRouting()
    }.start()
}
