package example;

public class ThreadTest {


    public static void main(String[] args) throws InterruptedException {
//        TestRunnable t = new TestRunnable();
//        Thread thread = new Thread(t);
        //任何接口，如果只包含唯一一个抽象方法，那么它就是一个函数式接口
        Thread thread = new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("///");
        });

        //观察状态
        Thread.State state = thread.getState();
        System.out.println(state);    //NEW

        //观察后启动
        thread.start();   //启动线程
        state = thread.getState();
        System.out.println();   //Run

        while(state != Thread.State.TERMINATED){  //只要线程不停止，就一直输出状态
            Thread.sleep(100);
            state = thread.getState();    //更新线程状态
            System.out.println(state);   //每100毫秒更新一次输出状态

            //thread.start();   报错，因为已经死亡的线程不能再启动
        }
    }
}
