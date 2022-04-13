package com.danielomeara.clock.features.timer.domain.models

data class TimerText(
    val hours: String,
    val minutes: String,
    val seconds: String
) {
    fun mapToTimer(): Timer {
        return Timer(
            hours.toLong(),
            minutes.toLong(),
            seconds.toLong()
        )
    }
}

data class Timer(
    val hours: Long,
    val minutes: Long,
    val seconds: Long
) {
    fun mapToTimerText(): TimerText {
        val hoursString = hours.toString()
        val minutesString = minutes.toString()
        val secondsString = seconds.toString()
        return TimerText(
            hours = if(hoursString.length == 2) hoursString else "0$hoursString",
            minutes = if(minutesString.length == 2) minutesString else "0$minutesString",
            seconds = if (secondsString.length == 2) secondsString else "0$secondsString"
        )
    }

    fun getSecondsRemaining(): Long {
        return (hours * 3600) + (minutes * 60) + (seconds)
    }
}