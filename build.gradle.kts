import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val logbackVersion: String by project

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    kotlin("jvm") version "1.7.10" apply false
    java
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

allprojects {
    group = "com.santosystem"
    version = "1.0.0"

    apply(plugin = "java")

    tasks.withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_11.toString()
        targetCompatibility = JavaVersion.VERSION_11.toString()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }
}

subprojects {
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
    }

    dependencies {
        // Kotlin
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))

        // Logger
        implementation("ch.qos.logback:logback-classic:$logbackVersion")

        // Gradle env
        implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")

        // Testing
        testImplementation("io.mockk:mockk:1.13.2")
        testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    }

    tasks.test {
        useJUnitPlatform()
    }
}
