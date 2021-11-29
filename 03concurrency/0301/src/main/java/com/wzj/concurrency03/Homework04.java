package com.wzj.concurrency03;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 *
 * 方法四：读写分离，写加锁，读不加锁，参考CopyOnWriteArrayList的实现
 *
 * @author wangzhijie
 */
public class Homework04 {

    private static transient volatile int b;

    public static void main(String[] args) {
        long start=System.currentTimeMillis();

        // 异步执行 下面方法
        try {
            new Thread(() -> { SyncCount.add(); }).start();

            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 这是得到的返回值
            int result = SyncCount.get();

            // 确保 拿到result 并输出
            System.out.println("异步计算结果为："+result);
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

            // 然后退出main线程
            System.out.println("Main Thread End!");

        } catch (Exception ex) {
            System.out.println("catch submit");
            ex.printStackTrace();
        }
    }

    public static class SyncCount {
        public static boolean add() {
            ReentrantLock lock = new ReentrantLock(true);
            lock.lock();
            try {
                b = sum();
                return true;
            } finally {
                lock.unlock();
            }
        }
        public static int get() {
            return b;
        }
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2) {
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }

}
