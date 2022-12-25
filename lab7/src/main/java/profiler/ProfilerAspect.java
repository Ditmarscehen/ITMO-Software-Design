package profiler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ProfilerAspect {

    @Around("execution(* *(..)) && !within(profiler.*)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startNs = System.nanoTime();
        Object result = joinPoint.proceed(joinPoint.getArgs());
        long spentTime = System.nanoTime() - startNs;
        Profiler.getInstant().profile(joinPoint, spentTime);

        return result;
    }
}