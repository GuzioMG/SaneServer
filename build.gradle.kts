plugins {
    kotlin("jvm") version "2.3.0"
}

group = "hub.guzio.TrixnityTest"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("de.connect2x.trixnity:trixnity-bom:5.1.2"))

    implementation("de.connect2x.trixnity:trixnity-client")

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}