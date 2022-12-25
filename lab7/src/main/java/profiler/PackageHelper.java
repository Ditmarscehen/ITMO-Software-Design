package profiler;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class PackageHelper {
    private static final Pattern packageNamePattern = Pattern.compile("^[a-z\\d_]*(\\.[a-z\\d_]+)*(\\*)?$");

    public static boolean isValidPackageName(String packageName) {
        return packageNamePattern.asMatchPredicate().test(packageName);
    }

    public static String getPackage(ProceedingJoinPoint joinPoint) {
        List<String> packageAndClass = Arrays.asList(joinPoint.getSignature().getDeclaringTypeName().split("\\."));

        return String.join(".", packageAndClass.subList(0, packageAndClass.size() - 1));
    }

    public static Pattern getPackageNamePattern(String packageName) {
        return Pattern.compile("^" + packageName
                .replaceAll("\\.", "\\.")
                .replaceAll("\\*", ".*")
                + "$");
    }
}
