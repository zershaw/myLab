package refactoring_guru.singleton.example.non_thread_safe;

public class TestRunnable implements Runnable {

    @Override
    public void run() {
        if(Thread.currentThread().getName().equals("one")){
            Singleton singleton = Singleton.getInstance("FOO");
            System.out.println(Thread.currentThread().getName() + singleton.value);
        }
        else{
            Singleton anotherSingleton = Singleton.getInstance("BAR");
            System.out.println(Thread.currentThread().getName() + anotherSingleton.value);
        }

    }
    public static void main(String[] args) {
        //创建线程对象，通过线程对象来开启我们的线程（代理模式）
        TestRunnable thread = new TestRunnable();
        new Thread(thread,"one").start();
        new Thread(thread,"two").start();
    }
}

