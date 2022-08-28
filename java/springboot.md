# SpringBoot

## 创建springboot_parent工程

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.6.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <packaging>pom</packaging>
    <groupId>com.hauchuan.springboot</groupId>
    <artifactId>springboot_parent</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springboot_parent</name>
    <description>Spring Boot Parent</description>
    <properties>
        <java.version>1.8</java.version>
    </properties>


    <dependencies>
        <!--项目热更新-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!--maven打包插件-->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

## 创建子web工程

### 1.pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>com.hauchuan.springboot</groupId>
		<artifactId>springboot_parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</parent>
	<groupId>com.huachuan.springboot</groupId>
	<artifactId>springboot_web</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>springboot_web</name>
	<description>Spring Boot Web</description>
	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
        <!--web-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
        <!--springboot集成测试功能-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
</project>
```

### 2.项目结构

![image-20220828174831629](D:\Summary\java\images\image-20220828174831629.png)

### 3.application.yml

```yaml
# 项目启动端口号
server:
  port: 8090
```

### 4.SpringbootWebApplication

```JAVA
package com.huachuan.springboot_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootWebApplication.class, args);
	}
}
```

### 5.EmpController

````JAVA
package com.huachuan.springboot_web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emp")
public class EmpController {
    @RequestMapping("/hi")
    public String SayHellow(){
        return "Helloword";
    }
}
````

### 6.启动 SpringbootWebApplication 

![image-20220828175908188](D:\Summary\java\images\image-20220828175908188.png)

### 7.访问 http://localhost:8090/emp/hi

![image-20220828180043090](D:\Summary\java\images\image-20220828180043090.png)

## web工程集成mybatis

以下是有修改的地方

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>com.hauchuan.springboot</groupId>
		<artifactId>springboot_parent</artifactId>
		<version>0.0.1-SNAPSHOT</version><!-- lookup parent from repository -->
	</parent>
	<groupId>com.huachuan.springboot</groupId>
	<artifactId>springboot_mybatis</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>springboot_mybatis</name>
	<description>spring boot集成mybatis</description>
	<properties>
		<java.version>1.8</java.version>
	</properties>
	<dependencies>
		<!--web-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<!--springboot集成测试功能-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
         <!--数据库连接池-->
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid-spring-boot-starter</artifactId>
			<version>1.2.3</version>
		</dependency>
		<!--jdbc-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
		<!--mybatis -->
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>2.1.4</version>
		</dependency>
		<!--mysql驱动-->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>

			<!-- Mybatis-Generator插件，自动生成代码 -->
			<plugin>
				<groupId>org.mybatis.generator</groupId>
				<artifactId>mybatis-generator-maven-plugin</artifactId>
				<version>1.3.5</version>
				<configuration>
					<configurationFile>${project.basedir}/src/main/resources/generatorConfig.xml</configurationFile>
					<verbose>true</verbose>
					<overwrite>true</overwrite>
				</configuration>
				<dependencies>
					<!--必須要引入数据库驱动-->
					<dependency>
						<groupId>mysql</groupId>
						<artifactId>mysql-connector-java</artifactId>
						<!--必须制定版本-->
						<version>8.0.22</version>
					</dependency>
				</dependencies>
			</plugin>
		</plugins>
	</build>
</project>
```

### 项目结构

![image-20220828182632187](D:\Summary\java\images\image-20220828182632187.png)

### application.yml

```yaml
# 数据源
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://hadoop102:3306/springboot_mybatis?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
    #   数据源其他配置
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true
    #   配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
    filters: stat,wall
    maxPoolPreparedStatementPerConnectionSize: 20
    useGlobalDataSourceStat: true
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
    #初始化时运行sql脚本
    schema: classpath:sql/schema.sql
    initialization-mode: always

    druid:
      #开启druid 监控台servlet
      stat-view-servlet:
        enabled: true
        login-username: admin
        loginPassword: 123456
      #开启druid 监控filter
      web-stat-filter:
        enabled: true
#设置mybatis
mybatis:
  mapper-locations: classpath:com/huachuan/springboot_web/mapper/*Mapper.xml
  #config-location: classpath:mybatis-config.xml
  typeAliasesPackage: com.huachuan.entity
  configuration:
    mapUnderscoreToCamelCase: true
```

