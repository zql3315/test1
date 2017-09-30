package test.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class TestFileWrite {

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 100; i++) {
            new Thread(new DIYWrite()).start();
        }

//        File f = new File("a" + System.currentTimeMillis());
//        f.createNewFile();  //当且仅当不存在具有此抽象路径名指定名称的文件时，不可分地创建一个新的空文件。  
//        java.util.concurrent.CountDownLatch cwl = new java.util.concurrent.CountDownLatch(3);
//        FileOutputStream fos = new FileOutputStream(f);
//        new Thread(new WriteThread(cwl, fos, "bb")).start();
//        new Thread(new WriteThread(cwl, fos, "aaasdfasfd")).start();
//        new Thread(new WriteThread(cwl, fos, "cc")).start();
//        cwl.await();
//        System.out.println("=====");
//        fos.flush();
//        fos.close();
//        FileInputStream f1 = new FileInputStream(f);
//        byte[] bytearray = new byte[1024];
//        int n = f1.read(bytearray);
//        f1.close();
//        System.out.println(new String(bytearray, 0, n));
    }

}

class WriteThread implements Runnable {

    OutputStream os = null;

    String fSpe = null;

    java.util.concurrent.CountDownLatch cwl;

    public WriteThread(java.util.concurrent.CountDownLatch cwl, OutputStream os, String fSpe) {
        super();
        this.cwl = cwl;
        this.os = os;
        this.fSpe = fSpe;
    }

    @Override
    public void run() {
        try {
            os.write((fSpe + "\r\n").getBytes());
            cwl.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
