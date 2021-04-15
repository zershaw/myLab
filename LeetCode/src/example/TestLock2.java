package example;

import java.util.concurrent.locks.ReentrantLock;

public class TestLock2 implements Runnable {

    int ticketNums = 500;
    //定义Lock锁
    private ReentrantLock reentrantLock = new ReentrantLock();


    @Override
    public void run() {
        while (true) {
            try {
                reentrantLock.lock(); //加锁
                if(ticketNums > 0 ){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "购买了第" + ticketNums-- + "张票");

                } else {
                    break;
                }
            } finally {
                //解锁
                reentrantLock.unlock();
            }

        }
    }
}