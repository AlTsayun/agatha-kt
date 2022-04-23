
/**
 * To define plugins
 */
object BuildPlugins {
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
}

/**
 * To define dependencies
 */
object Deps {
    val coroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}" }


    val ktorServer by lazy { "io.ktor:ktor-server-core:${Versions.ktor}" }
    val ktorServerNetty by lazy { "io.ktor:ktor-server-netty:${Versions.ktor}" }
    val ktorServerTls by lazy { "io.ktor:ktor-network-tls-certificates:${Versions.ktor}" }

    val ktorClient by lazy { "io.ktor:ktor-client-core:${Versions.ktor}" }
    val ktorClientCio by lazy { "io.ktor:ktor-client-cio:${Versions.ktor}" }
    val ktorClientSerialization by lazy { "io.ktor:ktor-client-serialization:${Versions.ktor}" }
    val ktorClientGson by lazy { "io.ktor:ktor-client-gson:${Versions.ktor}" }

    val ktorSerialization by lazy { "io.ktor:ktor-serialization:${Versions.ktor}" }

    val koinKtor by lazy { "io.insert-koin:koin-ktor:${Versions.koin}" }
    val koin by lazy { "io.insert-koin:koin-core:${Versions.koin}" }
    val koinLogger by lazy { "io.insert-koin:koin-logger-slf4j:${Versions.koin}" }

    val exposed by lazy { "org.jetbrains.exposed:exposed-core:${Versions.exposed}" }
    val exposedDao by lazy { "org.jetbrains.exposed:exposed-dao:${Versions.exposed}" }
    val exposedJdbc by lazy { "org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}" }
    val exposedJavaTime by lazy { "org.jetbrains.exposed:exposed-java-time:${Versions.exposed}" }

    val postgresql by lazy { "org.postgresql:postgresql:${Versions.postgres}" }

    val logback by lazy { "ch.qos.logback:logback-classic:${Versions.logback}" }
}