package me.okko.agathakt.manager.ktor

import io.ktor.application.*
import io.ktor.network.tls.certificates.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import me.okko.agathakt.manager.ktor.plugins.configureContentNegotiation
import me.okko.agathakt.manager.ktor.plugins.configureCors
import me.okko.agathakt.manager.ktor.plugins.configureKoin
import me.okko.agathakt.manager.ktor.plugins.configureRouting
import org.slf4j.LoggerFactory
import java.io.File

fun startKtorServer() {
    embeddedServer(Netty, configureEnvironment()).start()
}

fun Application.module() {
    configureContentNegotiation()
    configureCors()
    configureKoin()
    configureRouting()
}

fun configureEnvironment(): ApplicationEngineEnvironment {
    val keyStoreFile = File("build/keystore.jks")
    val keystore = generateCertificate(
        file = keyStoreFile,
        keyAlias = "sampleAlias",
        keyPassword = "password",
        jksPassword = "password"
    )

    return applicationEngineEnvironment {
        log = LoggerFactory.getLogger("ktor.application")
        connector {
            port = 8080
        }
        sslConnector(
            keyStore = keystore,
            keyAlias = "sampleAlias",
            keyStorePassword = { "password".toCharArray() },
            privateKeyPassword = { "password".toCharArray() }) {
            port = 8444
            keyStorePath = keyStoreFile
        }
        module(Application::module)
    }
}
