plugins {
    application //to run JVM part
    id("agatha.kt-conventions")
}

dependencies {
    implementation(project(":common-kt"))
    implementation("io.ktor:ktor-server-core:${Versions.ktor}")
    implementation("io.ktor:ktor-server-netty:${Versions.ktor}")

    implementation("io.ktor:ktor-serialization:${Versions.ktor}")
    implementation("ch.qos.logback:logback-classic:${Versions.logback}")
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:${Versions.kmongo}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serializationJson}")
//    testImplementation("io.ktor:ktor-server-tests-jvm:${Versions.ktor}")
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}")
}