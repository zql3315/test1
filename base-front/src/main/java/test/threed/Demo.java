package test.threed;

/**
 * 
 *  处理单例模式中的线程同步问题
 *  在懒汉式单例模式中存在线程同步问题
 *  处理方案：采用双重判断的形式同时解决效率和线程安全的问题
 * 
 * @author n004881
 *
 */
public class Demo {

    public static void main(String[] args) {
        new Thread() {

            @Override
            public void run() {
                System.out.println(Singleton3.getInstance());
            }

        }.start();

        new Thread() {

            @Override
            public void run() {
                System.out.println(Singleton3.getInstance());
            }

        }.start();

    }

}

//单例模式(饿汉式)
class Singleton {

    //静态字段是在类加载的时候就初始化了
    private static Singleton instance = new Singleton();

    // 私有化构造方法，防止外界创建对象
    private Singleton() {

    }

    public static Singleton getInstance() {
        return instance;
    }
}

//单例模式(懒汉式)
//存在线程安全问题，可以通过双重判断加同步处理解决这里的线程安全问题

class Singleton2 {

    private static Singleton2 instance;

    private Singleton2() {
        // emty
    }

    // 在这里，线程安全问题只会出现在第一次创建对象的时候，只要对象已经被创建完，
    // 那么就不需要再对代码进行同步处理
    // public static synchronizedSingleton2 getInstance(){//存在线程安全问题，可以通过双重判断加同步处理解决这里的线程安全问题
    public static Singleton2 getInstance() {

        if (instance == null) {        //通过双重判断对象是否存在
            synchronized (Singleton2.class) {//同步
                if (instance == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        //TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    instance = new Singleton2();
                }
            }

        }
        return instance;
    }
}

/*使用静态内部类实现单例模式
* 在类加载的时候会加载类中的成员，会初始化静态字段，将类中的成员加载到内存中(方法区)
*
*/
class Singleton3 {

    private Singleton3() {

    }

    //静态代码块只会在类加载的时候被调用一次
    static {
        System.out.println("外部类中的静态代码块......");
    }

    //静态内部类
    private static class Inner {

        static Singleton3 instance = new Singleton3();
        static {
            System.out.println("内部类中的静态代码块.....");
        }
    }

    public static Singleton3 getInstance() {
        return Inner.instance;
    }
}
