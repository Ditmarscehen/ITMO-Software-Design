package profiler;

public class Statistic {
    private long summaryTime;
    private int count;

    public Statistic() {
        this.summaryTime = 0;
        this.count = 0;
    }

    public void update(long time) {
        summaryTime += time;
        count++;
    }

    public long getSummaryTime() {
        return summaryTime;
    }

    public int getCount() {
        return count;
    }

    public long getAverageTime() {
        return summaryTime / count;
    }

    @Override
    public String toString() {
        return String.format("invoked: %s, summary time: %s, average time: %s", count, summaryTime, getAverageTime());
    }
}
