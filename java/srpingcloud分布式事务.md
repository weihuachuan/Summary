# 分布式事务seata
## seata安装
1. Windows 环境下载 seata 1.4.2
2. 将下载的压缩包解压，找到 config 目录里面的 registry.conf 文件，使用nacos作为注册中心和配置中心，将下面内容复制到该文件中
   
``` json
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  type = "nacos"

   nacos {
    application = "seata-server"  		# seata服务注册在nacos中的服务名
    serverAddr = "10.197.24.79:8848"		# 填写自己的nacos地址
    group = "DEFAULT_GROUP"		
    namespace = "nacos-test"
    cluster = "SZ"	#	自定义集群名称
    username = "nacos"
    password = "nacos"
  }
  
}

config {
  # file、nacos 、apollo、zk、consul、etcd3
    type = "nacos"

  nacos {
    serverAddr = "10.197.24.79:8848"	# 填写自己的nacos地址
    namespace = "nacos-test"
    group = "DEFAULT_GROUP"
    username = "nacos"
    password = "nacos"
  }

}
```
3. 创建 seata 所需要的数据库，用于记录事务回滚操作。
```sql
-- 先创建数据库，名称为seata_server，执行下面sql语句创建出表。
-- -------------------------------- The script used when storeMode is 'db' --------------------------------
-- the table to store GlobalSession data
CREATE DATABASE IF NOT EXISTS seata_server;

CREATE TABLE IF NOT EXISTS `global_table`
(
    `xid`                       VARCHAR(128) NOT NULL,
    `transaction_id`            BIGINT,
    `status`                    TINYINT      NOT NULL,
    `application_id`            VARCHAR(32),
    `transaction_service_group` VARCHAR(32),
    `transaction_name`          VARCHAR(128),
    `timeout`                   INT,
    `begin_time`                BIGINT,
    `application_data`          VARCHAR(2000),
    `gmt_create`                DATETIME,
    `gmt_modified`              DATETIME,
    PRIMARY KEY (`xid`),
    KEY `idx_status_gmt_modified` (`status` , `gmt_modified`),
    KEY `idx_transaction_id` (`transaction_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- the table to store BranchSession data
