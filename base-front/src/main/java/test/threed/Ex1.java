package test.threed;

/**
 * 同账户同步存钱
 * 
 * @author n004881
 *
 */
public class Ex1 {

    public static void main(String[] args) {
        Account account = new Account();
        new Thread(account, "小强").start();
        new Thread(account, "小花").start();

    }

}

class Account implements Runnable {

    private int money = 0;  //存款

    @Override
    public void run() {
        //存钱三次，每次存100元，并且在读完之后显示账户的余额
        for (int i = 0; i < 3; i++) {
            saveMoney(100);
        }
    }

    /**
     * 存钱
     */
    public synchronized void saveMoney(int money) {
        this.money += money;
        System.out.println(Thread.currentThread().getName() + "存入100元，账户余额为" + this.money);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
