plugins {
    kotlin("jvm") version "1.9.21"
}

dependencies {
    // Other dependencies.
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.21")
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }

    test {
        useJUnitPlatform()
    }

    register<AddDayTask>("addDay")

}
