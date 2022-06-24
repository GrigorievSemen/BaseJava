package ru.mystudies.basejava;

public class MainDeadLock {
    public static final String Lock_1 = "Lock_1";
    public static final String Lock_2 = "Lock_2";

    public static void main(String[] args) {
        doRun(Lock_1, Lock_2);
        doRun(Lock_2, Lock_1);
    }

    private static void doRun(String str_1, String str_2) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (str_1) {
                    System.out.println(Thread.currentThread().getName() + " : удерживает блокировку " + str_1);
                    try {
                        Thread.sleep(10);
                        System.out.println(Thread.currentThread().getName() + " : ожидает снятие блокировки c  " + str_2);
                        synchronized (str_2) {
                            //do something
                        }
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }).start();
    }
}
