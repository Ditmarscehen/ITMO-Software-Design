package utils

import java.time.Instant
import java.time.temporal.ChronoUnit

class TimeUtils {
    companion object {
        fun calculateTimeBefore(time: Int) =
            Instant.now().minus(time.toLong(), ChronoUnit.HOURS).epochSecond.toString()
    }
}