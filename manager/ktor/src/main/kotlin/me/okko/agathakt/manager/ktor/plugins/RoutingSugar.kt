package me.okko.agathakt.manager.ktor.plugins

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

fun PipelineContext<Unit, ApplicationCall>.parseIntParam(param: String) =
    try {
        getParam(param).toInt()
    } catch (e: NumberFormatException) {
        throw ParamParseException(param, Int.javaClass)
    }

fun PipelineContext<Unit, ApplicationCall>.getParam(param: String) =
    call.parameters[param]?: throw MissingUrlParamException(param)

//suspend inline fun <reified T> PipelineContext<Unit, ApplicationCall>.restAuthorization(
//    idField: String,
//    idCarrier: String,
//    body: PipelineContext<Unit, ApplicationCall>.(Int) -> T?
//) {
//    restErrors {
//        val id = parseIntParam(idField)
//        val item = body.invoke(this, id) ?: throw IdNotFoundException(idCarrier, id)
//        call.respond(item)
//    }
//}
suspend inline fun <reified T> PipelineContext<Unit, ApplicationCall>.restGetByIntId(
    idField: String,
    idCarrier: String,
    body: PipelineContext<Unit, ApplicationCall>.(Int) -> T?
) {
    restErrors {
        val id = parseIntParam(idField)
        val item = body.invoke(this, id) ?: throw IdNotFoundException(idCarrier, id)
        call.respond(item)
    }
}
suspend inline fun PipelineContext<Unit, ApplicationCall>.restErrors(
    body: PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit
) {
    try {
        body.invoke(this, subject)
    } catch (e: RestException) {
        println(e)
        call.respondText(text = e.toString(), status = e.status)
    }
}

abstract class RestException(val status: HttpStatusCode) : RuntimeException() {
    abstract override fun toString(): String
}

open class UnauthorizedException : RestException(HttpStatusCode.Unauthorized) {
    override fun toString(): String = "User unauthorized"
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