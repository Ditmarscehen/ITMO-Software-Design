package clock

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration


class SettableClock(private var now: Instant) : Clock {
    override fun now(): Instant {
        return now
    }

    fun set(now: Instant) {
        this.now = now
    }

    fun increment(duration: Duration){
        this.now += duration
    }
}
