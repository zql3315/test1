package test.cglib.test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * 都说 Cglib 创建的动态代理的运行性能比 JDK 动态代理能高出大概 10 倍，今日抱着怀疑精神验证了一下，发现情况有所不同，遂贴出实验结果，以供参考和讨论。
 * 
 * 以 TestImpl 的调用耗时作为基准，对比通过其它三种代理进行调用的耗时
 * 
 * 结论：从 jdk6 到 jdk7、jdk8 ，动态代理的性能得到了显著的提升，而 cglib 的表现并未跟上，甚至可能会略微下降。传言的 cglib 比 jdk动态代理高出 10 倍的情况也许是出现在更低版本的 jdk 上吧。

 * 以上测试用例虽然简单，但揭示了 jdk 版本升级可能会带来一些新技术改变，会使我们以前的经验失效。放在真实业务场景下时，还需要按照实际情况进行测试后才能得出特定于场景的结论。

 * 总之，实践出真知，还要与时俱进地去检视更新一些以往经验。

 * 注：上述实验中 cglib 的版本是 3.1 。

 * @author n004881
 *
 */

public class ProxyPerfTester {

    public static void main(String[] args) {
        //创建测试对象；
        Test nativeTest = new TestImpl();
        Test decorator = new DecoratorTest(nativeTest);
        Test dynamicProxy = DynamicProxyTest.newProxyInstance(nativeTest);
        Test cglibProxy = CglibProxyTest.newProxyInstance(TestImpl.class);

        //预热一下；
        int preRunCount = 10000;
        runWithoutMonitor(nativeTest, preRunCount);
        runWithoutMonitor(decorator, preRunCount);
        runWithoutMonitor(cglibProxy, preRunCount);
        runWithoutMonitor(dynamicProxy, preRunCount);

        //执行测试；
        Map<String, Test> tests = new LinkedHashMap<String, Test>();
        tests.put("Native   ", nativeTest);
        tests.put("Decorator", decorator);
        tests.put("Dynamic  ", dynamicProxy);
        tests.put("Cglib    ", cglibProxy);
        int repeatCount = 3;
        int runCount = 1000000;
        runTest(repeatCount, runCount, tests);
//        runCount = 50000000;
//        runTest(repeatCount, runCount, tests);
    }

    private static void runTest(int repeatCount, int runCount, Map<String, Test> tests) {
        System.out.println(String.format(
                "\n==================== run test : [repeatCount=%s] [runCount=%s] [java.version=%s] ====================",
                repeatCount,
                runCount,
                System.getProperty("java.version")));
        for (int i = 0; i < repeatCount; i++) {
            System.out.println(String.format("\n--------- test : [%s] ---------", (i + 1)));
            for (String key : tests.keySet()) {
                runWithMonitor(tests.get(key), runCount, key);
            }
        }
    }

    private static void runWithoutMonitor(Test test, int runCount) {
        for (int i = 0; i < runCount; i++) {
            test.test(i);
        }
    }

    private static void runWithMonitor(Test test, int runCount, String tag) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < runCount; i++) {
            test.test(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("[" + tag + "] Elapsed Time:" + (end - start) + "ms");
    }
}
