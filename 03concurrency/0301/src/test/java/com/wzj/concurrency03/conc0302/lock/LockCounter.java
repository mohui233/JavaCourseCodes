package com.wzj.concurrency03.conc0302.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class LockCounter {
    private int sum = 0;
    // 可重入锁+公平锁
    private Lock lock = new ReentrantLock((true));
    public int AddAndGet(){
        try {
            lock.lock();
            return ++sum;
        } finally {
            lock.unlock();
        }
    }
    public int getSum() {
        return sum;
    }

    // 测试代码
    public static void testLockCounter() {
        int loopNum = 100_0000;
        LockCounter counter = new LockCounter();
        IntStream.range(0,loopNum).parallel()
                .forEach(i-> counter.AddAndGet());
        int sum = counter.getSum();
        System.out.println(sum);
    }

    public static void main(String[] args) {
        testLockCounter();
    }
}
