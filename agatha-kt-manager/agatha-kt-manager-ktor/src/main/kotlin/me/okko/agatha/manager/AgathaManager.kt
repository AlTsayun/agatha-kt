package me.okko.agatha.manager

import me.okko.agatha.manager.ktor.startKtorServer
import me.okko.agatha.manager.repository.initializeDatabaseTables
import me.okko.agatha.manager.repository.insertDatabaseEntities

fun main() {
    initializeDatabaseTables()
//    insertDatabaseEntities()
    startKtorServer()
}