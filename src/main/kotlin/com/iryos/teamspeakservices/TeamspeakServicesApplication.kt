package com.iryos.teamspeakservices

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableConfigurationProperties(
    TeamspeakProperties::class
)
@EnableScheduling
@SpringBootApplication
class TeamspeakServicesApplication

fun main(args: Array<String>) {
    runApplication<TeamspeakServicesApplication>(*args)
}

@ConfigurationProperties("teamspeak")
data class TeamspeakProperties(val host: String, val queryUsername: String, val queryPassword: String, val nickname: String)
