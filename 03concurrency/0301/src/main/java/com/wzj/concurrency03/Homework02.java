package com.wzj.concurrency03;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 *
 * 方法二：读写分离，读写都加同步锁
 *
 * @author wangzhijie
 */
public class Homework02 {

    public static void main(String[] args) {
        long start=System.currentTimeMillis();

        // 异步执行 下面方法
        try {
            final SetGet s = new SetGet();
            Thread t = new Thread(() -> {
                try {
                    s.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.start();
            s.set();

            // 这是得到的返回值
            int result = s.get();

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

    public static class SetGet {

        int a = 0;
        public synchronized void set() throws Exception {
            a = sum();
        }

        public synchronized int get() throws Exception {
            return a;
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
