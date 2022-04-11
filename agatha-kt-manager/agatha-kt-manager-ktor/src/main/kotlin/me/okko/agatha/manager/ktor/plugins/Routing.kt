package me.okko.agatha.manager.ktor.plugins

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import me.okko.agatha.manager.plugin.PluginFactory
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val pluginFactory: PluginFactory by inject()

    routing {
        get("/pluginOutput/{pluginId}") {
            val pluginId = try {
                call.parameters["pluginId"]?.toInt()
            } catch (e: NumberFormatException) {
                null;
            } ?: return@get call.respondText(
                "Missing plugin id",
                status = HttpStatusCode.BadRequest
            )

            val processedValue = pluginFactory.getByIdOrNull(pluginId)?.getOutput() ?: return@get call.respondText(
                "Plugin with specified id not found",
                status = HttpStatusCode.BadRequest
            )
            call.respond(processedValue)
        }
    }
}