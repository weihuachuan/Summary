# 离线数仓

## 执行流程

![image-20221106224424476](images/image-20221106224424476.png)

## 定时调度时候 dt

![image-20221106230120628](images/image-20221106230120628.png)

## 造业务数据

![image-20221106230645925](images/image-20221106230645925.png)

1. 修改application.properties 中的日期

2. java - jar gmall2020-mock-db-2020-05-18.jar

## 造日志数据

1. cluster.sh start
2. dt.sh 2022-11-07
3. lg.sh

## azkaban 执行任务

![image-20221106231410968](images/image-20221106231410968.png)
