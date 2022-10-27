# MySQL中的列转行

##mysql中的列转行

在工作中遇到的一个MySQL列转行的统计：

### 场景
用户访问app时会跳出标签选择页面让用户选择喜欢的标签，在数据库中记录的是数组样式的字符串，数据样式大致如下：

```
id	user_id	like_tags	create_time
1	1101	[“八卦”,“数码”,“财经”]	2020-09-01 09:19:52
2	1102	[“数码”]	2020-09-01 09:23:52
3	1103	[“数码”,“影视”]	2020-09-01 09:29:52
…	…	…	…
```
要统计每个标签选择的人数有多少。

### 分析与解决
like_tags字段存放的是用户喜好的数据，这个问题是数组的字符串，需要列转行，然后进行统计。在网上搜了一下，MySQL没有数组的操作函数和explode函数，有些需要建存储过程和函数来进行统计，但生产上权限的限制问题没法随意建存储过程，而且这个统计看上去很简单。

大致分析了一下数据，进入app选取的标签不会超过8个，最多like_tags会有6个数据，利用一个临时表可以解决问题。

在个人电脑MySQL上建表测试一下

### 创建表
```
create table user_tags(
id int
,like_tags varchar(50)
);


### 添加数据
insert into user_tags values (1,'["八卦","数码","财经"]');
insert into user_tags values (2,'["数码"]');
insert into user_tags values (3,'["数码","影视"]');
```


### 查询select * from user_tags验证插入的数据
```

+------+------------------------+
| id   | like_tags              |
+------+------------------------+
|    1 | ["八卦","数码","财经"] |
|    2 | ["数码"]               |
|    3 | ["数码","影视"]        |
+------+------------------------+
```

本来想使用一下with as建临时表，发现不支持，就直接用union all然后子查询的临时表了

```
SELECT
  SUBSTRING_INDEX( SUBSTRING_INDEX( a.clean_like_tags, ',', b.help_id + 1 ), ',',- 1 ) AS NAME,
  COUNT( 1 ) 
FROM
  (
  SELECT REPLACE
    ( REPLACE ( REPLACE ( like_tags, '[', '' ), ']', '' ), '"', '' ) AS clean_like_tags 
  FROM
    user_tags 
  ) a
LEFT JOIN (
  SELECT
    0 AS help_id UNION ALL
  SELECT
    1 UNION ALL
  SELECT
    2 UNION ALL
  SELECT
    3 UNION ALL
  SELECT
    4 UNION ALL
  SELECT
    5 
  ) b
ON b.help_id < ( LENGTH( a.clean_like_tags ) - LENGTH( REPLACE ( a.clean_like_tags, ',', '' ) ) + 1 ) 
GROUP BY
  NAME;
```
结算结果
```
+------+------------+
| NAME | COUNT( 1 ) |
+------+------------+
| 八卦 |          1 |
| 影视 |          1 |
| 数码 |          3 |
| 财经 |          1 |
+------+------------+
```