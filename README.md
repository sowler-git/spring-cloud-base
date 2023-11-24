#Spring Cloud 服务
这是一个以Spring Cloud Alibaba为主要组件，整合的一套微服务框架。

#版本描述
###软件版本

JDK 1.8.0_271

Apache Maven 3.5.0

Mysql 8.0.32

Redis 6.0.8

Spring Boot 版本：2.3.2.RELEASE

Spring Cloud 版本： Hoxton.SR9

Spring Cloud Alibaba Version 版本： 2.2.6.RELEASE
##
###组件版本

Nacos 版本 1.4.2 

https://nacos.io/zh-cn/

https://github.com/alibaba/nacos

Dubbo Admin 版本 0.3.0

https://github.com/apache/dubbo-admin

Dubbo 版本2.7.8 使用jar：spring-cloud-starter-dubbbo:2.2.6.RELEASE

https://cn.dubbo.apache.org/zh-cn/


Sentinel 版本1.8.1 使用jar：spring-cloud-starter-alibaba-sentinel:2.2.6.RELEASE

https://sentinelguard.io/zh-cn/index.html

https://github.com/alibaba/Sentinel

Seata 版本1.3.0 使用jar：spring-cloud-starter-alibaba-seata:2.2.6.RELEASE

https://seata.io/zh-cn/

https://github.com/seata/seata

ZipKin 版本 2.12.9 使用jar：spring-cloud-starter-zipkin:2.2.6.RELEASE

启动端口：9411

https://github.com/openzipkin/zipkin

#监控工具

Prometheus

版本号：prometheus-2.44.0-rc.0

官网：https://prometheus.io/download/

github：https://github.com/prometheus

下载启动成功访问：http://localhost:9090/graph

Grafana UI监控

版本号：grafana-10.1.0

官网：https://grafana.com/ 

仪表盘下载地址：https://grafana.com/grafana/dashboards/

下载启动成功访问：http://localhost:3000 

默认账号密码：admin/admin

模板编号：

主机基础监控(cpu，内存，磁盘，网络)模板ID： 9276 

JVM模板ID：4701

SpringBoot Dashboard 模板ID:  12900

##
#模块说明
common-module 基础公共模块：存放一些实体文件和其他公共类

framework-module SpringBoot配置文件，存放一些第三方配置类

interface-module 通用业务接口存放。如：dubbo接口

gateway-module 服务网关

user-module 用户管理

order-module 订单管理


##项目打包

执行maven命令一键打包：

````
mvn clean package -Dmaven.test.skip=true
````
