package me.okko.agathakt.manager.repository

import me.okko.agathakt.manager.repository.MeduimTable.actor
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class Actor(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Actor>(ActorTable)

    var firstName by ActorTable.firstName
    var lastName by ActorTable.lastName
    var email by ActorTable.email
    var passwordHash by ActorTable.passwordHash

    var createdDate by ActorTable.createdDate
    var isActive by ActorTable.isActive
}

class SensorType(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SensorType>(SensorTypeTable)

    var name by SensorTypeTable.name

    var createdDate by SensorTypeTable.createdDate
    var isActive by SensorTypeTable.isActive
}

class Sensor(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Sensor>(SensorTable)

    var url by SensorTable.url
    var type by SensorType referencedOn SensorTable.type

    var createdDate by SensorTable.createdDate
    var isActive by SensorTable.isActive
}

class Plugin(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Plugin>(PluginTable)

    var name by PluginTable.name
    var sensorTypes by SensorType via PluginToSensorTypeTable

    var createdDate by PluginTable.createdDate
    var isActive by PluginTable.isActive
}

class Meduim(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Meduim>(MeduimTable)

    var name by MeduimTable.name
    var actor by Actor referencedOn MeduimTable.actor
    var sensors by Sensor via MeduimToSensorTable
    var plugins by Plugin via MeduimToPluginTable

    var createdDate by MeduimTable.createdDate
    var isActive by MeduimTable.isActive
}

fun insertDatabaseEntities() {

    val sensorType1 = transaction {
        SensorType.new {
            name = "sensorType1"

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }

    val sensor1 = transaction {
        Sensor.new {
            url = "http://localhost:8081/" + sensorType1.id.value
            type = sensorType1

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }

    val plugin1 = transaction {
        Plugin.new {
            name = "plugin1"

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }

    transaction {
        plugin1.sensorTypes = SizedCollection(sensorType1)
    }

    val actor1 = transaction {
        Actor.new {
            firstName = "firstName"
            lastName = "lastName"
            email = "example@mail.com"
            passwordHash = "password"

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }

    val medium1 = transaction {
        Meduim.new {
            actor = actor1
            name = "meduim1"

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }
    transaction {
        medium1.sensors = SizedCollection(sensor1)
        medium1.plugins = SizedCollection(plugin1)
    }
}