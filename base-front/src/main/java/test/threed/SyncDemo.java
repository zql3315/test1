package test.threed;

/*
 * 购票同步对象锁
 * 
 * 用程序模拟铁路售票系统：实现通过两个售票点发售某日某次列车的50张车票，
 * 一个售票点用一个线程表示
 */
public class SyncDemo {

    public static void main(String[] args) {

        new SaleTicketThread("窗口1").start();
        new SaleTicketThread("窗口2").start();

        // 创建票对象（内部封装了卖票的方法，并且具备运行在线程中的能力）
        Ticket tickt = new Ticket();
        // 让多个窗口同时卖票（让线程执行指定的任务对象）
        new Thread(tickt, "窗口1").start();
        new Thread(tickt, "窗口2").start();
    }

}

class Ticket implements Runnable {

    int num = 50;// 票数

    Object obj = new Object();

    @Override
    public void run() {
        // 不停地卖票
        while (true) {
            sale();
        }
    }

    /**
     * 卖票，每调用一次，卖一张票
     */
    //同步方法，其实就是同步代码块的简写方式
    public synchronized void sale() {
        // 同步代码块，同一时间只能有一个线程进行执行里面的代码
        //          synchronized (this) {// 同步锁,每个对象都可以作为同步锁进行使用(可用任何对象)
        if (num > 0) {
            try {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + "...sale..." + num--);
            } catch (InterruptedException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //          }
    }

}

class SaleTicketThread extends Thread {

    private int num = 50;

    public SaleTicketThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (num > 0) {
            System.out.println(getName() + "......sale....." + num);
            num--;
        }
    }

}
