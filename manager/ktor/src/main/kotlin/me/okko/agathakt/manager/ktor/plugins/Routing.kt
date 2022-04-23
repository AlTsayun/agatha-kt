package me.okko.agathakt.manager.ktor.plugins

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import me.okko.agathakt.manager.plugin.PluginFactory
import me.okko.agathakt.manager.service.ScriptComposer
import me.okko.agathakt.manager.service.SensorDataLoader
import org.koin.ktor.ext.inject
import kotlin.io.path.exists

fun Application.configureRouting() {
    val pluginFactory: PluginFactory by inject()
    val sensorDataLoader: SensorDataLoader by inject()
    val scriptComposer: ScriptComposer by inject()

    routing {
        get("/meduimOutput/{meduimId}") {
            restErrors {
                val meduimId = parseIntParam("meduimId")

                val sensorTypeIdToData = sensorDataLoader.loadSensorDataForMeduimByIdOrNull(meduimId) ?:
                    throw IdNotFoundException("meduim", meduimId)

                val processedValue = pluginFactory.getAllForMeduimById(1)
                    .map {
                        it.computeFromSensorData(sensorTypeIdToData)
                    }
                call.respond(processedValue)
            }
        }

        route("/script") {
            get("/{mediumId}") {
                restErrors {
                    val meduimId = parseIntParam("mediumId")
                    // construct reference to file
                    // ideally this would use a different filename
                    val file = scriptComposer.get(meduimId)
                    if(!file.exists()) {
                        throw NotFoundException();
                    }
                    call.respondFile(file.toFile())
                }
            }
        }
    }
}



private fun PipelineContext<Unit, ApplicationCall>.parseIntParam(param: String) =
    try {
        getParam(param).toInt()
    } catch (e: NumberFormatException) {
        throw ParamParseException(param, Int.javaClass)
    }

private fun PipelineContext<Unit, ApplicationCall>.getParam(param: String) =
        call.parameters[param]?: throw MissingUrlParamException(param)

private suspend fun PipelineContext<Unit, ApplicationCall>.restErrors(
    body: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit
) {
    try {
        body.invoke(this, this.subject)
    } catch (e: RestException) {
        call.respondText(text = e.toString(), status = e.status)
    }
}
abstract class RestException(val status: HttpStatusCode) : RuntimeException() {
    abstract override fun toString(): String
}
open class NotFoundException : RestException(HttpStatusCode.NotFound) {
    override fun toString(): String = "Cannot find requested resource"
}

open class BadRequestException : RestException(HttpStatusCode.BadRequest) {
    override fun toString(): String = "Bad request parameters"
}

open class MissingUrlParamException(val param: String) : BadRequestException() {
    override fun toString(): String = "Missing $param"
}
open class ParamParseException(val param: String, val type: Class<Any>) : BadRequestException() {
    override fun toString(): String = "Cannot parse param $param to type $type"
}
open class IdNotFoundException(val idCarrier: String, val id: Int) : NotFoundException() {
    override fun toString(): String = "$idCarrier with specified id[$id] not found"
}