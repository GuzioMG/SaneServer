package hub.guzio.TrixnityTest

import io.ktor.http.*
import de.connect2x.trixnity.clientserverapi.client.MatrixClientServerApiClientImpl
import de.connect2x.trixnity.clientserverapi.model.appservice.Ping
import de.connect2x.trixnity.core.model.EventId
import de.connect2x.trixnity.core.model.UserId
import de.connect2x.trixnity.core.model.events.ClientEvent
import de.connect2x.trixnity.core.model.events.m.room.RoomMessageEventContent.TextBased.Text
import de.connect2x.trixnity.core.subscribeEvent
import kotlinx.coroutines.runBlocking

fun main() {
    val client = MatrixClientServerApiClientImpl(
        baseUrl = Url("https://api.chat.guziohub.ovh"),
        asUserId = UserId("_trixnitytest", "guziohub.ovh")
    )

    client.sync.subscribeEvent<Text, ClientEvent.RoomEvent<Text>> { event ->
        val body = event.content.body
        when {
            body.startsWith("ping") -> {
                println("Event: "+client.room.sendMessageEvent(event.roomId, Text(body = "pong")).getOrElse{ e ->
                    println(e)
                    e.printStackTrace()
                    return@getOrElse EventId("ERR")
                })
            }
        }
    }

    runBlocking {
        println("Pinged after: "+client.appservice.ping("trixnitytest").getOrElse{ e ->
            println(e.message)
            e.printStackTrace()
            return@getOrElse Ping.Response(durationMs = 0)
        }.durationMs)
    }
}