### SpringbootWebApplication

```java
package com.huachuan.springboot_web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.huachuan.springboot_web.mapper")
public class SpringbootWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootWebApplication.class, args);
	}
}
```



### schema.sql

```sql
/*
Navicat MySQL Data Transfer

Source Server         : tuling
Source Server Version : 50554
Source Host           : localhost:3306
Source Database       : mybatis

Target Server Type    : MYSQL
Target Server Version : 50554
File Encoding         : 65001

Date: 2020-12-16 19:54:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('1', '经理');
INSERT INTO `dept` VALUES ('2', '普通员工');

-- ----------------------------
-- Table structure for emp
-- ----------------------------
DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of emp
-- ----------------------------
INSERT INTO `emp` VALUES ('4', '徐庶老师帅', '2020-12-16', '1');
INSERT INTO `emp` VALUES ('5', '张三', '2020-07-07', '2');
INSERT INTO `emp` VALUES ('6', '李四', '2020-07-07', '2');

```

### generatorConfig.xml

自动生成对应表的 entity包类、mapper包类、mapper.xml包类

```xml
<!DOCTYPE generatorConfiguration PUBLIC
        "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <!--如果需要使用 command的方式生成需要配置数据库驱动的jar包路径
    <classPathEntry location="指定数据驱动的磁盘路径"/>-->

    <!--context 生成上下文 配置生成规则
            id 随意写
           targetRuntime 生成策略
                MyBatis3DynamicSql 默认的，会生成 动态生成sql的方式（没有xml)
                MyBatis3 生成通用的查询，可以指定动态where条件
                MyBatis3Simple 只生成简单的CRUD
    -->
    <context id="simple" targetRuntime="MyBatis3Simple">

        
        <commentGenerator>
            <!--设置是否生成注释  true 不生成  注意： 如果不生成注释，下次生成代码就不会进行合并-->
            <property name="suppressAllComments" value="true"/>
        </commentGenerator>
        <!--数据源 -->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://hadoop102:3306/springboot_mybatis?serverTimezone=UTC"
                        userId="root"
                        password="123456"/>

        <!--pojo
        javaModelGenerator  java实体生成规则(POJO)
            targetPackage 生成到哪个包下
            targetProject 生成到当前文件的哪个相对路径下
        -->
        <javaModelGenerator targetPackage="com.huachuan.springboot_web.entity" targetProject="src/main/java"/>
        <!--mapper xml映射文件
            sqlMapGenerator mapper xml映射文件生成规则
            targetPackage 生成到哪个包下
            targetProject 生成到当前文件的哪个相对路径下
        -->
        <sqlMapGenerator targetPackage="com.huachuan.springboot_web.mapper" targetProject="src/main/resources"></sqlMapGenerator>
        <!--mapper接口
            javaClientGenerator mapper mapper接口生成规则
            type 指定生成的方式
                1.使用注解的方式生成
                2.使用接口绑定的方式生成（要配置sqlMapGenerator）
            targetPackage 生成到哪个包下
            targetProject 生成到当前文件的哪个相对路径下-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.huachuan.springboot_web.mapper" targetProject="src/main/java"/>


        <!--配置哪些表需要进行代码生成
        tableName 表名
        domainObjectName pojo类名
        mapperName 对应mapper接口的类名 和 mapper xml文件名
        -->
        <table tableName="emp" domainObjectName="Emp" mapperName="EmpMapper" />
        <table tableName="dept" domainObjectName="Dept" mapperName="DeptMapper" />
    </context>
</generatorConfiguration>
```

### EmpController

```java
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmpController {
    @Autowired
    private EmpMapper empMapper;

    @RequestMapping("/all")
    public List<Emp> getAll(){
        return empMapper.selectAll();
    }
}
```



### 启动顺序 

SpringbootWebApplication ：

​	根据schema.sql 生成数据库表   applacation.yml文件中 第一次initialization-mode: always 往后initialization-mode: never 

![image-20220828184533677](D:\Summary\java\images\image-20220828184533677.png)

启动mybatis-genorator生成 对应的entity mapper mapper.xml

![image-20220828184550007](D:\Summary\java\images\image-20220828184550007.png)

### 启动并访问

![image-20220828190532261](D:\Summary\java\images\image-20220828190532261.png)

