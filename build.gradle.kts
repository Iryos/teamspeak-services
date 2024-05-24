import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("com.google.cloud.tools.jib") version "3.4.2"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "2.0.0"
}

val projectVersion: String by extra

group = "com.iryos"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.2.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")
    implementation("com.github.theholywaffle:teamspeak3-api:1.3.1")
    developmentOnly("org.springframework.boot:spring-boot-devtools:3.3.0")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.2.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.5")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jib {
    from {
        image = "eclipse-temurin:17-jre-alpine"
    }
    to {
        image = "ghcr.io/iryos/teamspeak-services"
        tags = setOf("latest")
    }
}
