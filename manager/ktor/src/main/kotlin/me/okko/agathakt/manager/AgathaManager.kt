package me.okko.agathakt.manager

import me.okko.agathakt.manager.ktor.startKtorServer
import me.okko.agathakt.manager.repository.initializeDatabaseTables
import me.okko.agathakt.manager.repository.insertDatabaseEntities

fun main() {
    initializeDatabaseTables()
//    insertDatabaseEntities()
    startKtorServer()
}