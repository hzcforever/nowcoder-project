package com.nowcoder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadTest {

    public static void main(String[] args) {
//        test();
//        testExecutor();
        testAtomic();
    }

    private static int count = 0;
    private static AtomicInteger atomicInteger = new AtomicInteger();
    public static void testAtomic() {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        for (int i = 0; i < 10; i++) {
//                            count++;
//                            System.out.println(count);
                            System.out.println(atomicInteger.incrementAndGet()); //原子性操作
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static void testExecutor() {
        ExecutorService service1 = Executors.newSingleThreadExecutor(); //单线程
        ExecutorService service2 = Executors.newFixedThreadPool(2);
        service2.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i = 0;i < 10;i++) {
                        Thread.sleep(1000);
                        System.out.println("Executor2:" + i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        service2.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i = 0;i < 10;i++) {
                        Thread.sleep(1000);
                        System.out.println("Executor1:" + i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        service1.shutdown(); //等到前面线程任务执行完后关闭
        service2.shutdown();
    }

    public static void test() {
        BlockingQueue<String > q = new ArrayBlockingQueue<>(10);
        new Thread(new Producer(q)).start();
        new Thread(new Consumer(q), "Consumer1").start();
        new Thread(new Consumer(q), "Consumer2").start();
    }
}

class Consumer implements Runnable {

    private BlockingQueue<String> q = null;

    public Consumer(BlockingQueue<String> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":" + q.take());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Producer implements Runnable {

    private BlockingQueue<String> q = null;

    public Producer(BlockingQueue<String> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            for(int i = 0;i < 100;i++) {
                Thread.sleep(1000);
                q.put(String.valueOf(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

