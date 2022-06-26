package me.okko.agathakt.manager.ktor.plugins

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import me.okko.agathakt.manager.plugin.PluginFactory
import me.okko.agathakt.manager.repository.Medium
import me.okko.agathakt.manager.service.ActorProvider
import me.okko.agathakt.manager.service.MediumProvider
import me.okko.agathakt.manager.service.PluginProvider
import me.okko.agathakt.manager.service.ScriptComposer
import me.okko.agathakt.manager.service.SensorDataLoader
import me.okko.agathakt.manager.service.SensorProvider
import me.okko.agathakt.manager.service.SensorTypeProvider
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.inject
import kotlin.io.path.exists

fun Application.configureRouting() {
    val pluginFactory: PluginFactory by inject()
    val sensorDataLoader: SensorDataLoader by inject()
    val scriptComposer: ScriptComposer by inject()
    val pluginProvider: PluginProvider by inject()
    val sensorTypeProvider: SensorTypeProvider by inject()
    val actorProvider: ActorProvider by inject()
    val sensorProvider: SensorProvider by inject()
    val mediumProvider: MediumProvider by inject()

    routing {

        route("/mediums") {
            get {
                call.respond(mediumProvider.getAllByActorIdOrNull(1)?: throw RuntimeException())
            }

            post {
                TODO("create new meduim")
            }

            route("/{mediumId}") {
                route("/plugins") {
                    get {
                        restGetByIntId("mediumId", "medium") { mediumId ->
                            pluginProvider.getAllByMediumIdOrNull(mediumId)
                        }
                    }
                    post {
                        TODO("add new plugin to medium")
                    }
                    get("/output/from/{from}/to/{to}") {
                        restErrors {
                            val mediumId = parseIntParam("mediumId")
                            val period = parseIntParam("from")..parseIntParam("to")

                            lateinit var medium: Medium
                            lateinit var sensorIdToUrl: Map<Int, String>
                            lateinit var pluginIds: List<Int>

                            transaction {
                                medium = (Medium.findById(mediumId)
                                    ?: throw IdNotFoundException(
                                        "medium",
                                        mediumId
                                    ))

                                sensorIdToUrl = medium.sensors.associate {
                                    it.type.id.value to it.url
                                }
                                pluginIds = medium.plugins.map { it.id.value }
                            }
                            val sensorTypeIdToData = sensorDataLoader.loadSensorData(sensorIdToUrl, period)

                            call.respond(
                                pluginIds.map {
                                    val plugin = pluginFactory.getByIdOrNull(it)
                                        ?: throw IdNotFoundException(
                                            "plugin",
                                            it
                                        )
                                    plugin.compute(sensorTypeIdToData, period)
                                }
                            )
                        }
                    }
                }

                route("/sensors") {
                    get("/") {
                        restGetByIntId("mediumId", "medium") { mediumId ->
                            sensorProvider.getAllByMediumIdOrNull(mediumId)
                        }
                    }
                    post("/") {
                        TODO("add new sensor for meduim")
                    }
                }

                get("/sensorTypes") {
                    restGetByIntId("mediumId", "medium") { mediumId ->
                        sensorTypeProvider.getAllByMediumIdOrNull(mediumId)
                    }
                }

                get("/script") {
                    restGetByIntId("mediumId", "medium") { mediumId ->
                        // construct reference to file
                        // ideally this would use a different filename
                        val file = scriptComposer.getByMedium(mediumId)
                        if (!file.exists()) {
                            throw NotFoundException();
                        }
                        call.respondFile(file.toFile())
                    }
                }
            }
        }

        route("/sensorTypes") {
            get("/{sensorTypeId}") {
                restGetByIntId("sensorTypeId", "sensorType") { id ->
                    sensorTypeProvider.getByIdOrNull(id)
                }
            }
        }

        route("/plugins") {
            get("/") {
                call.respond(pluginProvider.getAll())
            }
            get("/{pluginId}") {
                restGetByIntId("pluginId", "plugin") { id ->
                    pluginProvider.getByIdOrNull(id)
                }
            }
        }

        route("/actors") {
            route("/{actorId}") {
                get("/overview") {
                    restGetByIntId("actorId", "actor") { id ->
                        actorProvider.getByIdOrNull(id)
                    }
                }
            }
        }
    }
}