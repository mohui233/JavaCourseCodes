# Java 8 不同GC和堆内存的总结



##  使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 /CMS/G1的案例

### 一、并行 GC

> 添加 GC 策略参数  -XX:+UseParallelGC ，或 GC策略删掉不要。Java8默认的解决策略，并行GC。

1.  默认内存配置

```java
 java -XX:+PrintGCDetails com.wzj.nio01.GCLogAnalysis
 
 执行 5 次 Full GC 。
 
 物理内存16G，默认使用四分之一的物理内存，作为当前的虚拟机最大的堆内存。
 物理内存小于1G，默认会使用二分之一的物理内存，作为当前的虚拟机最大的堆内存。
```

2. 手动配置内存参数  -Xmx1g -Xms1g

```java
2.1 默认策略

java -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xmx1g -Xms1g com.wzj.production.nio01.GCLogAnalysis  

执行 7 次 Full GC 。
  

2.2 并行GC策略 -XX:+UseParallelGC

java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx1g -Xms1g -XX:+UseParallelGC com.wzj.production.nio01.GCLogAnalysis 

执行 6 次 Full GC 。
```

3. 手动配置内存参数  -Xmx512m -Xms512m

```java
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx512m -Xms512m com.wzj.production.nio01.GCLogAnalysis 

执行 38 次 Full GC 。

可以看到发生了多次的Full GC，跟 -Xmx1g -Xms1g 相比 GC的频率更频繁了。说明我们的整个堆内存是不太够用的。
```

4. 手动配置内存参数  -Xmx256m -Xms256m

```java
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx256m -Xms256m com.wzj.production.nio01.GCLogAnalysis 

执行 24 次 Full GC 。

最终出现我们熟悉的OutOfMemory，我们的堆内存溢出。也就是配置256的堆内存，它是放不下我们刚才创建了那么多对象的，所以堆内存就溢出了。
```

5. 手动配置内存参数 -Xmx4g -Xms4g              

```java
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx4g -Xms4g -XX:+UseParallelGC com.wzj.production.nio01.GCLogAnalysis 
 
只执行 12 次 Young GC 。
年轻代垃圾回收器名字 PSYoungGen 。

比前面堆内存小的时候 -Xmx1g -Xms1g ，GC的次数就会变小，GC的暂停时间长了很多。
JVM的堆内存, 整个蓄水池的作用其实提升了。也就是说单位时间内, 它可以容纳更多的对象, 因为整个容量变大了。Young区变大了Old区也变大了。
不管是新对象还是老年代对像,它都可以容纳更大的更多的对象。
```

6. 手动配置内存参数  -Xmx1g

```java
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx1g -XX:+UseParallelGC com.wzj.production.nio01.GCLogAnalysis 

执行 11 次 Full GC 。

GC的频率，明显比之前 -Xmx1g -Xms1g 要多了一些。
就是第一次发生Full GC的时候，堆内存容量没有到我们可能运用的最大值。但是这时候, 因为容量一开始被设的还不大，然后Old区满了, 自然的就会产生Full GC,JVM才会把我们的整个堆内存的容量进步的扩大。最终不断地扩大，会扩大到我们的最大值，Xmx这个最大值。同样的道理因为我们的堆内存变小了,更短的时间内就把它填满了, 所以需要更快的回收频率, 尽快地把它清空掉。
```



### 二、串行 GC

> 添加 GC 策略参数  -XX:+UseSerialGC 

1. 手动配置内存参数  -Xmx1g -Xms1g

```java
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx1g -Xms1g -XX:+UseSerialGC com.wzj.production.nio01.GCLogAnalysis

只执行 41 次 Young GC 。
年轻代垃圾回收器名字 DefNew 。

前36次Young区垃圾回收完之后， 这时候我们的Old区已经比较大了，所以执行 1 次老年代的垃圾回收。
这次老年代的垃圾回收，把老年代，大概回收掉了 50% 的对象，用掉了 22 毫秒。
可以发现Old区回收和Young区回收的时间是差不多的。
因为串行化的 GC，它的垃圾回收是单线程的，所以它执行的效率是相对比较低的。
```



