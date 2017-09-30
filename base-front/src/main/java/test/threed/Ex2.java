package test.threed;

/*
 * 火车过山洞
 * 
 * 有5辆火车要过山洞，但确保山洞同时只能有一辆火车通过（过山洞需要1秒），打印输出火车通过的顺序。
 * （过山洞的顺序是不可控的，只要保证同一时间只有一辆火车能通过山洞即可）
 * 提示：使用线程同步，一辆火车就是一个线程
 */
public class Ex2 {

    public static void main(String[] args) {

        new Train("火车1").start();
        new Train("火车2").start();
        new Train("火车3").start();
        new Train("火车4").start();
        new Train("火车5").start();
    }
}

class Train extends Thread {

    public Train(String name) {
        super(name);
    }

    @Override
    public void run() {
        synchronized (Train.class) {
            System.out.println(getName() + "过山洞.....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODOAuto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
