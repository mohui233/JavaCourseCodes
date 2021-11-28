package com.wzj.concurrency03;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author wangzhijie
 */
public class RandomSleepTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Integer result = sum();
        TimeUnit.MICROSECONDS.sleep(1);
        return result;
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
