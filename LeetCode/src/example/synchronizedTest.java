package example;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class synchronizedTest implements Runnable {
    //共享资源
    static int i = 0;

    /**
     * synchronized 修饰实例方法
     */
    public static synchronized void increase() {
        i++;
        System.out.println(Thread.currentThread().getName()+' ' +i);
    }

    @Override
    public void run() {
        for (int j = 0; j < 1000; j++) {
            increase();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        synchronizedTest czx = new synchronizedTest();
        Thread t1 = new Thread(czx);
        Thread t2 = new Thread(czx);
        t1.start();
        t2.start();
        //t1.join();
        //t2.join();
    }


}