### 三、CMS GC

> 添加 GC 策略参数  -XX:+UseConcMarkSweepGC 

```java
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx1g -Xms1g -XX:+UseConcMarkSweepGC com.wzj.production.nio01.GCLogAnalysis 

执行 6 次 CMS GC 。
执行 37 次 Young GC 。
CMS最终标记 CMS Final Remark 。
年轻代垃圾回收器名字 ParNew 。

我们可以看到个特殊的现象,就是发生了 ParNew 的 Young GC 。
在CMS GC的过程中, CMS 因为它是回收我们的老年代的, 所以CMS进行过程中, 有可能会发生一次或多次的Young GC。
```



### 四、G1 GC

> 添加 GC 策略参数  -XX:+UseG1GC              

1. 手动配置内存参数 -Xmx1g -Xms1g          

```java
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx1g -Xms1g -XX:+UseG1GC com.wzj.production.nio01.GCLogAnalysis  


G1 GC的日志,非常之复杂非常的复杂,复杂程度远超其它的GC 。
很多标记了并行的表示很多操作步骤 [Parallel Time, 表示是可以多线程并发来执行的。
另外我们可以看到G1 GC的暂停,有些是Young区的,有些是混合的。 

因为大对象的分配失败，导致了启动了G1的初始化标记 (initial-mark) 。G1作为我们CMS算法的升级,所以它很多的步骤,跟我们的CMS是比较类似的。
```

2. 手动配置内存参数 -Xmx4g -Xms4g   

```java
java -XX:+PrintGC -XX:+PrintGCDateStamps -Xmx4g -Xms4g -XX:+UseG1GC com.wzj.production.nio01.GCLogAnalysis      

只执行 20 次 Young GC 。

Young区和Old区比较大。Young 区还没晋升到 Old区。
```





## 使用压测工具wrk 或 sb，演练 gateway-server-0.0.1-SNAPSHOT.jar 示例

### 一、并行 GC

> 添加 GC 策略参数  -XX:+UseParallelGC ，或 GC策略删掉不要。Java8默认的解决策略，并行GC。

1. 手动配置内存参数  -Xmx1g -Xms1g

```java
wangzhijie@bogon nio01 % java -jar -Xmx1g -Xms1g -XX:+UseParallelGC gateway-server-0.0.1-SNAPSHOT.jar
wangzhijie@bogon nio01 % wrk -c 40 -d30s http://localhost:8088
Running 30s test @ http://localhost:8088
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.37ms   11.77ms 195.91ms   94.49%
    Req/Sec    25.67k    11.69k   66.08k    67.18%
  1511850 requests in 30.09s, 375.14MB read
  Non-2xx or 3xx responses: 1511850
Requests/sec:  50243.26
Transfer/sec:     12.47MB
```

2. 手动配置内存参数 -Xmx4g -Xms4g    

   ```java
   wangzhijie@bogon nio01 % java -jar -Xmx4g -Xms4g -XX:+UseParallelGC gateway-server-0.0.1-SNAPSHOT.jar 
   wangzhijie@bogon nio01 % wrk -c 40 -d30s http://localhost:8088
   Running 30s test @ http://localhost:8088
     2 threads and 40 connections
     Thread Stats   Avg      Stdev     Max   +/- Stdev
       Latency     3.73ms   12.95ms 253.85ms   94.30%
       Req/Sec    25.43k    11.96k   63.08k    66.10%
     1499455 requests in 30.08s, 372.07MB read
     Non-2xx or 3xx responses: 1499455
   Requests/sec:  49853.64
   Transfer/sec:     12.37MB
   ```

3. 手动配置内存参数 -Xmx256m -Xms256m

