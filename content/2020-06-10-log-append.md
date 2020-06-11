+++
title = "关于JAVA的性能测试"
description = "在斤斤计较的过程中提升"
date = 2020-06-10T21:19:12Z

[taxonomies]
tags = ["java", "benchmark", "jmh"]
categories = ["programming"]

+++

//TODO 还没写完
//最近更新 2020-06-11 20:59:08

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

如何评价效率/性能，从大的接口到小的一段逻辑都有相应的工具进行测试。

## 字符串拼接或占位符

## 关于DEBUG日志输出

前些天在review一个同事的代码时发现很多这种写法

```java
if (log.isDebugEnabled()) {
    log.debug("Hello world.");
}
```

## 结论
