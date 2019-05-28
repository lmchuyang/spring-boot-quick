# lmc
springBootCloud
springBoot-hystrix
springboot-helloworld
这三个项目 都是测试springcloud相关功能实现，可以分开或者组合在一起使用

springcloud服务的相关项目测试



ElasticsearchDemo  单纯的利用客户端实现 增， 删， 改，查，以及批量操作等常用方法
springboot-elasticsearch-demo  项目是在springboot配置项目下使用，更好的集成其它框架更有利于开发

elasticsearch  大部分能跑的代码都 放在测试包下面，
每一个方法都 经过 测试通过后，才上传，初期开发的时候，这些DEMO还是很有用处
在测试目录下新增加了 距离的计算方式和附近人的查询，利用geoHash方式实现



# hadoop-elasticsearch-big
大数据相关的代码测试，后期会更新elasticsearch+flink+hbase+hive

欢迎大家留言
Tip: 技术更新换代太快，本仓库仅做参考，自己的项目具体使用哪个版本还需谨慎思考~(不推荐使用最新的版本，推荐使用(最新-1|2)的版本，会比较稳定)

**前言**
-
   日常工作中遇到的一些问题，自己业余时间研究的项目功能等，希望能够存放起来，以便以后用到的时候，方便查阅，也更好了解原理 ，
   所以目前建了这个仓库就是将自己平时遇到的和学习到的东西整合在一起，这个地址会作为一直更新的地方，学的东西太多，包括后面的数据仓库搭建等一些相关文档。
   
目前整理所有项目的功能点，以及说明

- ├── SpringBootRabbitMQ
- ├── learn-springboot-Sharding-Sphere
- ├── learn-springboot-aysnc
- ├── learn-springboot-jvm
- ├── learn-springboot-mybaits-rw
- ├──learn-springboot-mysqlToHbase
- ├── learn-springboot-quartz
- ├── learn-springboot-redis
- ├── learn-springboot-shading-spherejdbc
- ├── learn-springboot-websocket
- ├── springboot-elasticsearch-demo
- ├── springbootHbaseDemo
- ├──learn-springboot-xxxxx



**learn-springboot-xxxxx**
-
- 基于springboot快速学习示例，后面版本的代码都是以这个为基础

**SpringBootRabbitMQ**
-
-   RabbitMQ相关的代码示例，包括 延迟队列，消息方式 一对一，一对多，和广播模式 

**learn-springboot-Sharding-Sphere**
-  
-    基于-Sharding-Sphere 对数据库主从 实现读写分离

**learn-springboot-shading-spherejdbc**
-
-    基于shading-jdbc 实现了分库分表的基本配置功能，
   
**learn-springboot-aysnc**
-
- 主要是一些线程池的使用方式，基本线程涌出，异步线程池，以及如何返回线程执行结果

**learn-springboot-jvm**
-
-   JVM的参数说明，以及一些调优方式

**learn-springboot-mybaits-rw**
 -
-  利用开源插件， 在mybaits层实现了读写分离，强制读主库等，这种实现方式比spherejdbc好的地方是，支持原生SQL，所有MYSQL支持的语句它都支持，大部使用场景应该也是这样实现

**learn-springboot-mysqlToHbase**
-
- JAVA操作Hbase 以及MYSQL 主要学习的是利用阿里开源插件cannl，监听MYSQL的binlog日志，利用主从原理，把MYSQL的数据实时插入到hbase里， 有实时插入功能，也有线下查询插入两种方式， 以及把hbase 里面的数据插入到mysql里等，一些示例

**learn-springboot-quartz**
-
-  基于spring 自带的定时任务和quartz配置组合实现 的定时任务，定制下单规则自动启动定时任务执行操作，线上添加定时任务等
 
**learn-springboot-redis**
-
 主要实现了redis的基本功能 还有GEOHASH地理位置的高级功能，基于redis的分布式锁实现方式，有redisson redlock 以及LUA脚本实现的分工式锁 和秒杀场景的商品扣减保证不超卖。
 
**learn-springboot-websocket**
-
- 简单的实现了后台推消息给前端，界面刷新不影响，简单的实现了聊天功能，监听ID消息的方式推送

**springboot-elasticsearch-demo**
- 
- 整合ES 实现了基本的增删改查接口，以及后面的ELK日志分析系统搭建，包括地理位置的功能，和地图显示功能，实现站点，附近的人等功能。

**springbootHbaseDemo**
-
- springboot结合Hbase相关功能，实现 增删改查等功能

