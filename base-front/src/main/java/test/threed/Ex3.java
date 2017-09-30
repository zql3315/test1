package test.threed;

/**
 * 数字和字母的间隔输出
 * 1.         写两个线程，一个线程打印 1~52，另一个线程打印字母A-Z打印顺序为12A34B56C……5152Z（2个数字1个字母）。
 * 提示：使用线程间的通信。
*/
public class Ex3 {

    public static void main(String[] args) {
        // TODO Auto-generated methodstub
        Printer printer = new Printer();
        new NumberThread(printer).start();
        new LetterThread(printer).start();
    }

}

//数字输出线程
class NumberThread extends Thread {

    private Printer printer;

    public NumberThread(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 52; i++) {
            printer.printNum(i);
        }
    }
}

//字母输出线程
class LetterThread extends Thread {

    private Printer printer;

    public LetterThread(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        for (char c = 'A'; c <= 'Z'; c++) {
            printer.printLetter(c);
        }
    }
}

/**
* 打印输出类
*/
class Printer {

    private boolean numOut = false;  //信号量，记录数字是否已经输出

    //输出数字
    public synchronized void printNum(int num) {
        try {
            if (numOut) {//如果数字已经输出，就等待输出字母
                wait();//注意：wait、notify的调用者必须是当前同步代码块对应的同步锁
            }
            System.out.println(num);  //输出数字
            //如果刚刚输出的数字是偶数的话，就唤醒字母输出线程
            if (num % 2 == 0) {
                numOut = true;  //标记已经输出数字
                notify();       //唤醒字母输出线程去输出字母
            }
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // TODOAuto-generated catch block
            e.printStackTrace();
        }

    }

    //输出字母
    public synchronized void printLetter(char c) {
        try {
            if (!numOut) {    //如果还没有输出数字，就等待数字输出
                wait();
            }
            System.out.println(c);   //输出字母
            numOut = false;       //标记已经输出国字母，等待输出数字
            notify();             //唤醒数字输出线程去输出数字
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // TODOAuto-generated catch block
            e.printStackTrace();
        }

    }
}
