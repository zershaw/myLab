package example.jucTest;

class Data{
    private int num = 0;
    public synchronized void increment() throws InterruptedException{
        while(num != 0){
            this.wait();
        }
        num++;
        System.out.println(Thread.currentThread().getName() + "=>" + num);
        this.notifyAll();
    }
    // ********************
    public synchronized void decrement() throws InterruptedException{
        while (num == 0){
            this.wait();
        }
        num--;
        System.out.println(Thread.currentThread().getName() + "=>" + num);
        this.notifyAll();
    }
}