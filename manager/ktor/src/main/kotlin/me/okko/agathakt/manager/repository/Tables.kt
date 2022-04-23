package me.okko.agathakt.manager.repository

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction

object ActorTable: IntIdTable("actor") {
    val firstName = varchar("first_name", 64)
    val lastName = varchar("last_name", 64)
    val email = varchar("email", 320)
    val passwordHash = varchar("password_hash", 60)
    val isActive = bool("is_active")
    val createdDate = date("created_date")
}

object MeduimTable: IntIdTable("meduim") {
    val actor = reference("actor_id", ActorTable.id)
    val name = varchar("name", 64)
    val isActive = bool("is_active")
    val createdDate = date("created_date")
}

object PluginTable: IntIdTable("plugin") {
    val name = varchar("name", 64)
    val isActive = bool("is_active")
    val createdDate = date("created_date")
}

object MeduimToPluginTable: Table("meduim_to_plugin") {
    val meduim = reference("meduim_id", MeduimTable.id)
    val plugin = reference("plugin_id", PluginTable.id)
    override val primaryKey = PrimaryKey(meduim, plugin, name = "PK_meduim_to_plugin")
}

object MeduimToSensorTable: Table("meduim_to_sensor") {
    val meduim = reference("meduim_id", MeduimTable.id)
    val sensor = reference("sensor_id", SensorTable.id)
    override val primaryKey = PrimaryKey(meduim, sensor, name = "PK_meduim_to_sensor")
}

object SensorTypeTable: IntIdTable("sensor_type") {
    val name = varchar("name", 64)
    val isActive = bool("is_active")
    val createdDate = date("created_date")
}

object SensorTable: IntIdTable("sensor") {
    val type = reference("sensor_type_id", SensorTypeTable.id)
    val url = varchar("url", 1024)
    val isActive = bool("is_active")
    val createdDate = date("created_date")
}

object PluginToSensorTypeTable: Table("plugin_to_sensor_type") {
    val plugin = reference("plugin_id", PluginTable.id)
    val sensorType = reference("sensor_type_id", SensorTypeTable.id)
    override val primaryKey = PrimaryKey(plugin, sensorType, name = "PK_plugin_to_sensor_type")
}

fun initializeDatabaseTables() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/postgres",
        user = "postgres",
        password = "password",
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            ActorTable,
            MeduimTable,
            PluginTable,
            MeduimToPluginTable,
            MeduimToSensorTable,
            SensorTypeTable,
            SensorTable,
            PluginToSensorTypeTable
        )
    }

}

