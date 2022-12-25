import clock.SettableClock
import kotlinx.datetime.Clock
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class TestEventStatistic {
    @Test
    fun testOneEvent() {
        val instant = Clock.System.now()
        val clock = SettableClock(instant)
        val eventStatistic = EventStatisticImpl(clock)
        val eventName = "event1"

        eventStatistic.incEvent(eventName)

        assertEquals(1.0 / 60.0, eventStatistic.getEventStatisticByName(eventName))
        assertEquals(1.0 / 60.0, eventStatistic.getAllEventStatistic())
    }

    @Test
    fun testSeveralEvents() {
        val instant = Clock.System.now()
        val clock = SettableClock(instant)
        val eventStatistic = EventStatisticImpl(clock)
        val eventName1 = "event1"
        val eventName2 = "event2"
        val eventName3 = "event3"

        eventStatistic.incEvent(eventName1)
        eventStatistic.incEvent(eventName2)
        eventStatistic.incEvent(eventName3)

        assertEquals(1.0 / 60.0, eventStatistic.getEventStatisticByName(eventName1))
        assertEquals(1.0 / 60.0, eventStatistic.getEventStatisticByName(eventName2))
        assertEquals(1.0 / 60.0, eventStatistic.getEventStatisticByName(eventName3))

        assertEquals(3.0 / 60.0, eventStatistic.getAllEventStatistic())
    }

    @Test
    fun testNotIncludeOldEvent() {
        val instant = Clock.System.now()
        val clock = SettableClock(instant)
        val eventStatistic = EventStatisticImpl(clock)
        val eventName = "event1"

        eventStatistic.incEvent(eventName)
        clock.increment(5.toDuration(DurationUnit.MINUTES))

        eventStatistic.incEvent(eventName)
        clock.increment(30.toDuration(DurationUnit.MINUTES))

        eventStatistic.incEvent(eventName)
        clock.increment(28.toDuration(DurationUnit.MINUTES))

        eventStatistic.incEvent(eventName)

        assertEquals(3.0 / 60.0, eventStatistic.getEventStatisticByName(eventName))
        assertEquals(3.0 / 60.0, eventStatistic.getAllEventStatistic())
    }

    @Test
    fun testAll() {
        val instant = Clock.System.now()
        val clock = SettableClock(instant)
        val eventStatistic = EventStatisticImpl(clock)
        val eventName1 = "event1"
        val eventName2 = "event2"
        val eventName3 = "event3"

        eventStatistic.incEvent(eventName1)
        eventStatistic.incEvent(eventName2)
        eventStatistic.incEvent(eventName3)
        clock.increment(10.toDuration(DurationUnit.MINUTES))


        eventStatistic.incEvent(eventName2)
        clock.increment(10.toDuration(DurationUnit.MINUTES))

        eventStatistic.incEvent(eventName3)
        clock.increment(10.toDuration(DurationUnit.MINUTES))

        eventStatistic.incEvent(eventName3)
        clock.increment(10.toDuration(DurationUnit.MINUTES))

        eventStatistic.incEvent(eventName2)
        clock.increment(10.toDuration(DurationUnit.MINUTES))

        eventStatistic.incEvent(eventName1)
        clock.increment(10.toDuration(DurationUnit.MINUTES))

        eventStatistic.incEvent(eventName2)
        clock.increment(10.toDuration(DurationUnit.MINUTES))

        assertEquals(1.0 / 60.0, eventStatistic.getEventStatisticByName(eventName1))
        assertEquals(3.0 / 60.0, eventStatistic.getEventStatisticByName(eventName2))
        assertEquals(2.0 / 60.0, eventStatistic.getEventStatisticByName(eventName3))

        assertEquals(6.0 / 60.0, eventStatistic.getAllEventStatistic())
    }
}