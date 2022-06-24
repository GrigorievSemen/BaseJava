package ru.mystudies.basejava;

public class MainDeadLock {
    public static final Object Lock_1 = new Object();
    public static final Object Lock_2 = new Object();

    public static void main(String[] args) {
        Thread thread_1 = new Thread(new Test_1(), "Thread 1");
        Thread thread_2 = new Thread(new Test_2(), "Thread 2");

        thread_1.start();
        thread_2.start();
    }

    private static class Test_1 implements Runnable {
        public void run() {
            synchronized (Lock_1) {
                System.out.println(Thread.currentThread().getName() + " : удерживает блокировку Lock_1");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(Thread.currentThread().getName() + " : ожидает снятие блокировки Lock_2");

                synchronized (Lock_2) {
                    //do something
                }
            }
        }
    }

    private static class Test_2 implements Runnable {
        public void run() {
            synchronized (Lock_2) {
                System.out.println(Thread.currentThread().getName() + " : удерживает блокировку Lock_2");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(Thread.currentThread().getName() + " : ожидает снятия блокировки Lock_1");
                synchronized (Lock_1) {
                    //do something
                }
            }
        }
    }
}
