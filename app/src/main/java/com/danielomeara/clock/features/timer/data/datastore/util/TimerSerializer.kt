package com.danielomeara.clock.features.timer.data.datastore.util

import androidx.datastore.core.Serializer
import com.danielomeara.clock.features.timer.domain.models.Timer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object TimerSerializer: Serializer<Timer> {
    override val defaultValue: Timer
        get() = Timer()

    override suspend fun readFrom(input: InputStream): Timer {
        return try {
            Json.decodeFromString(
                deserializer = Timer.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: Timer, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = Timer.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}