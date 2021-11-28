package com.wzj.concurrency03;

import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 *
 * 方法一：创建一个可缓存的线程池
 *
 * @author wangzhijie
 */
public class Homework01 {


    public static void main(String[] args) {
        long start=System.currentTimeMillis();

        // 异步执行 下面方法
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            // 这是得到的返回值
            int result = executorService.submit(() -> sum()).get();

            // 确保 拿到result 并输出
            System.out.println("异步计算结果为："+result);
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

            // 然后退出main线程
            executorService.shutdown();
            System.out.println("Main Thread End!");

        } catch (Exception ex) {
            System.out.println("catch submit");
            ex.printStackTrace();
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
