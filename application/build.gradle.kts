plugins {
    id("application")
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"

    kotlin("plugin.spring") version "1.6.21"
}

application {
    mainClass.value("com.santosystem.financial.balancing.ApplicationKt")
}

tasks {
    jar {
        enabled = false
    }
}

extra["springCloudVersion"] = "2021.0.4"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":infra"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Kotlin Jackson Spring Integration
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // OpenFeign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    // OpenAPI 3
    implementation("org.springdoc:springdoc-openapi-data-rest:1.6.11")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.11")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.11")
    implementation("org.springdoc:springdoc-openapi-core:1.1.49")

    // Logger
    implementation("org.zalando:logbook-spring-boot-starter:2.14.0")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")

//    testImplementation("io.mockk:mockk:1.12.3")
//    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}
