+++
title = "关于JAVA的性能测试"
description = "在斤斤计较的过程中提升"
date = 2020-06-10T21:19:12Z

[taxonomies]
tags = ["java", "benchmark", "jmh"]
categories = ["programming"]

+++

//TODO 还没写完
//最近更新 2020-06-15 19:35:51

今天在Feedly上读到一篇InfoQ上的[文章](https://xie.infoq.cn/article/cae1455171caa912d103a5b8e)，针对阿里「Java开发手册」中日志输出规范：

```code
【强制】在日志输出时，字符串变量之间的拼接使用占位符的方式。
说明：因为 String 字符串的拼接会使用 StringBuilder 的 append()方式，有一定的性能损耗。使用占位符仅是替换动作，可以有效提升性能。
正例：logger.debug("Processing trade with id: {} and symbol: {}", id, symbol);
```

中关于字符串拼接和占位符的性能提出质疑。

突然也想写点什么。

## 关于性能测试

作为经济适用程序员，斤斤计较是其中一个特性「以后说其他更多特性」，能一纳秒完成就不给两纳秒，如何使用更少的时间实现相同功能是需要经常锻炼的砍价能力。

如何评价效率/性能，从接口至逻辑都有相应的工具进行测试。接口的性能测试这儿不介绍了，如何针对我们写的逻辑也就是一段代码或者一个方法进行测试呢，写N多年前写PHP的时候最常见写法是

```php
$count = 1000000;
$begin = microtime(true);
for ($i = 0; $i < $count; $i++) { 
    # code...
}
$total_seconds = microtime(true) - $begin;
$qps = round($count / $total_seconds, 2);
```

但到了JAVA发现这么写，得到的结果前面的几次性能明显差于后来的，比如

```java
public class Sample001 {
    public static void main(String[] args) {
        for (int round = 0; round < 10; round++) {
            long begin = System.nanoTime();
            int count = 100_000;
            for (int i = 0; i < count; i++) {
                cat();
            }
            System.out.printf("Round %03d, finished %d in %2.3f ms\n", round, count, (System.nanoTime() - begin) / 1_000_000.0);
        }
    }

    private static long cat() {
        String a = "";
        for (int i = 0; i < 10; i++) {
            a += i;
        }
        return a.length();
    }
}

```

执行10轮的结果如下：

```text
# javac Sample001.java && java -Xms1G -Xmx1G Sample001
Round 000, finished 100000 in 40.474 ms
Round 001, finished 100000 in 37.225 ms
Round 002, finished 100000 in 36.471 ms
Round 003, finished 100000 in 35.715 ms
Round 004, finished 100000 in 34.391 ms
Round 005, finished 100000 in 13.165 ms
Round 006, finished 100000 in 14.205 ms
Round 007, finished 100000 in 14.137 ms
Round 008, finished 100000 in 17.646 ms
Round 009, finished 100000 in 18.273 ms
```

大概五次以后性能从约40ms提升至15ms，把代码改一下`int count = 500_000;`验证一下：

```text
# javac Sample001.java && java -Xms1G -Xmx1G Sample001
Round 000, finished 500000 in 178.598 ms
Round 001, finished 500000 in 68.093 ms
Round 002, finished 500000 in 58.356 ms
Round 003, finished 500000 in 58.449 ms
Round 004, finished 500000 in 75.782 ms
Round 005, finished 500000 in 60.858 ms
Round 006, finished 500000 in 79.830 ms
Round 007, finished 500000 in 77.924 ms
Round 008, finished 500000 in 63.239 ms
Round 009, finished 500000 in 63.782 ms
```

为何呢？这就是[JIT](https://www.ibm.com/developerworks/cn/java/j-lo-just-in-time/index.html)的功劳了。

然后[JMH](https://openjdk.java.net/projects/code-tools/jmh/)就登场了，JMH有一个预热（Warmup）的过程，让测试结果更接近真实（当然也许真实使用时并没有达到JIT的阈值，下次分析一下JIT的具体实现和触发）。

## 字符串拼接或占位符

```sh
mvn archetype:generate \
  -DinteractiveMode=false \
  -DarchetypeGroupId=org.openjdk.jmh \
  -DarchetypeArtifactId=jmh-java-benchmark-archetype \
  -DgroupId=org.sample \
  -DartifactId=sample002 \
  -Dversion=1.0
```

## 关于DEBUG日志输出

前些天在review一个同事的代码时发现很多这种写法

```java
if (log.isDebugEnabled()) {
    log.debug("Hello world.");
}
```

## 结论
