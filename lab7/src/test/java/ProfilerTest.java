import included.Included;
import included.inner.Inner;
import notincluded.NotIncluded;
import org.junit.Assert;
import org.junit.Test;
import profiler.Profiler;

public class ProfilerTest {
    private static final String INCLUDED_RUN_METHOD_NAME = "public static void included.Included.run(int)";
    private static final String INCLUDED_RETURN_NUM_METHOD_NAME = "public static int included.Included.returnNum()";
    private static final String INNER_RUN_METHOD_NAME = "public static void included.inner.Inner.run()";
    private static final String NOT_INCLUDED_RETURN_STRING_METHOD_NAME = "public static java.lang.String notincluded.NotIncluded.returnString()";

    @Test
    public void testAll() {
        Profiler.setupNew("*");
        invokeMethods();
        Profiler profiler = Profiler.getInstant();
        Assert.assertEquals(2, profiler.getMethodCount(INCLUDED_RUN_METHOD_NAME));
        Assert.assertEquals(2, profiler.getMethodCount(INCLUDED_RETURN_NUM_METHOD_NAME));
        Assert.assertEquals(3, profiler.getMethodCount(INNER_RUN_METHOD_NAME));
        Assert.assertEquals(1, profiler.getMethodCount(NOT_INCLUDED_RETURN_STRING_METHOD_NAME));
    }

    @Test
    public void testOnlyIncluded() {
        Profiler.setupNew("included");
        invokeMethods();
        Profiler profiler = Profiler.getInstant();
        Assert.assertEquals(2, profiler.getMethodCount(INCLUDED_RUN_METHOD_NAME));
        Assert.assertEquals(2, profiler.getMethodCount(INCLUDED_RETURN_NUM_METHOD_NAME));
        Assert.assertEquals(0, profiler.getMethodCount(INNER_RUN_METHOD_NAME));
        Assert.assertEquals(0, profiler.getMethodCount(NOT_INCLUDED_RETURN_STRING_METHOD_NAME));
    }

    @Test
    public void testIncludedWithInner() {
        Profiler.setupNew("included*");
        invokeMethods();
        Profiler profiler = Profiler.getInstant();
        Assert.assertEquals(2, profiler.getMethodCount(INCLUDED_RUN_METHOD_NAME));
        Assert.assertEquals(2, profiler.getMethodCount(INCLUDED_RETURN_NUM_METHOD_NAME));
        Assert.assertEquals(3, profiler.getMethodCount(INNER_RUN_METHOD_NAME));
        Assert.assertEquals(0, profiler.getMethodCount(NOT_INCLUDED_RETURN_STRING_METHOD_NAME));
    }

    private void invokeMethods() {
        Inner.run();
        Included.run(1);
        Included.run(2);
        NotIncluded.returnString();
        Inner.run();
        Inner.run();
    }
}
