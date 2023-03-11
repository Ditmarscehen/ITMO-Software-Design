import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class EventStatisticImpl(private val clock: Clock) : EventStatistic {
    companion object {
        private val STORED_TIME = 1.toDuration(DurationUnit.HOURS)
    }

    private val statistic: MutableMap<String, MutableList<Instant>> = HashMap()
    override fun incEvent(name: String) {
        statistic.putIfAbsent(name, ArrayList())
        statistic[name]?.add(clock.now())
    }

    override fun getEventStatisticByName(name: String): Double {
        return getEventStatisticCountByName(name) / 60.0
    }

    private fun getEventStatisticCountByName(name: String): Int {
        val stat = statistic[name] ?: return 0
        val freshStat = removeOutdated(stat)
        statistic[name] = freshStat.toMutableList()

        return freshStat.size
    }

    override fun getAllEventStatistic(): Double {
        return statistic.map { getEventStatisticCountByName(it.key) }.sum() / 60.0
    }

    override fun printStatistic() {
        println(this.toString())
    }

    private fun removeOutdated(stat: List<Instant>): List<Instant> {
        val pos = stat.binarySearch(clock.now().minus(STORED_TIME))
        val removeFrom = if (pos < 0) -(pos + 1) else pos

        return stat.subList(removeFrom, stat.size)
    }

    override fun toString(): String {
        return statistic.keys.joinToString("\n") { "$it: ${getEventStatisticByName(it)}" }
    }
}
