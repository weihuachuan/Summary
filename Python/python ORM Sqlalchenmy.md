# python ORM Sqlalchenmy Django 外的ORM 框架
## SqlalChenmy.py
```python
# 依次导入常用的数据类型
from datetime import datetime

from sqlalchemy import create_engine, Column, Integer, String, Float, DECIMAL, Boolean, Date, DateTime, Time, Text, \
    Enum, ForeignKey, SMALLINT, TIMESTAMP, VARCHAR, MetaData

from sqlalchemy import Column, ForeignKey, Integer, String, TIMESTAMP
from sqlalchemy.ext.declarative import declarative_base

from sqlalchemy.orm import sessionmaker, relationship

from config.application import *

Base = declarative_base()


def connect(user=mysql_user, password=mysql_password,
            db=mysql_db, host=mysql_ip, port=mysql_port):
    '''Returns a connection and a metadata object'''
    # We connect with the help of the PostgreSQL URL
    # postgresql://federer:grandestslam@localhost:5432/tennis
    url = 'mysql+pymysql://{username}:{pwd}@{host}:{port}/{db}?charset=utf8'
    url = url.format(username=user, pwd=password, host=host, port=port, db=db)

    # The return value of create_engine() is our connection object
    con = create_engine(url)
    return con


class Coex_Task(Base):
    __tablename__ = 'coex_task'
    id = Column(Integer, primary_key=True, autoincrement=True)
    task_name = Column(VARCHAR)
    mode = Column(SMALLINT)
    download = Column(VARCHAR)
    upload_path = Column(VARCHAR)
    status_message = Column(VARCHAR)
    status = Column(VARCHAR)
    group = Column(VARCHAR)
    log_path = Column(VARCHAR)
    # 创建时间
    create_time = Column(DateTime, default=datetime.now)
    # 修改时间
    modify_time = Column(DateTime, onupdate=datetime.now, default=datetime.now)
    is_delete = Column(SMALLINT)


class Run_time(Base):
    __tablename__ = 'run_time'
    id = Column(Integer, primary_key=True, autoincrement=True)
    running_time = Column(DateTime)
    Coex_Task_id = Column(Integer, ForeignKey(Coex_Task.id))
    # 创建时间
    create_time = Column(DateTime, default=datetime.now)
    # 修改时间
    modify_time = Column(DateTime, onupdate=datetime.now, default=datetime.now)
    coex_task = relationship('Coex_Task', backref='run_time')
    is_delete = Column(SMALLINT)


class Report(Base):
    __tablename__ = 'report'
    id = Column(Integer, primary_key=True, autoincrement=True)
    report_type = Column(VARCHAR)
    csv_path = Column(VARCHAR)
    report_path = Column(VARCHAR)
    keypart_path = Column(VARCHAR)
    keyword_path = Column(VARCHAR)
    null_radio = Column(VARCHAR)
    keep_item = Column(VARCHAR)
    date_processing = Column(VARCHAR)
    filter_sn = Column(VARCHAR)
    run = Column(SMALLINT)
    status = Column(VARCHAR)
    Coex_Task_id = Column(Integer, ForeignKey(Coex_Task.id))
    coex_task = relationship('Coex_Task', backref='report')
    # 创建时间
    create_time = Column(DateTime, default=datetime.now)
    # 修改时间
    modify_time = Column(DateTime, onupdate=datetime.now, default=datetime.now)
    is_delete = Column(SMALLINT)


class Html(Base):
    __tablename__ = 'html'
    id = Column(Integer, primary_key=True, autoincrement=True)
    Coex_Task_id = Column(Integer, ForeignKey(Coex_Task.id))
    Coex_Task = relationship('Coex_Task', backref='html')
    keypart_path = Column(VARCHAR)
    report_type = Column(VARCHAR)
    report = Column(VARCHAR)
    item = Column(VARCHAR)
    read_config = Column(VARCHAR)
    config_path = Column(VARCHAR)
    run = Column(SMALLINT)
    status = Column(VARCHAR)

    # 创建时间
    create_time = Column(DateTime, default=datetime.now)
    # 修改时间
    modify_time = Column(DateTime, onupdate=datetime.now, default=datetime.now)
    is_delete = Column(SMALLINT)


class Jointplot(Base):
    __tablename__ = 'jointplot'
    id = Column(Integer, primary_key=True, autoincrement=True)
    Coex_Task_id = Column(Integer, ForeignKey(Coex_Task.id))
    Coex_Task = relationship('Coex_Task', backref='jointplot')
    keypart_path = Column(VARCHAR)
    keyword_path = Column(VARCHAR)
    report_type = Column(VARCHAR)
    report = Column(VARCHAR)
    title = Column(VARCHAR)
    run = Column(SMALLINT)
    status = Column(VARCHAR)

    # 创建时间
    create_time = Column(DateTime, default=datetime.now)
    # 修改时间
    modify_time = Column(DateTime, onupdate=datetime.now, default=datetime.now)
    is_delete = Column(SMALLINT)


class Group(Base):
    __tablename__ = 'group'
    id = Column(Integer, primary_key=True, autoincrement=True)
    group_name = Column(VARCHAR)
    user_name = Column(VARCHAR)
    user_password = Column(VARCHAR)
    report_keypark = Column(VARCHAR)
    report_keyword = Column(VARCHAR)
    html_keypark = Column(VARCHAR)
    jointplot_keypark = Column(VARCHAR)
    jointplot_keyword = Column(VARCHAR)

    # 创建时间
    create_time = Column(DateTime, default=datetime.now)
    # 修改时间
    modify_time = Column(DateTime, onupdate=datetime.now, default=datetime.now)
    is_delete = Column(SMALLINT)


if __name__ == '__main__':
    engine = connect()
    session = sessionmaker(engine)()
    # session.close()
    # 查询数据
    print(session.query(Run_time).first().coex_task.task_name)
    print(session.query(Coex_Task).filter_by(id=39).first())
    print(session.query(Coex_Task).filter_by(id=39).all()[0].run_time)
    # 更新数据
    # u = session.query(Coex_Task).filter(Coex_Task.task_name == 'test').first()
    # u.task_name = '大王'
    # session.commit()
    # 聚合函数与分组
    # max/min/avg
    # print('------------------------------------------------')
    # print(session.query(func.max(Employee.emp_no)).scalar())
    # print('------------------------------------------------')
    # print(session.query(func.min(Employee.emp_no)).scalar())
    # print('------------------------------------------------')
    # print(session.query(func.avg(Employee.emp_no)).scalar())

    # 分组
    # print(session.query(func.count(Employee.emp_no)).group_by(Employee.emp_no).all())
    # 关联查询
    # 查询员工所在部门编号
    # 隐匿内连接
    # print('----------------------------------------------------------------------')
    # results = session.query(Employee, Dept_emp).filter(Employee.emp_no == Dept_emp.emp_no).filter(
    #     Employee.emp_no == 10010).all()  # 查询使用“类名+属性”
    # print(results)
    #
    # # JOIN
    # print('----------------------------------------------------------------------')
    # results = session.query(Employee, Dept_emp).join(Dept_emp, Employee.emp_no == Dept_emp.emp_no).filter(
    #     Employee.emp_no == 10010).all()
    # print(results)
    # results = session.query(func.count(Employee.emp_no)).join(Dept_emp, Employee.emp_no == Dept_emp.emp_no).filter(
    #     Employee.emp_no == 10010).all()
    # print(results)  # 上面的结果只显示一个值（因为以employee表各字段的值来显示自动去重了，但从统计结果看有2个值）
```
