## 1查询课程编号为01的课程比02的课程成绩高的所有学生的学号（重点）：

```sql
select student.*,a.s_score as 01_score,b.s_score as 02_score
from student
  join score a on student.s_id=a.s_id and a.c_id='01'
  left join score b on student.s_id=b.s_id and b.c_id='02'
where  a.s_score>b.s_score;
```

## 2、查询01课程比02课程成绩低的学生的信息及课程分数（重点）:
```sql
select student.*,a.s_score as 01_score,b.s_score as 02_score
from student
join score a on student.s_id=a.s_id and a.c_id='01'
left join score b on student.s_id=b.s_id and b.c_id='02'
where a.s_score<b.s_score;
```
# 3、查询平均成绩大于等于60分的同学的学生编号和学生姓名和平均成绩（重点）:
```sql
select  student.s_id,student.s_name,round(avg (score.s_score),1) as avgScorse from student
join score on student.s_id = score.s_id
group by student.s_id,student.s_name
having avg (score.s_score) >= 60;
```
## 4、查询平均成绩小于60分的同学的学生编号和学生姓名和平均成绩:
```sql
select  score.s_id,student.s_name,round(avg (score.s_score),1) as avgScore from student
inner join score on student.s_id=score.s_id
group by score.s_id,student.s_name
having avg (score.s_score) < 60
union all
select  s2.s_id,s2.s_name,0 as avgScore from student s2
where s2.s_id not in
    (select distinct sc2.s_id from score sc2);
```
## 5、查询所有同学的学生编号、学生姓名、选课总数、所有课程的总成绩:
```sql
select student.s_id,student.s_name,(count(score.c_id) )as total_count,sum(score.s_score)as total_score
from student
left join score on student.s_id=score.s_id
group by student.s_id,student.s_name ;
```
## 6、查询李姓老师的数量:
```sql
select t_name,count(1) from teacher  where t_name like '李%' group by t_name;
```
## 7、查询学过张三老师授课的同学的信息(重点):
```sql
select student.* from student
join score on student.s_id =score.s_id
join  course on course.c_id=score.c_id
join  teacher on course.t_id=teacher.t_id and t_name='张三';
```
## 8、查询没学过张三老师授课的同学的信息（重点）:
```sql
select student.* from student
left join (select s_id from score
      join  course on course.c_id=score.c_id
      join  teacher on course.t_id=teacher.t_id and t_name='张三')tmp
on  student.s_id =tmp.s_id
where tmp.s_id is null;
```
## 9、查询学过编号为01并且也学过编号为02的课程的同学的信息（重点）:
```sql
select * from student
join (select s_id from score where c_id =1 )tmp1
    on student.s_id=tmp1.s_id
join (select s_id from score where c_id =2 )tmp2
    on student.s_id=tmp2.s_id;
```
## 10、查询学过编号为01但是没有学过编号为02的课程的同学的信息（重点）:
```sql
select student.* from student
join (select s_id from score where c_id =1 )tmp1
    on student.s_id=tmp1.s_id
left join (select s_id from score where c_id =2 )tmp2
    on student.s_id =tmp2.s_id
where tmp2.s_id is null;
```
## 11、查询没有学全所有课程的同学的信息（重点）:
```sql
select student.* from student
join (select count(c_id)num1 from course)tmp1
left join(
      select s_id,count(c_id)num2
        from score group by s_id)tmp2
on student.s_id=tmp2.s_id and tmp1.num1=tmp2.num2
where tmp2.s_id is null;
```
## 12、查询至少有一门课与学号为01的同学所学相同的同学的信息（重点）:
```sql
select student.* from student
join (select c_id from score where score.s_id=01)tmp1
join (select s_id,c_id from score)tmp2
    on tmp1.c_id =tmp2.c_id and student.s_id =tmp2.s_id
where student.s_id  not in('01')
group by student.s_id,s_name,s_birth,s_sex;
```
## 13、查询和01号的同学学习的课程完全相同的其他同学的信息（重点）:
```sql
select student.*,tmp1.course_id from student
join (select s_id ,concat_ws('|', collect_set(c_id)) course_id from score
      group by s_id having s_id not in (1))tmp1
  on student.s_id = tmp1.s_id
join (select concat_ws('|', collect_set(c_id)) course_id2
            from score  where s_id=1)tmp2
      on tmp1.course_id = tmp2.course_id2;
```
## 14、查询没学过张三老师讲授的任一门课程的学生姓名（重点）:
```sql
select student.* from student
  left join (select s_id from score
          join (select c_id from course join  teacher on course.t_id=teacher.t_id and t_name='张三')tmp2
          on score.c_id=tmp2.c_id )tmp
  on student.s_id = tmp.s_id
  where tmp.s_id is null;
```
## 15、查询两门及其以上不及格课程的同学的学号，姓名及其平均成绩（重点）:
```sql
select student.s_id,student.s_name,tmp.avg_score from student
inner join (select s_id from score
      where s_score<60
        group by score.s_id having count(s_id)>1)tmp2
on student.s_id = tmp2.s_id
left join (
    select s_id,round(AVG (score.s_score)) avg_score
      from score group by s_id)tmp
      on tmp.s_id=student.s_id;
```
## 16、检索01课程分数小于60，按分数降序排列的学生信息（和34类似）:
```sql
select student.*,s_score from student,score
where student.s_id=score.s_id and s_score<60 and c_id='01'
order by s_score desc;
```
## 17、按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩（重点）:
```sql
select a.s_id,tmp1.s_score as chinese,tmp2.s_score as math,tmp3.s_score as english,
    round(avg (a.s_score),2) as avgScore
from score a
left join (select s_id,s_score  from score s1 where  c_id='01')tmp1 on  tmp1.s_id=a.s_id
left join (select s_id,s_score  from score s2 where  c_id='02')tmp2 on  tmp2.s_id=a.s_id
left join (select s_id,s_score  from score s3 where  c_id='03')tmp3 on  tmp3.s_id=a.s_id
group by a.s_id,tmp1.s_score,tmp2.s_score,tmp3.s_score order by avgScore desc;
```
## 18.查询各科成绩最高分、最低分和平均分：以如下形式显示：课程ID，课程name，最高分，最低分，平均分，及格率，中等率，优良率，优秀率(重点):
```sql
select course.c_id,course.c_name,tmp.maxScore,tmp.minScore,tmp.avgScore,tmp.passRate,tmp.moderate,tmp.goodRate,tmp.excellentRates from course
join(select c_id,max(s_score) as maxScore,min(s_score)as minScore,
    round(avg(s_score),2) avgScore,
    round(sum(case when s_score>=60 then 1 else 0 end)/count(c_id),2)passRate,
    round(sum(case when s_score>=60 and s_score<70 then 1 else 0 end)/count(c_id),2) moderate,
    round(sum(case when s_score>=70 and s_score<80 then 1 else 0 end)/count(c_id),2) goodRate,
    round(sum(case when s_score>=80 and s_score<90 then 1 else 0 end)/count(c_id),2) excellentRates
from score group by c_id)tmp on tmp.c_id=course.c_id;
```
## 19、按各科成绩进行排序，并显示排名（重点row_number）:
```sql
select c_id,s_id,s_score,row_number()over(partition by c_id order by s_score desc)as rank from score;
```
## 20、查询学生的总成绩并进行排名（重点）:
```sql
select score.s_id,s_name,sum(s_score) sumscore,row_number()over(order by sum(s_score) desc) Ranking
  from score ,student
    where score.s_id=student.s_id
    group by score.s_id,s_name order by sumscore desc;
```
## 21、查询不同老师所教不同课程平均分从高到低显示（重点）:
```sql
select course.c_id,course.t_id,t_name,round(avg(s_score),2)as avgscore from course
    join teacher on teacher.t_id=course.t_id
    join score on course.c_id=score.c_id
    group by course.c_id,course.t_id,t_name order by avgscore desc;
```
## 22、查询所有课程的成绩第2名到第3名的学生信息及该课程成绩（重点）:
```sql
select student.*,t1.c_id,t1.s_score,t1.ranking
from(select s_id,c_id,s_score,row_number() over (partition by c_id order by s_score desc) ranking
from score)t1
join student on student.s_id=t1.s_id
where ranking>1 and ranking<4;
```
## 23、统计各科成绩各分数段人数：课程编号,课程名称,[100-85],[85-70],[70-60],[0-60]及所占百分比（重点）
```sql
select c.c_id,c.c_name,tmp1.s0_60, tmp1.percentum,tmp2.s60_70, tmp2.percentum,tmp3.s70_85, tmp3.percentum,tmp4.s85_100, tmp4.percentum
from course c
join(select c_id,sum(case when s_score<60 then 1 else 0 end )as s0_60,
               round(100*sum(case when s_score<60 then 1 else 0 end )/count(c_id),2)as percentum
     from score group by c_id)tmp1 on tmp1.c_id =c.c_id
left join(select c_id,sum(case when s_score<70 and s_score>=60 then 1 else 0 end )as s60_70,
               round(100*sum(case when s_score<70 and s_score>=60 then 1 else 0 end )/count(c_id),2)as percentum
     from score group by c_id)tmp2 on tmp2.c_id =c.c_id
left join(select c_id,sum(case when s_score<85 and s_score>=70 then 1 else 0 end )as s70_85,
               round(100*sum(case when s_score<85 and s_score>=70 then 1 else 0 end )/count(c_id),2)as percentum
     from score group by c_id)tmp3 on tmp3.c_id =c.c_id
left join(select c_id,sum(case when s_score>=85 then 1 else 0 end )as s85_100,
               round(100*sum(case when s_score>=85 then 1 else 0 end )/count(c_id),2)as percentum
     from score group by c_id)tmp4 on tmp4.c_id =c.c_id;
```
## 24、查询学生平均成绩及其名次（重点）:
```sql
select tmp.*,row_number()over(order by tmp.avgScore desc) Ranking from
  (select student.s_id,
          student.s_name,
          round(avg(score.s_score),2) as avgScore
  from student join score
  on student.s_id=score.s_id
  group by student.s_id,student.s_name)tmp
order by avgScore desc;
```
## 25、查询各科成绩前三名的记录（不考虑成绩并列情况）（重点）
```sql
select score.c_id,course.c_name,student.s_name,s_score from score
join student on student.s_id=score.s_id
join course on  score.c_id='01' and course.c_id=score.c_id
order by s_score desc limit 3;  
```
## 26、查询每门课程被选修的学生数:
```sql
select c.c_id,c.c_name,tmp.number from course c
    join (select c_id,count(1) as number from score
        where score.s_score<60 group by score.c_id)tmp
    on tmp.c_id=c.c_id;
```
## 27、查询出只有两门课程的全部学生的学号和姓名:
```sql
select st.s_id,st.s_name from student st
  join (select s_id from score group by s_id having count(c_id) =2)tmp
    on st.s_id=tmp.s_id;
```
## 28、查询男生、女生人数:
```sql
select tmp1.man,tmp2.women from
    (select count(1) as man from student where s_sex='男')tmp1,
    (select count(1) as women from student where s_sex='女')tmp2;
```
## 29、查询名字中含有风字的学生信息:
```sql
select * from student where s_name like '%风%';
```
## 30、查询同名同性学生名单，并统计同名人数:
```sql
select s1.s_id,s1.s_name,s1.s_sex,count(*) as sameName
from student s1,student s2
where s1.s_name=s2.s_name and s1.s_id<>s2.s_id and s1.s_sex=s2.s_sex
group by s1.s_id,s1.s_name,s1.s_sex;
```
## 31、查询1990年出生的学生名单（重点year）:
```sql
select * from student where s_birth like '1990%';
```
## 32、查询每门课程的平均成绩，结果按平均成绩降序排列，平均成绩相同时，按课程编号升序排列:
```sql
select score.c_id,c_name,round(avg(s_score),2) as avgScore from score
  join course on score.c_id=course.c_id
    group by score.c_id,c_name order by avgScore desc,score.c_id asc;
```
## 33、查询平均成绩大于等于85的所有学生的学号、姓名和平均成绩:
```sql
select score.s_id,s_name,round(avg(s_score),2)as avgScore from score
    join student on student.s_id=score.s_id
    group by score.s_id,s_name having avg(s_score) >= 85;
```
## 34、查询课程名称为数学，且分数低于60的学生姓名和分数:
```sql
select s_name,s_score as mathScore from student
    join (select s_id,s_score
            from score,course
            where score.c_id=course.c_id and c_name='数学')tmp
    on tmp.s_score < 60 and student.s_id=tmp.s_id;
```
## 35、查询所有学生的课程及分数情况（重点）:
```sql
select a.s_name,
    SUM(case c.c_name when '语文' then b.s_score else 0 end ) as chainese,
    SUM(case c.c_name when '数学' then b.s_score else 0 end ) as math,
    SUM(case c.c_name when '英语' then b.s_score else 0 end ) as english,
    SUM(b.s_score) as sumScore
  from student a
    join score b on a.s_id=b.s_id
    join course c on b.c_id=c.c_id
    group by s_name,a.s_id;
```
## 36、查询任何一门课程成绩在70分以上的学生姓名、课程名称和分数（重点）:
```sql
select student.s_id,s_name,c_name,s_score from student
  join (select sc.* from score sc
        left join(select s_id from score where s_score < 70 group by s_id)tmp
        on sc.s_id=tmp.s_id where tmp.s_id is null)tmp2
    on student.s_id=tmp2.s_id
  join course on tmp2.c_id=course.c_id
order by s_id;
```
## 37、查询课程不及格的学生:
```sql
select s_name,c_name as courseName,tmp.s_score
from student
join (select s_id,s_score,c_name
      from score,course
      where score.c_id=course.c_id and s_score < 60)tmp
on student.s_id=tmp.s_id;
```
## 38、查询课程编号为01且课程成绩在80分以上的学生的学号和姓名:
```sql
select student.s_id,s_name,s_score as score_01
from student
join score on student.s_id=score.s_id
where c_id='01' and s_score >= 80;
```
## 39、求每门课程的学生人数:
```sql
select course.c_id,course.c_name,count(1)as selectNum
from course
join score on course.c_id=score.c_id
group by course.c_id,course.c_name;
```
## 40、查询选修张三老师所授课程的学生中，成绩最高的学生信息及其成绩（重点top）:
```sql
select student.*,tmp3.c_name,tmp3.maxScore
from (select s_id,c_name,max(s_score)as maxScore from score
      join (select course.c_id,c_name from course join
                  (select t_id,t_name from teacher where t_name='张三')tmp
            on course.t_id=tmp.t_id)tmp2
      on score.c_id=tmp2.c_id group by score.s_id,c_name
      order by maxScore desc limit 1)tmp3
join student
on student.s_id=tmp3.s_id;
```
## 41、查询不同课程成绩相同的学生的学生编号、课程编号、学生成绩（重点）:
```sql
select distinct a.s_id,a.c_id,a.s_score from score a,score b
    where a.c_id <> b.c_id and a.s_score=b.s_score;
```
## 42、查询每门课程成绩最好的前三名（重点）:
```sql
select tmp1.* from
  (select *,row_number()over(order by s_score desc) ranking
      from score  where c_id ='01')tmp1
where tmp1.ranking <= 3
union all
select tmp2.* from
  (select *,row_number()over(order by s_score desc) ranking
      from score where c_id ='02')tmp2
where tmp2.ranking <= 3
union all
select tmp3.* from
  (select *,row_number()over(order by s_score desc) ranking
      from score where c_id ='03')tmp3
where tmp3.ranking <= 3;
```
## 43、统计每门课程的学生选修人数（超过5人的课程才统计）:
```sql
select distinct course.c_id,tmp.num from course
    join (select c_id,count(1) as num from score group by c_id)tmp
    where tmp.num>=5 order by tmp.num desc ,course.c_id asc;
```
## 44、检索至少选修两门课程的学生学号:
```sql
select s_id,count(c_id) as totalCourse
from score
group by s_id
having count(c_id) >= 2;
```
## 45、查询选修了全部课程的学生信息:
```sql
select student.* 
from student,
     (select s_id,count(c_id) as totalCourse 
      from score group by s_id)tmp
where student.s_id=tmp.s_id and totalCourse=3;
```
## 46、查询各学生的年龄(周岁):
 ```sql
select s_name,s_birth,
      (year(CURRENT_DATE)-year(s_birth)-
        (case when month(CURRENT_DATE) < month(s_birth) then 1
          when month(CURRENT_DATE) = month(s_birth) and day(CURRENT_DATE) < day(s_birth) then 1
          else 0 end)
        ) as age
    from student;

	select s_name,s_birth,
  floor((datediff(current_date,s_birth) - floor((year(current_date) - year(s_birth))/4))/365) as age
from student;
```

## 47、查询本周过生日的学生:
```sql
select * from student where weekofyear(CURRENT_DATE)+1 =weekofyear(s_birth);
```

## 48、查询下周过生日的学生:
```sql
select * from student where weekofyear(CURRENT_DATE)+1 =weekofyear(s_birth);
```
## 49、查询本月过生日的学生:
```sql
select * from student where MONTH(CURRENT_DATE) =MONTH(s_birth);
```
## 50、查询12月份过生日的学生:
```sql
select s_name,s_sex,s_birth from student where substring(s_birth,6,2)='12';
```