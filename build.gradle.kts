import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val logbackVersion: String by project

plugins {
    kotlin("jvm") version "1.7.10"
}

allprojects {
    group = "com.santosystem"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    apply(plugin = "kotlin")

    java.sourceCompatibility = JavaVersion.VERSION_11
    java.targetCompatibility = JavaVersion.VERSION_11

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }
}

subprojects {
    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))

        implementation("ch.qos.logback:logback-classic:$logbackVersion")

        // Gradle env
        implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")

        testImplementation(kotlin("test"))
    }

    tasks.test {
        useJUnitPlatform()
    }
}
