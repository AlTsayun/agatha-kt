package me.okko.agathakt.manager.repository

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class Image(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Image>(ImageTable)

    var link by ImageTable.link
}

class Actor(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Actor>(ActorTable)

    var firstName by ActorTable.firstName
    var lastName by ActorTable.lastName
    var email by ActorTable.email
    var passwordHash by ActorTable.passwordHash
    var avatar by Image optionalReferencedOn ActorTable.avatar

    var createdDate by ActorTable.createdDate
    var isActive by ActorTable.isActive
}

class SensorType(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SensorType>(SensorTypeTable)

    var name by SensorTypeTable.name
    var description by SensorTypeTable.description
    var externalLink by SensorTypeTable.externalLink
    var image by Image optionalReferencedOn SensorTypeTable.image

    var createdDate by SensorTypeTable.createdDate
    var isActive by SensorTypeTable.isActive
}

class Sensor(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Sensor>(SensorTable)

    var type by SensorType referencedOn SensorTable.type
    var url by SensorTable.url
    var medium by Medium referencedOn SensorTable.medium

    var createdDate by SensorTable.createdDate
    var isActive by SensorTable.isActive
}

class PluginType(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PluginType>(PluginTypeTable)

    var type by PluginTypeTable.type
    var name by PluginTypeTable.name
    var description by PluginTypeTable.description
    var sensorTypes by SensorType via PluginTypeToSensorTypeTable
    var externalLink by PluginTypeTable.externalLink
    var image by Image optionalReferencedOn PluginTypeTable.image

    var createdDate by PluginTypeTable.createdDate
    var isActive by PluginTypeTable.isActive
}

class Plugin(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Plugin>(PluginTable)

    var type by PluginType referencedOn PluginTable.type
    var medium by Medium referencedOn PluginTable.medium
    var isDashboardVisible by PluginTable.isDashboardVisible

    var createdDate by PluginTable.createdDate
    var isActive by PluginTable.isActive
}

class Medium(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Medium>(MediumTable)

    var name by MediumTable.name
    var description by MediumTable.description
    var url by MediumTable.url
    var actor by Actor referencedOn MediumTable.actor
    val sensors by Sensor referrersOn SensorTable.medium
    val plugins by Plugin referrersOn PluginTable.medium

    var createdDate by MediumTable.createdDate
    var isActive by MediumTable.isActive
}

fun insertDatabaseEntities() {

    val visitorsImage = transaction {
        Image.new {
            link = "https://i.postimg.cc/FsCXSfQv/image.png"
        }
    }


    // sensor types
    val pageLoadTimeSensorType = transaction {
        SensorType.new {
            name = "Page load time"
            description = "Detects how much time it takes for page to load."
            externalLink = null
            image = null

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }
    val visitTimeSensorType = transaction {
        SensorType.new {
            name = "Visit time"
            description = "Gathers info about duration of user visits."
            externalLink = null
            image = visitorsImage

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }
    val deviceSensorType = transaction {
        SensorType.new {
            name = "Device"
            description = "Collects device info which users tend to open your web page from."
            externalLink = null
            image = null

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }


    // plugin types
    val averagePageLoadTimePluginType = transaction {
        PluginType.new {
            type = "immediate"
            name = "Average page load time"
            description = "How much it takes to load a page in average."
            externalLink = null
            image = null

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }
    val declinesPluginType = transaction {
        PluginType.new {
            type = "chart"
            name = "Declines"
            description = "Depicts count of declines produced by period of time."
            externalLink = null
            image = null

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }
    val deviceTypePluginType = transaction {
        PluginType.new {
            type = "chart"
            name = "Device type"
            description = "Shows device types used to view tour web-page."
            externalLink = null
            image = null

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }
    val visitorsPluginType = transaction {
        PluginType.new {
            type = "chart"
            name = "Visitors"
            description = "Shows how many visitors attended you web page sectioned by periods."
            externalLink = null
            image = visitorsImage

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }

    transaction {
        averagePageLoadTimePluginType.sensorTypes = SizedCollection(pageLoadTimeSensorType)
        declinesPluginType.sensorTypes = SizedCollection(visitTimeSensorType)
        deviceTypePluginType.sensorTypes = SizedCollection(deviceSensorType)
        visitorsPluginType.sensorTypes = SizedCollection(visitTimeSensorType)
    }


    // actors
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


    // mediums
    val medium1 = transaction {
        Medium.new {
            actor = actor1
            name = "Example"
            description = "Example medium"
            url = "http://localhost:8090/"

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }


    // plugins
    val averagePageLoadTimePlugin = transaction {
        Plugin.new {
            type = averagePageLoadTimePluginType
            medium = medium1

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }
    val declinesPlugin = transaction {
        Plugin.new {
            type = declinesPluginType
            medium = medium1


            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }
    val deviceTypePlugin = transaction {
        Plugin.new {
            type = deviceTypePluginType
            medium = medium1


            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }
    val visitorsPlugin = transaction {
        Plugin.new {
            type = visitorsPluginType
            medium = medium1

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }


    // sensors
    val pageLoadTimeSensor = transaction {
        Sensor.new {
            url = "http://localhost:8081/" + pageLoadTimeSensorType.id.value
            type = pageLoadTimeSensorType
            medium = medium1

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }
    val visitTimeSensor = transaction {
        Sensor.new {
            url = "http://localhost:8081/" + visitTimeSensorType.id.value
            type = visitTimeSensorType
            medium = medium1

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }
    val deviceTypeSensor = transaction {
        Sensor.new {
            url = "http://localhost:8081/" + deviceSensorType.id.value
            type = deviceSensorType
            medium = medium1

            createdDate = LocalDate.of(2022, 4, 3)
            isActive = true
        }
    }
}