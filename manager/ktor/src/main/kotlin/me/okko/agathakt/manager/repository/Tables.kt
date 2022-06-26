package me.okko.agathakt.manager.repository

import me.okko.agathakt.manager.repository.PluginTypeTable.default
import me.okko.agathakt.manager.repository.PluginTypeTable.nullable
import me.okko.agathakt.manager.repository.SensorTable.default
import me.okko.agathakt.manager.repository.SensorTable.nullable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction

object ImageTable : IntIdTable("image") {
    val link = varchar("link", 1024)
}

object ActorTable : IntIdTable("actor") {
    val firstName = varchar("first_name", 64)
    val lastName = varchar("last_name", 64)
    val email = varchar("email", 320)
    val passwordHash = varchar("password_hash", 60)
    val avatar = reference("avatar_image_id", ImageTable.id).nullable().default(null)

    val isActive = bool("is_active")
    val createdDate = date("created_date")
}

object MediumTable : IntIdTable("medium") {
    val actor = reference("actor_id", ActorTable.id)
    val name = varchar("name", 64)
    val description = varchar("description", 64).nullable().default(null)
    val url = varchar("url", 1024)
    val isActive = bool("is_active")
    val createdDate = date("created_date")
}

object PluginTypeTable : IntIdTable("plugin") {
    val type = varchar("type", 64)
    val name = varchar("name", 64)
    val description = varchar("description", 1024)
    val externalLink = varchar("external_link", 1024).nullable().default(null)
    val image = reference("image_id", ImageTable.id).nullable()
    val isActive = bool("is_active")
    val createdDate = date("created_date")
}

object PluginTable : IntIdTable("medium_to_plugin") {
    val type = reference("plugin_type_id", PluginTypeTable.id).uniqueIndex()
    val medium = reference("medium_id", MediumTable.id)
    val isDashboardVisible = bool("is_dashboard_visible").default(true)
    val isActive = bool("is_active")
    val createdDate = date("created_date")
}

object SensorTypeTable : IntIdTable("sensor_type") {
    val name = varchar("name", 64)
    val description = varchar("description", 1024)
    val externalLink = varchar("external_link", 1024).nullable().default(null)
    val image = reference("image_id", ImageTable.id).nullable().default(null)
    val isActive = bool("is_active")
    val createdDate = date("created_date")
}

object SensorTable : IntIdTable("sensor") {
    val type = reference("sensor_type_id", SensorTypeTable.id)
    val medium = reference("medium_id", MediumTable.id)
    val url = varchar("url", 1024)
    val isActive = bool("is_active")
    val createdDate = date("created_date")
}

object PluginTypeToSensorTypeTable : Table("plugin_type_to_sensor_type") {
    val pluginType = reference("plugin_type_id", PluginTypeTable.id)
    val sensorType = reference("sensor_type_id", SensorTypeTable.id)
    override val primaryKey = PrimaryKey(pluginType, sensorType, name = "PK_plugin_type_to_sensor_type")
}

fun initializeDatabaseTables() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/postgres",
        user = "postgres",
        password = "password",
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            ImageTable,
            ActorTable,
            MediumTable,
            PluginTypeTable,
            SensorTypeTable,
            PluginTable,
            SensorTable,
            PluginTypeToSensorTypeTable
        )
    }

}

