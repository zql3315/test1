package test.threed;

public class ThreadTest4 {

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {

            public void run() {
                Count count = new Count();
                count.count();
            }
        };
        for (int i = 0; i < 10; i++) {
            new Thread(runnable,"线程=="+i).start();
        }
    }
}
