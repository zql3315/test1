package test.threed;

/**
 * 主子线程的循环输出
 * 子线程循环3次，接着主线程循环6次，接着又回到子线程循环3次，接着再回到主线程又循环6次，如此循环10次，请写出程序
 * @author n004881
 *
 */
public class Ex4 {

    static boolean flag = false; // 记录子线程是否已经输出

    static Object lock = new Object();

    public static void main(String[] args) {
        // TODO Auto-generated methodstub
        // 子线程
        new Thread() {

            @Override
            public void run() {
                try {
                    for (int i = 1; i <= 10; i++) {
                        synchronized (lock) {
                            if (flag) {
                                lock.wait();
                            }
                            System.out.println("~~~~~~~~~~~~~~第" + i + "回合~~~~~~~~");
                            for (int j = 1; j <= 3; j++) {
                                System.out.println("子线程" + j);
                                Thread.sleep(200);
                            }
                            flag = true;
                            lock.notify();
                        }
                    }
                } catch (InterruptedException e) {
                    //TODO Auto-generated catch block
                    e.printStackTrace();
                }
            };
        }.start();
        // 主线程
        for (int i = 1; i <= 10; i++) {
            try {
                synchronized (lock) {
                    if (!flag) {
                        lock.wait();
                    }
                    for (int j = 1; j <= 6; j++) {
                        System.out.println("主线程....." + j);
                        Thread.sleep(200);
                    }
                    flag = false;
                    lock.notify();
                }
            } catch (InterruptedException e) {
                // TODOAuto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
