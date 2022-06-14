# sql 实例

## Orcale

### oracle分割字符串转多行

```sql
create table test
(
  pid           VARCHAR2(50),
  user_name     VARCHAR2(200)
)
```



```sql
insert into test(pid, user_name) values ('1', 'a,b,c');
insert into test(pid, user_name) values ('2', 'd,e,f');
insert into test(pid, user_name) values ('3', 'g,h,i,j,k');
```

查询sql

```sql
select t.pid, regexp_substr(t.user_name, '[^,]+', 1, level) user_name, level
  from test t
connect by prior t.pid = t.pid
       and level <= regexp_count(t.user_name, '[^,]+')
       and prior DBMS_RANDOM.VALUE() IS NOT NULL
```

prior t.pid = t.pid 和 and prior DBMS_RANDOM.VALUE() IS NOT NULL 一定要有, 不然逗号分割的多了会非常慢

prior t.pid = t.pid 标记关系

prior DBMS_RANDOM.VALUE() IS NOT NULL 解决死循环
