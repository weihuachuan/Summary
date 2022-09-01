# python小技巧

## 消除if else 语句让业务代码更简洁

```python
import functools


def value_dispatch(func):
    registry = {}

    @functools.wraps(func)
    def wrapper(arg0, *args, **kwargs):
        try:
            delegate = registry[arg0]
        except KeyError:
            pass
        else:
            return delegate(arg0, *args, **kwargs)

        return func(arg0, *args, **kwargs)

    def register(value):
        def wrap(func):
            if value in registry:
                raise ValueError(
                    '@value_dispatch: there is already a handler '
                )
            registry[value] = func
            return func
        return wrap

    def register_for_all(values):
        def wrap(func):
            for value in values:
                if value in registry:
                    raise ValueError(
                        '@value_dispatch: there is already a handler '
                    )
                registry[value] = func
            return func
        return wrap

    wrapper.register = register
    wrapper.register_for_all = register_for_all
    return wrapper


@value_dispatch
def get_discount(level):
    return '等级错误'


@get_discount.register(1)
def parse_level_1(level):
    "大量计算代码"
    discount = 0.1
    return discount


@get_discount.register(2)
def parse_level_2(level):
    "大量计算代码"
    discount = 0.2
    return discount


@get_discount.register(3)
def parse_level_3(level):
    "大量计算代码"
    discount = 0.3
    return discount


@get_discount.register(4)
def parse_level_4(level):
    "大量计算代码"
    discount = 0.4
    return discount


@get_discount.register(5)
def parse_level_5(level):
    "大量计算代码"
    discount = 0.5
    return discount


@get_discount.register(6)
def parse_level_1(level):
    "大量计算代码"
    discount = 3 + 2 - 5 * 0.1
    return discount


@get_discount.register(7)
def parse_level_1(level):
    "大量计算代码"
    discount = 0.7
    return discount


if __name__ == '__main__':
    discount = get_discount(7)
    print('等级3的用户，获得的折扣是：%s' % discount)
    # test_oracle1 = TestOracle('mmbs_user', '123456', '10.175.94.58', '7035', 'MMBS')
    # test_oracle1.select_single(DEPART_INDEX_SQL)

```

# 设置pip代理

```shell
cd C:/user/
mkdir pip
cd pip
vim pip.ini
# 添加如下内容即可pip
[global]
proxy=http://23337:RFsw123.@10.191.131.15:3128
```

# 设置git代理

```shell
cd C:/user/
vim .gitConfig
# 添加如下内容即可
[user]
	name = weihuachuan
	email = 592067084@qq.com
[http]
	proxy = http://23337:RFsw123.@10.191.131.45:3128
```

# Redis 消费者中启动子线程（可手动停止）
```python
import json
import threading
import time
import inspect
import ctypes

import redis


def _async_raise(tid, exctype):
    """raises the exception, performs cleanup if needed"""
    tid = ctypes.c_long(tid)
    if not inspect.isclass(exctype):
        exctype = type(exctype)
    res = ctypes.pythonapi.PyThreadState_SetAsyncExc(tid, ctypes.py_object(exctype))
    if res == 0:
        raise ValueError("invalid thread id")
    elif res != 1:
        # """if it returns a number greater than one, you're in trouble,
        # and you should call it again with exc=NULL to revert the effect"""
        ctypes.pythonapi.PyThreadState_SetAsyncExc(tid, None)
        raise SystemError("PyThreadState_SetAsyncExc failed")


def stop_thread(thread):
    _async_raise(thread.ident, SystemExit)


def print_time(data, thread_dict):
    for i in range(10):
        print(data)
        time.sleep(1)
    thread_dict.pop(data['task_name'])

def start_consume(channel, thread_dict=None):
    redis_pool = redis.ConnectionPool(host='10.175.94.58', port=6379, password='123456', db=5,
                                      decode_responses=True)
    redis_db = redis.Redis(connection_pool=redis_pool)
    rc_sub = redis_db.pubsub()
    rc_sub.subscribe(channel)
    for item in rc_sub.listen():
        if item['type'] == 'message':
            print(item['channel'])
            print(item['data'])
            data = item['data']
            try:
                data = json.loads(item['data'])
            except Exception as e:
                print('不是标准命令=====>'+str(e))
            if data['command_type'] == 'start':
                t = threading.Thread(target=print_time, args=(data, thread_dict))
                t.setName(data['task_name'])
                thread_dict[t.getName()] = t
                t.start()
            elif data['command_type'] == 'stop':
                if data['task_name'] in thread_dict.keys():
                    stop_thread(thread_dict[data['task_name']])
                    thread_dict.pop(data['task_name'])
                else:
                    pass

if __name__ == "__main__":
    thread_dict = {}
    start_consume('Coex_test', thread_dict)                    

```

