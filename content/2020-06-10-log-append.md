+++
title = "读'驳《阿里「Java开发手册」中的1个bug》？'有感"
description = "我们在争论的过程中学习（仅限于技术领域）"
date = 2020-06-10T21:19:12Z

[taxonomies]
tags = ["java", "benchmark"]
categories = ["programming"]

+++

//TODO 还没写完

今天在Feedly上读到一批InfoQ的文章[驳《阿里「Java开发手册」中的1个bug》？](https://xie.infoq.cn/article/cae1455171caa912d103a5b8e)，文中的结论是"*本文我们测试了读者提出质疑的字符串拼接和占位符的性能评测，发现占位符方式性能高的观点依然无从考证，所以我们的基本看法还是，使用占位符的方式更加优雅，可以通过更少的代码实现更多的功能；至于性能方面，只能说还不错，但不能说很优秀。*"

突然想写点什么，本着技术人员研究的心态做更详细的分析。

## 性能测试

## 拼接还是占位符

## DEBUG日志输出

前些天在review一个同事的代码时发现很多这种写法

```java
if (log.isDebugEnabled()) {
    log.debug("Hello world.");
}
```

## 结论
