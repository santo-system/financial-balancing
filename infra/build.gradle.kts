plugins {
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

//tasks {
//    bootJar {
//        enabled = false
//    }
//}

dependencies {
    implementation(project(":domain"))

    // JPA
    implementation("org.springframework.data:spring-data-jpa:2.7.3")
    implementation("org.hibernate.validator:hibernate-validator:6.2.5.Final")
    implementation("org.hibernate:hibernate-core:5.6.11.Final")

    // Jakarta
    implementation("jakarta.validation:jakarta.validation-api:2.0.2")
    implementation("jakarta.annotation:jakarta.annotation-api:1.3.5")
    implementation("jakarta.persistence:jakarta.persistence-api:2.2.3")
    implementation("jakarta.transaction:jakarta.transaction-api:1.3.3")

    // DataBase
    implementation("org.flywaydb:flyway-core:9.3.1")
    runtimeOnly("com.h2database:h2:2.1.214")
    runtimeOnly("org.postgresql:postgresql:42.5.0")
}
