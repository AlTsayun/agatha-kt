import org.gradle.kotlin.dsl.`kotlin-dsl`

val kotlinVersion: String by project

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-serialization:${kotlinVersion}")
}
