## 安装Zipkin

在 SpringBoot 2.x 版本后就不推荐自定义 zipkin server 了，推荐使用官网下载的 jar 包方式

也就是说我们不需要编写一个mogu-zipkin服务了，而改成直接启动jar包即可


**下载地址：**

```
https://search.maven.org/remote_content?g=io.zipkin.java&a=zipkin-server&v=LATEST&c=exec
```

**运行：**

```
java -jar zipkin-server-2.12.5-exec.jar

# 或集成RabbitMQ

java -jar zipkin-server-2.12.5-exec.jar --zipkin.collector.rabbitmq.addresses=127.0.0.1
```
什么是ZipKin？
```$xslt
Zipkin 是一个根据 Google 发表的论文“ Dapper” 进行开源实现的分布式跟踪系统。 Dapper是Google 公司内部的分布式追踪系统，用于生产环境中
的系统分布式跟踪。 Google在其论文中对此进行了解释，他们“构建了Dapper，以向Google开发人员提供有关复杂分布式系统行为的更多信息。”从不同角
度观察系统对于故障排除至关重要，在系统复杂且分布式的情况下更是如此。
Zipkin可帮助您准确确定对应用程序的请求在哪里花费了更多时间。无论是代码内部的调用，还是对另一服务的内部或外部API调用，您都可以对系统进行检
测以共享上下文。微服务通常通过将请求与唯一ID相关联来共享上下文。此外，在系统太复杂的情况下，您可以选择仅使用样本追踪 (sample trace ，一
种占用资源比例更低的追踪方式) 来减少系统开销。
```