```java
wangzhijie@bogon nio01 % java -jar -Xmx256m -Xms256m -XX:+UseParallelGC gateway-server-0.0.1-SNAPSHOT.jar
wangzhijie@bogon nio01 % wrk -c 40 -d30s http://localhost:8088
Running 30s test @ http://localhost:8088
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.68ms   12.91ms 231.04ms   94.39%
    Req/Sec    23.32k     9.66k   47.17k    66.72%
  1384188 requests in 30.06s, 343.47MB read
  Non-2xx or 3xx responses: 1384188
Requests/sec:  46042.00
Transfer/sec:     11.42MB
```

4. 手动配置内存参数 -Xmx512m -Xms512m

```java
wangzhijie@bogon nio01 % java -jar -Xmx512m -Xms512m -XX:+UseParallelGC gateway-server-0.0.1-SNAPSHOT.jar
wangzhijie@bogon nio01 % wrk -c 40 -d30s http://localhost:8088
Running 30s test @ http://localhost:8088
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.55ms   13.19ms 222.19ms   94.92%
    Req/Sec    25.13k    10.31k   55.52k    70.32%
  1491997 requests in 30.07s, 370.22MB read
  Non-2xx or 3xx responses: 1491997
Requests/sec:  49624.71
Transfer/sec:     12.31MB
```



### 二、串行 GC

> 添加 GC 策略参数  -XX:+UseSerialGC 

1. 手动配置内存参数  -Xmx1g -Xms1g

```java
wangzhijie@bogon nio01 % java -jar -Xmx1g -Xms1g -XX:+UseSerialGC gateway-server-0.0.1-SNAPSHOT.jar
wangzhijie@bogon nio01 % wrk -c 40 -d30s http://localhost:8088
Running 30s test @ http://localhost:8088
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     5.00ms   16.79ms 258.50ms   93.78%
    Req/Sec    23.71k    11.35k   56.99k    63.73%
  1403513 requests in 30.07s, 348.26MB read
  Non-2xx or 3xx responses: 1403513
Requests/sec:  46673.99
Transfer/sec:     11.58MB
```

2. 手动配置内存参数 -Xmx4g -Xms4g    

```java
wangzhijie@bogon nio01 %  java -jar -Xmx4g -Xms4g -XX:+UseSerialGC gateway-server-0.0.1-SNAPSHOT.jar 
wangzhijie@bogon nio01 % wrk -c 40 -d30s http://localhost:8088
Running 30s test @ http://localhost:8088
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     4.94ms   17.00ms 246.70ms   93.94%
    Req/Sec    24.53k    11.66k   58.94k    66.78%
  1449097 requests in 30.08s, 359.57MB read
  Non-2xx or 3xx responses: 1449097
Requests/sec:  48172.57
Transfer/sec:     11.95MB
```

3. 手动配置内存参数 -Xmx256m -Xms256m

```java
wangzhijie@bogon nio01 %  java -jar -Xmx256m -Xms256m -XX:+UseSerialGC gateway-server-0.0.1-SNAPSHOT.jar
wangzhijie@bogon nio01 % wrk -c 40 -d30s http://localhost:8088
Running 30s test @ http://localhost:8088
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.77ms   16.21ms 277.29ms   96.13%
    Req/Sec    23.48k     8.82k   52.90k    71.04%
  1395149 requests in 30.10s, 346.19MB read
  Non-2xx or 3xx responses: 1395149
Requests/sec:  46346.54
Transfer/sec:     11.50MB
```

4. 手动配置内存参数 -Xmx512m -Xms512m

```java
wangzhijie@bogon nio01 %  java -jar -Xmx512m -Xms512m -XX:+UseSerialGC gateway-server-0.0.1-SNAPSHOT.jar
wangzhijie@bogon nio01 % wrk -c 40 -d30s http://localhost:8088
Running 30s test @ http://localhost:8088
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.14ms    7.92ms 159.13ms   95.82%
    Req/Sec    24.82k     9.83k   53.31k    70.27%
  1470647 requests in 30.03s, 364.92MB read
  Non-2xx or 3xx responses: 1470647
Requests/sec:  48971.12
Transfer/sec:     12.15MB
```



