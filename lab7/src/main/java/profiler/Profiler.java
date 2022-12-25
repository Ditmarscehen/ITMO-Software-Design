package profiler;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Profiler {
    private static Profiler instant;
    private final Map<String, Statistic> stat;
    private final Predicate<String> packageNamePredicate;

    private Profiler(String packageName) {
        if (!PackageHelper.isValidPackageName(packageName)) {
            throw new IllegalArgumentException("Invalid pattern name");
        }
        this.packageNamePredicate = PackageHelper.getPackageNamePattern(packageName).asPredicate();
        this.stat = new HashMap<>();
    }

    public static Profiler getInstant() {
        if (instant == null) {
            throw new RuntimeException("Profiler was not setup");
        }

        return instant;
    }

    public static void setupNew(String packageName) {
        instant = new Profiler(packageName);
    }

    public void profile(ProceedingJoinPoint joinPoint, long spentTime) {
        if (!needProfile(joinPoint)) {
            return;
        }

        String methodName = getMethodName(joinPoint);
        stat.putIfAbsent(methodName, new Statistic());
        stat.get(methodName).update(spentTime);
    }

    public int getMethodCount(String methodName) {
        Statistic statistic = stat.get(methodName);
        if (statistic == null) {
            return 0;
        }

        return statistic.getCount();
    }

    public String getStatString() {
        return stat.entrySet().stream()
                .map((entry) -> Profiler.statToString(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    private static String statToString(String methodName, Statistic statistic) {
        return String.format("%s: {%s}", methodName, statistic.toString());
    }

    private String getMethodName(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().toLongString();
    }

    private boolean needProfile(ProceedingJoinPoint joinPoint) {
        return packageNamePredicate.test(PackageHelper.getPackage(joinPoint));
    }
}
