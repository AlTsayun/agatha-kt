package me.okko.agatha.manager.repository

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

object PluginSupplierTable: IntIdTable("plugin_supplier") {
    val actor = reference("actor_id", ActorTable.id)
    val isActive = bool("is_active")
    val createdDate = date("created_date")
}

object PluginTable: IntIdTable("plugin") {
//    val supplier = reference("supplier_id", PluginSupplierTable.id)
    val name = varchar("name", 64)
    val isActive = bool("is_active")
    val createdDate = date("created_date")
}

object MeduimToPluginTable: Table("meduim_to_plugin") {
    val meduim = reference("meduim_id", MeduimTable.id)
    val plugin = reference("plugin_id", PluginTable.id)
    override val primaryKey = PrimaryKey(meduim, plugin, name = "PK_meduim_to_plugin")
}

object SensorSupplierTable: IntIdTable("sensor_supplier") {
    val actor = reference("actor_id", ActorTable.id)
    val isActive = bool("is_active")
    val createdDate = date("created_date")
}

object SensorTable: IntIdTable("sensor") {
//    val supplier = reference("supplier_id", SensorSupplierTable.id)
    val name = varchar("name", 64)
    val isActive = bool("is_active")
    val createdDate = date("created_date")
}

object PluginToSensorTable: Table("plugin_to_sensor") {
    val plugin = reference("plugin_id", PluginTable.id)
    val sensor = reference("sensor_id", SensorTable.id)
    override val primaryKey = PrimaryKey(plugin, sensor, name = "PK_plugin_to_sensor")
}

fun initializeDatabaseTables() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/postgres",
        user = "postgres",
        password = "password",
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            PluginTable,
            SensorTable,
            PluginToSensorTable
        )
    }

}

