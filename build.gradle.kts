plugins {
    kotlin("jvm") version "2.0.21"
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

kotlin {
    jvmToolchain(25)
}

group = "vainius.particles-yay"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

javafx {
    version = "21.0.5"
    modules = listOf("javafx.controls", "javafx.graphics")
}

application {
    mainClass.set("com.example.MainKt")
}

tasks.test {
    useJUnitPlatform()
}