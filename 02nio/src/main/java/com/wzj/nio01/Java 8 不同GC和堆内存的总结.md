## Java 8 不同GC和堆内存的总结

### 一、并行 GC

> 添加 GC 策略参数  -XX:+UseParallelGC ，或 GC策略删掉不要。Java8默认的解决策略，并行GC。

1.  默认内存配置

```java
 java -XX:+PrintGCDetails com.wzj.production.nio01.GCLogAnalysis
 
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

