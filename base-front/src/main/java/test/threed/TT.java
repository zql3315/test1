package test.threed;

import java.util.concurrent.CountDownLatch;

public class TT implements Runnable {

    public static int i = 0;

    static int threadCount = 1000000;

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(new TT(latch)).start();
        }
        
        latch.await();
        System.out.println(i + "===========");
    }

    private CountDownLatch latch;

    public TT(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        s();
        latch.countDown();
    }

    public void s() {
        i++;
    }

    public synchronized void s1() {
        i++;
    }
}