CREATE TABLE IF NOT EXISTS `branch_table`
(
    `branch_id`         BIGINT       NOT NULL,
    `xid`               VARCHAR(128) NOT NULL,
    `transaction_id`    BIGINT,
    `resource_group_id` VARCHAR(32),
    `resource_id`       VARCHAR(256),
    `branch_type`       VARCHAR(8),
    `status`            TINYINT,
    `client_id`         VARCHAR(64),
    `application_data`  VARCHAR(2000),
    `gmt_create`        DATETIME(6),
    `gmt_modified`      DATETIME(6),
    PRIMARY KEY (`branch_id`),
    KEY `idx_xid` (`xid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- the table to store lock data
CREATE TABLE IF NOT EXISTS `lock_table`
(
    `row_key`        VARCHAR(128) NOT NULL,
    `xid`            VARCHAR(128),
    `transaction_id` BIGINT,
    `branch_id`      BIGINT       NOT NULL,
    `resource_id`    VARCHAR(256),
    `table_name`     VARCHAR(32),
    `pk`             VARCHAR(36),
    `status`         TINYINT      NOT NULL DEFAULT '0' COMMENT '0:locked ,1:rollbacking',
    `gmt_create`     DATETIME,
    `gmt_modified`   DATETIME,
    PRIMARY KEY (`row_key`),
    KEY `idx_status` (`status`),
    KEY `idx_branch_id` (`branch_id`),
    KEY `idx_xid_and_branch_id` (`xid` , `branch_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
```
4. 每一个微服务使用的数据库都需要一个 undo_log 表，下面是两个微服务需要的数据库
``` sql
-- -------------------------------- 账户微服务的建表语句 --------------------------------
-- 先创建账户数据库，名称为 seata_account
CREATE DATABASE IF NOT EXISTS seata_account;
-- 回滚日志表
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb3;
-- 账户表
DROP TABLE IF EXISTS `account_tbl`;
CREATE TABLE `account_tbl` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `money` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;


-- -------------------------------- 订单微服务的建表语句 --------------------------------
-- 先创建账户数据库，名称为 seata_order
CREATE DATABASE IF NOT EXISTS seata_order;
-- 回滚日志表
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb3;
-- 订单表
DROP TABLE IF EXISTS `order_tbl`;
CREATE TABLE `order_tbl` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `product` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `count` int DEFAULT '0',
  `money` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8mb3;
```
5. 启动seata
   在 bin 目录中双击运行 seata-server.bat 。需要 java 环境支持
> 此处可能出现闪退情况，我们可以在 seata-server.bat 文件末尾更改下面代码
 ```shell
	if "%FORCE_EXIT_ON_ERROR%" == "on" (
		if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
	)
	cmd  # 在这个地方添加cmd，就不会出现闪退可以看到错误信息。
	exit /B %ERROR_CODE%
 ```
 6. 启动成功后可以在 nacos 服务管理中找到，我这里启动了两个集群（另一个只需要修改 regist.conf 的集群名并指定端口启动就行），注意集群名称在后面nacos配置中心需要使用到。
## 服务搭建
我们需要为项目中每一个微服务都配置 seata。下面以账户微服务为例，其他微服务配置基本一样。

1. 添加seata依赖，由于我们 seata 使用的是 1.4.2 版本，因此依赖也选择 1.4.2 版本
```xml
<!--        seata-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
            <exclusions>
                <!-- 要与seata服务端版本一直,所以把自带的替换掉 -->
                <exclusion>
                    <groupId>io.seata</groupId>
                    <artifactId>seata-spring-boot-starter</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>io.seata</groupId>
            <artifactId>seata-spring-boot-starter</artifactId>
            <version>1.4.2</version>
        </dependency>
```
2. 在nacos的配置列表中添加一个名叫 seataServer.properties 分组默认
DEFAULT_GROUP 的配置，作为seata的配置中心
```yml
#For details about configuration items, see https://seata.io/zh-cn/docs/user/configurations.html
#Transport configuration, for client and server
transport.type=TCP
transport.server=NIO
transport.heartbeat=true
transport.enableTmClientBatchSendRequest=false
transport.enableRmClientBatchSendRequest=true
transport.enableTcServerBatchSendResponse=false
transport.rpcRmRequestTimeout=30000
transport.rpcTmRequestTimeout=30000
transport.rpcTcRequestTimeout=30000
transport.threadFactory.bossThreadPrefix=NettyBoss
transport.threadFactory.workerThreadPrefix=NettyServerNIOWorker
transport.threadFactory.serverExecutorThreadPrefix=NettyServerBizHandler
transport.threadFactory.shareBossWorker=false
transport.threadFactory.clientSelectorThreadPrefix=NettyClientSelector
transport.threadFactory.clientSelectorThreadSize=1
transport.threadFactory.clientWorkerThreadPrefix=NettyClientWorkerThread
transport.threadFactory.bossThreadSize=1
transport.threadFactory.workerThreadSize=default
transport.shutdown.wait=3
transport.serialization=seata
transport.compressor=none

#Transaction routing rules configuration, only for the client
# 事务分组 test-group需要与项目中的事务分组配置保持一致，同时SH集群需要和seata服务端的集群相同
service.vgroupMapping.test-group=SH

#Transaction rule configuration, only for the client
client.rm.asyncCommitBufferLimit=10000
client.rm.lock.retryInterval=10
client.rm.lock.retryTimes=30
client.rm.lock.retryPolicyBranchRollbackOnConflict=true
client.rm.reportRetryCount=5
client.rm.tableMetaCheckEnable=false
client.rm.tableMetaCheckerInterval=60000
client.rm.sqlParserType=druid
client.rm.reportSuccessEnable=false
client.rm.sagaBranchRegisterEnable=false
client.rm.sagaJsonParser=fastjson
client.rm.tccActionInterceptorOrder=-2147482648
client.tm.commitRetryCount=5
client.tm.rollbackRetryCount=5
client.tm.defaultGlobalTransactionTimeout=60000
client.tm.degradeCheck=false
client.tm.degradeCheckAllowTimes=10
client.tm.degradeCheckPeriod=2000
client.tm.interceptorOrder=-2147482648
client.undo.dataValidation=true
client.undo.logSerialization=jackson
client.undo.onlyCareUpdateColumns=true
server.undo.logSaveDays=7
server.undo.logDeletePeriod=86400000
client.undo.logTable=undo_log
client.undo.compress.enable=true
client.undo.compress.type=zip
client.undo.compress.threshold=64k
#For TCC transaction mode
tcc.fence.logTableName=tcc_fence_log
tcc.fence.cleanPeriod=1h

#Log rule configuration, for client and server
log.exceptionRate=100

#Transaction storage configuration, only for the server. The file, DB, and redis configuration values are optional.
# 使用db进行事务存储
store.mode=db
store.lock.mode=db
store.session.mode=db
#Used for password encryption
store.publicKey=


#These configurations are required if the `store mode` is `db`. If `store.mode,store.lock.mode,store.session.mode` are not equal to `db`, you can remove the configuration block.
store.mode=db	
store.db.datasource=druid
store.db.dbType=mysql
store.db.driverClassName=com.mysql.cj.jdbc.Driver
store.db.url=jdbc:mysql://10.175.94.80:7000/seata_server?useUnicode=true&rewriteBatchedStatements=true&serverTimezone=UTC	#配置自己的数据库，此处使用的是文章开头创建的叫 seata_server 数据库
store.db.user=root
store.db.password=123456
store.db.minConn=5
store.db.maxConn=30
store.db.globalTable=global_table
store.db.branchTable=branch_table
store.db.queryLimit=100
store.db.lockTable=lock_table
store.db.maxWait=5000

#Transaction rule configuration, only for the server
server.recovery.committingRetryPeriod=1000
server.recovery.asynCommittingRetryPeriod=1000
server.recovery.rollbackingRetryPeriod=1000
server.recovery.timeoutRetryPeriod=1000
server.maxCommitRetryTimeout=-1
server.maxRollbackRetryTimeout=-1
server.rollbackRetryTimeoutUnlockEnable=false
server.distributedLockExpireTime=10000
server.xaerNotaRetryTimeout=60000
server.session.branchAsyncQueueSize=5000
server.session.enableBranchAsyncRemove=true

#Metrics configuration, only for the server
metrics.enabled=false
metrics.registryType=compact
metrics.exporterList=prometheus
metrics.exporterPrometheusPort=9898
```
3. 配置文件 bootstrap.yml
```yml
spring:
  application:
    name: userservice  # nacos的服务名称
  profiles:
    active: dev # 开发环境，这里是dev
  datasource:
    password: 123456
    username: root
    url: jdbc:mysql://10.175.94.80:7000/seata_account?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC
    driver-class-name: com.mysql.cj.jdbc.Driver
  cloud:
    nacos:
      discovery: #服务注册与发现
        server-addr: localhost:8848 #nacos地址
        namespace: nacos-test #指定命名空间 可以删掉namespace不写默认public
        cluster-name: SZ
      config:
        server-addr: localhost:8848 #nacos地址
        file-extension: yml  # 文件后缀名
        namespace: nacos-test #指定命名空间 可以删掉namespace不写默认public



mybatis:
  mapper-locations: classpath:mapper/*Mapper.xml
  type-aliases-package: com.demo.entity

# seata 配置 在nacos配置中心写了这里可以注释掉
seata:
  enabled: true
  application-id: ${spring.application.name}
  # 关闭自动代理数据源
  enable-auto-data-source-proxy: false
  #  # 使用哪个事务组
  tx-service-group: test-group		#需要和nacos配置中心 seataServer.properties 文件中service.vgroupMapping.test-group 一致
  registry:
    type: nacos
    nacos:
      server-addr: localhost:8848
      application: seata-server
      group: DEFAULT_GROUP
      namespace: nacos-test
  config:
    type: nacos
    nacos:
      server-addr: localhost:8848
      group: DEFAULT_GROUP
      data-id: seataServer.properties	#指定nacos配置中心 seataServer.properties 文件
      namespace: nacos-test


orderservice:
  ribbon:
    NFLoadBalancerRuleClassName: com.alibaba.cloud.nacos.ribbon.NacosRule # 负载均衡规则


server:
  port: 8081  #指定端口

feign:
  hystrix:
    enabled: true
  okhttp:
    enabled: true
```
4. 配置数据源代理
确保 bootstrap.yml 中 enable-auto-data-source-proxy: false
新建一个数据源配置类

```java
@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    /**
     * 需要将 DataSourceProxy 设置为主数据源，否则事务无法回滚
     *
     * @param druidDataSource The DruidDataSource
     * @return The default datasource
     */
    @Primary
    @Bean("dataSource")
    public DataSource dataSource(DruidDataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

}
```
然后在主类上添加@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})取消自动代理
5. 在调用其他服务接口的位置加上@GlobalTransactional注解，即可实现全局事务代理。
```java
    @GlobalTransactional
//    @Transactional
    public void create(String userId, String product, Integer count) {
        log.info("Seata全局事务id=================>{}", RootContext.getXID());

        BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));

        Order order = new Order();
        order.setUserId(userId);
        order.setProduct(product);
        order.setCount(count);
        order.setMoney(orderMoney);

        orderMapper.create(order);
        userFeignClient.decrease(userId, orderMoney);

    }
```
## 遇到的一些问题
1. no available service found in cluster ‘xxx’, please make sure registry config correct and keep your seata server running
2. io.seata.common.exception.FrameworkException: No available service
> 事务分组要与项目中的事务保持一致，bootstrap.yml 中seata: 
> tx-service-group: test-group 要与seataserver.properties中的sercvice.vgroupMapping.test-group=SH保持一致。
1. global_table 表中有数据但是 undo_log 没有数据
这时候需要配置手动数据源代理，关闭自动代理。上文有提及。   
