package clock

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class NormalClock : Clock {
    override fun now(): Instant {
        return Clock.System.now()
    }
}
