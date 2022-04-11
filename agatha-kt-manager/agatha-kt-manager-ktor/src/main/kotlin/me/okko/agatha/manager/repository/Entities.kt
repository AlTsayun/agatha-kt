package me.okko.agatha.manager.repository

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class Sensor(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Sensor>(SensorTable)

    var name by SensorTable.name
    var createdDate by SensorTable.createdDate
    var isActive by SensorTable.isActive
}

class Plugin(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Plugin>(PluginTable)

    var name by PluginTable.name
    var sensors by Sensor via PluginToSensorTable

    var createdDate by PluginTable.createdDate
    var isActive by PluginTable.isActive
}

fun insertDatabaseEntities() {

    val sensor1 = transaction {
        Sensor.new {
            name = "sensor1"
            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }

    val plugin = transaction {
        Plugin.new {
            name = "plugin1"
            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }
    transaction {
        plugin.sensors = SizedCollection(sensor1)
    }
}