### 三、CMS GC

> 添加 GC 策略参数  -XX:+UseConcMarkSweepGC 

1. 手动配置内存参数  -Xmx1g -Xms1g

```java
wangzhijie@bogon nio01 % java -jar -Xmx1g -Xms1g -XX:+UseConcMarkSweepGC gateway-server-0.0.1-SNAPSHOT.jar
wangzhijie@bogon nio01 % wrk -c 40 -d30s http://localhost:8088
Running 30s test @ http://localhost:8088
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     4.14ms   13.54ms 319.40ms   93.54%
    Req/Sec    24.30k    11.65k   58.27k    66.09%
  1432258 requests in 30.02s, 355.40MB read
  Non-2xx or 3xx responses: 1432258
Requests/sec:  47713.01
Transfer/sec:     11.84MB
```

2. 手动配置内存参数 -Xmx4g -Xms4g    

```java
wangzhijie@bogon nio01 %  java -jar -Xmx4g -Xms4g -XX:+UseConcMarkSweepGC gateway-server-0.0.1-SNAPSHOT.jar
wangzhijie@bogon nio01 % wrk -c 40 -d30s http://localhost:8088
Running 30s test @ http://localhost:8088
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     4.12ms   14.43ms 252.77ms   94.36%
    Req/Sec    25.09k    11.78k   62.16k    67.40%
  1489082 requests in 30.06s, 369.50MB read
  Non-2xx or 3xx responses: 1489082
Requests/sec:  49544.69
Transfer/sec:     12.29MB
```

3. 手动配置内存参数 -Xmx256m -Xms256m

```java
wangzhijie@bogon nio01 %  java -jar -Xmx256m -Xms256m -XX:+UseConcMarkSweepGC gateway-server-0.0.1-SNAPSHOT.jar
wangzhijie@bogon nio01 % wrk -c 40 -d30s http://localhost:8088
Running 30s test @ http://localhost:8088
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.67ms   13.09ms 227.86ms   94.77%
    Req/Sec    23.37k    10.47k   55.33k    67.91%
  1383694 requests in 30.09s, 343.34MB read
  Non-2xx or 3xx responses: 1383694
Requests/sec:  45982.27
Transfer/sec:     11.41MB
```

4. 手动配置内存参数 -Xmx512m -Xms512m

```java
wangzhijie@bogon nio01 %  java -jar -Xmx512m -Xms512m -XX:+UseConcMarkSweepGC gateway-server-0.0.1-SNAPSHOT.jar
Running 30s test @ http://localhost:8088
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     5.10ms   16.36ms 287.19ms   93.38%
    Req/Sec    22.44k    11.41k   63.73k    65.31%
  1331036 requests in 30.09s, 330.28MB read
  Non-2xx or 3xx responses: 1331036
Requests/sec:  44238.54
Transfer/sec:     10.98MB
```



### 四、G1 GC

> 添加 GC 策略参数  -XX:+UseG1GC              

1. 手动配置内存参数 -Xmx1g -Xms1g          

   ```java
   wangzhijie@bogon nio01 % java -jar -Xmx1g -Xms1g -XX:+UseG1GC gateway-server-0.0.1-SNAPSHOT.jar
   wangzhijie@bogon nio01 % wrk -c 40 -d30s http://localhost:8088
   Running 30s test @ http://localhost:8088
     2 threads and 40 connections
     Thread Stats   Avg      Stdev     Max   +/- Stdev
       Latency     4.86ms   15.68ms 273.75ms   93.43%
       Req/Sec    22.95k    11.53k   62.71k    67.12%
     1353785 requests in 30.06s, 335.92MB read
     Non-2xx or 3xx responses: 1353785
   Requests/sec:  45037.34
   Transfer/sec:     11.18MB
   ```

