package me.okko.agathakt.manager.ktor.plugins

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import me.okko.agathakt.manager.plugin.PluginFactory
import me.okko.agathakt.manager.plugin.model.ChartOutput
import me.okko.agathakt.manager.plugin.model.chart.Chart
import me.okko.agathakt.manager.plugin.model.chart.ChartData
import me.okko.agathakt.manager.plugin.model.chart.ChartOutput
import me.okko.agathakt.manager.plugin.model.chart.ChartType
import me.okko.agathakt.manager.plugin.model.chart.Dataset
import me.okko.agathakt.manager.plugin.model.chart.Model
import me.okko.agathakt.manager.plugin.model.chart.PieDataset
import me.okko.agathakt.manager.service.ActorProvider
import me.okko.agathakt.manager.service.MediumProvider
import me.okko.agathakt.manager.service.PluginProvider
import me.okko.agathakt.manager.service.ScriptComposer
import me.okko.agathakt.manager.service.SensorDataLoader
import me.okko.agathakt.manager.service.SensorProvider
import me.okko.agathakt.manager.service.SensorTypeProvider
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
        get("/meduimOutput/{meduimId}") {
            restErrors {
                val meduimId = parseIntParam("meduimId")

                val sensorTypeIdToData =
                    sensorDataLoader.loadSensorDataForMeduimByIdOrNull(meduimId) ?: throw IdNotFoundException(
                        "meduim",
                        meduimId
                    )

                val processedValue = pluginFactory.getAllForMeduimById(1)
                    .map {
                        it.computeFromSensorData(sensorTypeIdToData)
                    }
                call.respond(processedValue)
            }
        }

        get("/mediums") {
            call.respond(Model(11))
        }

        route("/mediums") {
//            get {
////                restErrors {
//                    // TODO: get actorId from authorization
////                    val actorId = 0
//                    call.respond(
////                        mediumProvider.getAllByActorIdOrNull(actorId) ?: TODO("wtf you logged in as non-existent actor")
//                        Model(11)
//                    )
////                }
//            }

            post("/") {
                TODO("create new meduim")
            }

            route("/{mediumId}") {
                route("/plugins") {
                    get("/") {
                        restGetByIntId("mediumId", "medium") { id ->
                            pluginProvider.getAllByMediumIdOrNull(id)
                        }
                    }
                    post("/") {
                        TODO("add new plugin to medium")
                    }
                    get("/{pluginId}/output") {
                        // output contains chart data

                        restErrors {
                            val meduimId = parseIntParam("meduimId")

                            val sensorTypeIdToData = sensorDataLoader.loadSensorDataForMeduimByIdOrNull(meduimId)
                                ?: throw IdNotFoundException(
                                    "meduim",
                                    meduimId
                                )

                            val pluginId = parseIntParam("pluginId")


                            val processedValue = pluginFactory.getAllForMeduimById(1)
                                .map {
                                    it.computeFromSensorData(sensorTypeIdToData)
                                }
                            call.respond(processedValue)
                        }

                        call.respond(
                            ChartOutput(
                                "name",
                                Chart(
                                    ChartType.pie,
                                    ChartData(
                                        listOf("Red", "Blue", "Yellow"),
                                        listOf(
                                            PieDataset(
                                                listOf(300, 50, 100),
                                                listOf(
                                                    "rgb(255, 99, 132)",
                                                    "rgb(54, 162, 235)",
                                                    "rgb(255, 205, 86)"
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )

//                        TODO("get specific plugin output for meduim")
                    }
                }

                route("/sensors") {
                    get("/") {
                        restGetByIntId("mediumId", "medium") { id ->
                            sensorProvider.getAllByMediumIdOrNull(id)
                        }
                    }
                    post("/") {
                        TODO("add new sensor for meduim")
                    }
                }

                get("/sensorTypes") {
                    restGetByIntId("mediumId", "medium") { id ->
                        sensorTypeProvider.getAllByMediumIdOrNull(id)
                    }
                }

                get("/script") {
                    restErrors {
                        val meduimId = parseIntParam("mediumId")
                        // construct reference to file
                        // ideally this would use a different filename
                        val file = scriptComposer.get(meduimId)
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