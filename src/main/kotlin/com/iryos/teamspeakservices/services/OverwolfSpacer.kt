package com.iryos.teamspeakservices.services

import com.github.theholywaffle.teamspeak3.TS3Api
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter
import com.iryos.teamspeakservices.TeamspeakProperties
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class OverwolfSpacer() {
    @Autowired
    private val teamspeakProperties: TeamspeakProperties? = null

    private val logger = LoggerFactory.getLogger(this.javaClass)
    private var ts3Api: TS3Api? = null

    fun init(api: TS3Api) {
        ts3Api = api
        var spacerGroupId: Int = 0

        ts3Api?.serverGroups?.forEach {
            if (it.name == "Overwolf Spacer") {
                spacerGroupId = it.id
            }
        }

        val eventAdapter =
            object : TS3EventAdapter() {
                override fun onClientJoin(event: ClientJoinEvent) {
                    if (event.clientNickname != "serveradmin" || event.clientNickname != "Unknown") {
                        try {
                            ts3Api?.addClientToServerGroup(spacerGroupId, event.clientDatabaseId)
                        } catch (e: Exception) {
                            if (!e.toString().contains("duplicate entry") && !e.toString().contains("invalid clientID")) {
                                logger.error("User: ${event.clientNickname} already in Group 'Overwolf Spacer'")
                                logger.error(e.toString())
                            }
                        }
                    }
                }
            }
        ts3Api?.registerAllEvents()
        ts3Api?.addTS3Listeners(eventAdapter)
    }

    @Scheduled(fixedDelayString = "\${overwolf.checkInterval}" + "000")
    fun scheduledTask() {
        logger.info("Check all clients if group (Overwolf Spacer) is set correctly!")
        var spacerGroupId: Int = 0

        ts3Api?.serverGroups?.forEach {
            if (it.name == "Overwolf Spacer") {
                spacerGroupId = it.id
            }
        }

        val currentClients = ts3Api?.clients

        currentClients?.forEach {
            if (!it.get("client_badges").contains("Overwolf=1")) {
                if (it.nickname != teamspeakProperties?.nickname || it.nickname.toString().contains("Unknown")) {
                    logger.debug(it.nickname)
                    try {
                        ts3Api?.addClientToServerGroup(spacerGroupId, it.databaseId)
                    } catch (e: Exception) {
                        if (!e.toString().contains("duplicate entry") && !e.toString().contains("invalid clientID")) {
                            logger.error("User: ${it.nickname} already in Group 'Overwolf Spacer'")
                            logger.error(e.toString())
                        }
                    }
                }
            } else {
                if (it.nickname != teamspeakProperties?.nickname || it.nickname.toString().contains("Unknown")) {
                    logger.debug(it.nickname)
                    try {
                        ts3Api?.removeClientFromServerGroup(spacerGroupId, it.databaseId)
                    } catch (e: Exception) {
                        if (!e.toString().contains("empty result set") && !e.toString().contains("invalid clientID")) {
                            logger.error("Cannot remove User: ${it.nickname} from Group 'Overwolf Spacer'")
                            logger.error(e.toString())
                        }
                    }
                }
            }
        }
    }
}
