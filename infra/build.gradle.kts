plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
//    id("org.flywaydb.flyway") version "9.3.1"

    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

tasks {
    bootJar {
        enabled = false
    }
}

//apply(plugin = "org.flywaydb.flyway")

//flyway {
//    url = "jdbc:postgresql://localhost:5432/santosystem?currentSchema=financial-balancing"
//    user = "financial"
//    password = "financial"
//    driver = "org.postgresql.Driver"
//}

dependencies {
    implementation(project(":domain"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // DataBase
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
}
