package test.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 在实际的项目中我们经常会与遇到操作文件的情况，这是很常见的，但是我们在实际中用传统的I/O操作不能满足我们项目的需要并且对文件的写特别慢，操作比较麻烦。
 * 那么我们怎么样实现呢？最近在项目中我们遇到了高并发的事情，我是这样操作的。
 * 利用JavaNIO实现非阻塞式的读写，通过锁机制完成高并发下的文件的操作。
 * 
 * @author n004881
 * 
 *
 */
public class DIYWrite extends Thread {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new DIYWrite()).start();
        }
    }

    public void run() {
        File file = new File("d:/aaa/test.txt");
        RandomAccessFile raf = null;
        FileChannel fileChannel = null;
        FileLock flout = null;
        try {
            raf = new RandomAccessFile(file, "rw");
            long filelength = raf.length();//获取文件的长度
            raf.seek(filelength);//将文件的读写指针定位到文件的末尾
            fileChannel = raf.getChannel();//打开文件通道
            while (true) {
                try {
                    flout = fileChannel.tryLock();//不断的请求锁，如果请求不到，等一秒再请求
                    break;
                } catch (Exception e) {
                    System.out.println("lock is exist ......");
                    sleep(100);
                }
            }
            String str = "{\"aaa\":22222}\n";//需要写入的内容

            raf.write(str.getBytes());//将需要写入的内容写入文件
        } catch (IOException e1) {
            e1.printStackTrace();
            System.out.print("file no find ...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                flout.release();
            } catch (IOException e1) {
                e1.printStackTrace();
                flout = null;
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    raf = null;
                }
            }
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    fileChannel = null;
                }
            }
        }

    }

}
