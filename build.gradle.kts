plugins {
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jlleitschuh.gradle.ktlint") version "12.2.0"
    id("com.google.cloud.tools.jib") version "3.4.5"
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.spring") version "2.1.20"
}

val projectVersion: String by extra

group = "com.iryos"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.4.4")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.1.20")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.20")
    implementation("com.github.theholywaffle:teamspeak3-api:1.3.1")
    developmentOnly("org.springframework.boot:spring-boot-devtools:3.4.4")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.4.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.5")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
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

ktlint {
    version.set("1.5.0")
}
