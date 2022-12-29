package com.iryos.teamspeakservices

import com.github.theholywaffle.teamspeak3.TS3Api
import com.github.theholywaffle.teamspeak3.TS3Config
import com.github.theholywaffle.teamspeak3.TS3Query
import com.iryos.teamspeakservices.services.OverwolfSpacer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

@Component
class Runner : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(Runner::class.java)

    @Autowired
    private val teamspeakProperties: TeamspeakProperties? = null

    @Autowired
    private val overwolfSpacer: OverwolfSpacer? = null

    override fun run(vararg args: String?) {
        logger.info("Checking Properties")
        if (
            teamspeakProperties?.host.isNullOrEmpty() ||
            teamspeakProperties?.queryUsername.isNullOrEmpty() ||
            teamspeakProperties?.queryPassword.isNullOrEmpty()
        ) {
            logger.error("Host / Query Username or Password NOT set!")
            exitProcess(1)
        }

        val config: TS3Config = TS3Config()
        config.setHost(teamspeakProperties?.host)
        config.setEnableCommunicationsLogging(true)

        val query = TS3Query(config)
        query.connect()

        val api: TS3Api = query.api
        api.login(teamspeakProperties?.queryUsername, teamspeakProperties?.queryPassword)
        api.selectVirtualServerById(1)
        api.setNickname(teamspeakProperties?.nickname)

        logger.info("Starting Overwolf Spacer Service!")
        overwolfSpacer?.init(api)
    }
}