2. 手动配置内存参数 -Xmx4g -Xms4g    

   ```java
   wangzhijie@bogon nio01 % java -jar -Xmx4g -Xms4g -XX:+UseG1GC gateway-server-0.0.1-SNAPSHOT.jar
   wangzhijie@bogon nio01 % wrk -c 40 -d30s http://localhost:8088
   Running 30s test @ http://localhost:8088
     2 threads and 40 connections
     Thread Stats   Avg      Stdev     Max   +/- Stdev
       Latency     3.80ms   12.66ms 237.47ms   94.11%
       Req/Sec    22.69k    11.68k   61.02k    65.92%
     1337529 requests in 30.10s, 331.89MB read
     Non-2xx or 3xx responses: 1337529
   Requests/sec:  44437.50
   Transfer/sec:     11.03MB
   ```

3. 手动配置内存参数 -Xmx256m -Xms256m

```java
wangzhijie@bogon nio01 % java -jar -Xmx256m -Xms256m -XX:+UseG1GC gateway-server-0.0.1-SNAPSHOT.jar
wangzhijie@bogon nio01 % wrk -c 40 -d30s http://localhost:8088
Running 30s test @ http://localhost:8088
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     4.84ms   14.22ms 248.23ms   92.78%
    Req/Sec    20.43k    11.26k   51.48k    61.82%
  1206388 requests in 30.08s, 299.35MB read
  Non-2xx or 3xx responses: 1206388
Requests/sec:  40108.13
Transfer/sec:      9.95MB
```

4. 手动配置内存参数 -Xmx512m -Xms512m

```java
wangzhijie@bogon nio01 % java -jar -Xmx512m -Xms512m -XX:+UseG1GC gateway-server-0.0.1-SNAPSHOT.jar
wangzhijie@bogon nio01 % wrk -c 40 -d30s http://localhost:8088
Running 30s test @ http://localhost:8088
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     4.32ms   14.36ms 212.88ms   93.78%
    Req/Sec    23.42k    11.59k   61.87k    66.61%
  1386413 requests in 30.02s, 344.02MB read
  Non-2xx or 3xx responses: 1386413
Requests/sec:  46177.88
Transfer/sec:     11.46MB
```



### 五、总结

> 无论内存参数如何，每秒的请求数，处理能力。并行 GC 最好。
>
> 总体而言，内置配置越大，处理能力越好。
>
> 并行GC，配置内存参数1g和4g相差不大。所以，采用适当的配置参数，一样能够提高并发处理能力。

1. 手动配置内存参数 -Xmx1g -Xms1g

```java
第一次测定 Requests/sec:  并行 GC 50243.26   CMS GC 47713.01   串行 GC 46673.99   G1 GC 45037.34    
第二次测定 Requests/sec:  并行 GC 46854.74
第二次测定 Requests/sec:  并行 GC 54238.89
```

2. 手动配置内存参数 -Xmx4g -Xms4g    

```java
第一次测定 Requests/sec:  并行 GC 49853.64   CMS GC 49544.69   串行 GC 48172.57   G1 GC 44437.50 
第二次测定 Requests/sec:  并行 GC 48185.5
第三次测定 Requests/sec:  并行 GC 45863.76
第四次测定 Requests/sec:  并行 GC 54259.01
```

3. 手动配置内存参数 -Xmx256m -Xms256m 

```java
第一次测定 Requests/sec: 并行 GC 46042.00   CMS GC 45982.27   串行 GC 46346.54   G1 GC 40108.13
第二次测定 Requests/sec: 并行 GC 47910.46
```

3. 手动配置内存参数 -Xmx512m -Xms512m

```java
第一次测定 Requests/sec: 并行 GC 49624.71   CMS GC 44238.54   串行 GC 48971.12   G1 GC 46177.88 
第二次测定 Requests/sec: 并行 GC 45975.01
```

