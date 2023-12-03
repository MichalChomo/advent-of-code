plugins {
    kotlin("jvm") version "1.9.21"
}

dependencies {
    // Other dependencies.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")
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
