plugins {
    application
    id("agatha.kt-conventions")
}

application {
    mainClass.set("me.okko.agathakt.manager.AgathaManagerKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {

    implementation(project(":common-kt"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")


    implementation("io.ktor:ktor-server-core:${Versions.ktor}")
    implementation("io.ktor:ktor-server-netty:${Versions.ktor}")
    implementation("io.ktor:ktor-auth:${Versions.ktor}")
    implementation("io.ktor:ktor-network-tls-certificates:${Versions.ktor}")

    implementation("io.ktor:ktor-client-core:${Versions.ktor}")
    implementation("io.ktor:ktor-client-cio:${Versions.ktor}")
    implementation("io.ktor:ktor-client-serialization:${Versions.ktor}")
    implementation("io.ktor:ktor-client-gson:${Versions.ktor}")

    implementation("io.ktor:ktor-serialization:${Versions.ktor}")

    implementation("io.insert-koin:koin-ktor:${Versions.koin}")
    implementation("io.insert-koin:koin-core:${Versions.koin}")
    implementation("io.insert-koin:koin-logger-slf4j:${Versions.koin}")

    implementation("org.jetbrains.exposed:exposed-core:${Versions.exposed}")
    implementation("org.jetbrains.exposed:exposed-dao:${Versions.exposed}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}")
    implementation("org.jetbrains.exposed:exposed-java-time:${Versions.exposed}")

    implementation("org.postgresql:postgresql:${Versions.postgres}")

    implementation("ch.qos.logback:logback-classic:${Versions.logback}")

//    testImplementation("io.ktor:ktor-server-tests:${Versions.ktor}")
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}")
}