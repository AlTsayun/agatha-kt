package me.okko.agathakt.manager.ktor.plugins

import io.ktor.application.*
import me.okko.agathakt.manager.di.koinModule
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.KoinApplicationStarted
import org.koin.ktor.ext.KoinApplicationStopPreparing
import org.koin.ktor.ext.KoinApplicationStopped
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    environment.monitor.subscribe(KoinApplicationStarted) {
        log.info("Koin started.")
    }
    install(Koin) {
        slf4jLogger()
        modules(koinModule)
    }
    environment.monitor.subscribe(KoinApplicationStopPreparing) {
        log.info("Koin stopping...")
    }
    environment.monitor.subscribe(KoinApplicationStopped) {
        log.info("Koin stopped.")
    }
}