# python 定时任务
```python
import xgboost
from apscheduler.schedulers.blocking import BlockingScheduler

from Log import Log
import pandas as pd
from sqlalchemy import create_engine
from config import LEAVE_SQL
import time
import os
from apscheduler.schedulers.background import BackgroundScheduler

model = xgboost.Booster()
def trained(log):
    log.info("开始建模")
    try:
        con = create_engine('oracle+cx_oracle://eerf_user:123456@10.175.94.58:1528/EERFTESTDB')
        df = pd.read_sql_query(LEAVE_SQL, con)
        # 资位去除前缀
        list_position = []
        for str in list(df['e_grade']):
            if '試用師' in str:
                new = str.replace('試用師', '')
                list_position.append(new)
            else:
                new = str.replace('師', '')
                list_position.append(new)
        df.loc[:, 'e_grade'] = list_position
        df = df.set_index('user_id')
        df[['e_grade', 'cumulative_years', 'annual_leave_hours', 'sick_leave_hours']] = df[
            ['e_grade', 'cumulative_years', 'annual_leave_hours', 'sick_leave_hours']].astype(float)
        train_x = []
        train_y = []
        for i in range(0, len(df)):
            train_x.append(df.iloc[i, 0:-5])
            train_y.append(df.iloc[i, -5])
        params = {
            'booster': 'gbtree',
            'objective': 'binary:logistic',
            'gamma': 0.2,
            'max_depth': 10,
            'lambda': 2,
            'subsample': 0.7,
            'colsample_bytree': 0.7,
            'min_child_weight': 3,
            'silent': 0,
            'eta': 0.007,
            'seed': 1000,
            'nthread': 4,
        }
        plst = list(params.items())
        featureList = []
        for i in df.columns:
            featureList.append(i)
        featureList = featureList[0:-5]

        dtrain = xgboost.DMatrix(
            train_x, train_y, feature_names=featureList)
        num_rounds = 500
        model = xgboost.train(plst, dtrain, num_rounds)
        model.save_model('./model.json')
        log.info("建模执行完成")
    except Exception as e:
        log.error(e.__str__())

def insert_data(log):
    log.info("开始处理近一年的数据")
    try:
        con = create_engine('oracle+cx_oracle://eerf_user:123456@10.175.94.58:1528/EERFTESTDB')
        df0 = pd.read_sql_query(LEAVE_SQL, con)
        df = df0.copy(deep=True)
        # 资位去除前缀
        list_position = []
        for data in list(df['e_grade']):
            if '試用師' in data:
                new = data.replace('試用師', '')
                list_position.append(new)
            else:
                new = data.replace('師', '')
                list_position.append(new)
        df.loc[:, 'e_grade'] = list_position
        df[['e_grade', 'cumulative_years', 'annual_leave_hours', 'sick_leave_hours']] = df[
            ['e_grade', 'cumulative_years', 'annual_leave_hours', 'sick_leave_hours']].astype(float)
        ## model Path
        model.load_model('./model.json')
        df = df.set_index('user_id')
        test_x = []
        test_y = []
        for i in range(0, len(df)):
            test_x.append(df.iloc[i, 0:-5])
            test_y.append(df.iloc[i, -5])
        featureList = []
        for i in df.columns:
            featureList.append(i)
        featureList = featureList[0:-5]

        dtest = xgboost.DMatrix(test_x, feature_names=featureList)
        # dtest = xgboost.DMatrix(test_x)
        pred = model.predict(dtest)
        df0['rate'] = list(pred)
        df0[['cumulative_years']] = df0[['cumulative_years']].astype(float)
        df0[['user_id', 'e_grade', 'cname', 'functionteam', 'leader', 'department']] = df0[
            ['user_id', 'e_grade', 'cname', 'functionteam', 'leader', 'department']].astype(str)
        df0.to_sql('LEAVE_DETAILS_BD', con, index=False, if_exists='append')
        log.info("數據插入执行完成")
    except Exception as e:
        log.error(e.__str__())

def dojob(log):
    # 创建调度器：BlockingScheduler
    scheduler = BlockingScheduler(timezone='Asia/Shanghai')
    # 添加任务,每年一月七号执行
    scheduler.add_job(trained, 'cron', month=1, day=7, hour=5, minute=25, args=[log])
    # 添加任务,每月七号执行
    scheduler.add_job(insert_data, 'cron', month='1-12', day=11, hour=5, minute=30, args=[log])
    scheduler.start()

if __name__ == '__main__':
    log = Log("./")
    # scheduler = BackgroundScheduler(timezone='Asia/Shanghai')
    # scheduler.add_job(trained, 'cron', month=1, day=7, hour=5, minute=25, args=[log])
    # # scheduler.add_job(trained, 'interval', seconds=3, args=[log])
    # scheduler.start()
    # scheduler2 = BackgroundScheduler(timezone='Asia/Shanghai')
    # scheduler2.add_job(insert_data, 'cron', month='1-12', day=7, hour=5, minute=30, args=[log])
    # # scheduler2.add_job(insert_data, 'interval', seconds=3, args=[log])
    # scheduler2.start()
    # print('Press Ctrl+{0} to exit'.format('Break' if os.name == 'nt' else 'C'))
    # try:
    #     # This is here to simulate application activity (which keeps the main thread alive).
    #     while True:
    #         time.sleep(2)
    # except (KeyboardInterrupt, SystemExit):
    #     # Not strictly necessary if daemonic mode is enabled but should be done if possible
    #     scheduler.shutdown()
    #     log.error("定时器报错")
    try:
        dojob(log)
    except Exception as e:
        print(e)
        log.error("定时器报错")

```

