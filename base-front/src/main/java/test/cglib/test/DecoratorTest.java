package test.cglib.test;

/**
 * 装饰者模式实现的代理(decorator)
 * 
 * @author n004881
 *
 */
public class DecoratorTest implements Test {

    private Test target;

    public DecoratorTest(Test target) {
        this.target = target;
    }

    public int test(int i) {
        return target.test(i);
    }
}
